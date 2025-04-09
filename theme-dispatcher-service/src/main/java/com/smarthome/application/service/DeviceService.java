package com.smarthome.application.service;

import com.smarthome.application.connection.HttpRestTemplateClient;
import com.smarthome.application.restApiData.enums.ApiError;
import com.smarthome.application.restApiData.enums.ApiOperationStatus;
import com.smarthome.application.restApiData.response.ApplicationResponse;
import com.smarthome.application.restApiData.response.CurrentDeviceConfigurationResponse;
import com.smarthome.application.restApiData.response.DeviceHealthStateResponse;
import com.smarthome.application.security.ServiceSecurityConfiguration;
import com.smarthome.application.service.serviceRegistry.DeviceResponseDescriptor;
import com.smarthome.application.service.serviceRegistry.DeviceUpdaterRegistry;
import exception.ServiceBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final static String UP_RESPONSE = "UP";

    private final static String UNREACHABLE_RESPONSE = "UNREACHABLE";

    private final HttpRestTemplateClient connector;

    private final ServiceSecurityConfiguration serviceSecurityConfiguration;

    private final DeviceUpdaterRegistry updaterRegistry;

    private final ExecutorService executorService = Executors.newFixedThreadPool(DeviceResponseDescriptor.values().length);

    /**
     * This method does appear complex, but it does couple of things well:
     * Abstract the services information from the implementation - their data specifics(names, responses, etc.) is contained in DeviceUpdaterRegistry
     * The method uses the ExecutorService to make the calls into separate threads so the execution would take as longer, as the longest call
     *
     * @return the combined response of all micro-services current running configurations
     */
    public ApplicationResponse<CurrentDeviceConfigurationResponse> getDeviceConfiguration() {

        CurrentDeviceConfigurationResponse response = new CurrentDeviceConfigurationResponse();
        String apiKeyHeader = serviceSecurityConfiguration.getApiKeyServiceHeader();

        ApplicationResponse<CurrentDeviceConfigurationResponse> applicationResponse = ApplicationResponse.buildBlankResponse();

        List<ApiError> errors = new CopyOnWriteArrayList<>();

        List<Callable<Void>> tasks = Arrays.stream(DeviceResponseDescriptor.values())
                .map(device -> (Callable<Void>) () -> {
                    try {
                        String url = serviceSecurityConfiguration.getServiceUrl(device.getServiceName());
                        String apiKeyValue = serviceSecurityConfiguration.getServiceApiKey(device.getServiceName());

                        Object payload = connector.get(
                                url,
                                device.getResponseClass(),
                                HttpMethod.GET,
                                Map.of(apiKeyHeader, apiKeyValue)
                        ).getPayload();

                        BiConsumer<CurrentDeviceConfigurationResponse, Object> updater = updaterRegistry.getUpdater(device);
                        if (updater != null && payload != null) {
                            updater.accept(response, payload);
                        }
                    } catch (ServiceBusinessException ex) {
                        errors.add(ApiError.builder()
                                .code(ex.getError().getCode())
                                .details(device.getServiceName())
                                .build());
                    }
                    return null;
                })
                .toList();

        try {
            executorService.invokeAll(tasks, 5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!CollectionUtils.isEmpty(errors)) {
            applicationResponse.setErrors(errors);
        }
        applicationResponse.setStatus(CollectionUtils.isEmpty(errors) ? ApiOperationStatus.SUCCESS : ApiOperationStatus.FAILED);
        applicationResponse.setPayload(response);

        return applicationResponse;
    }

    /**
     * Retrieves the health state of all the micro-services
     *
     * @return response, containing the state of all the micro-services
     */
    public ApplicationResponse<DeviceHealthStateResponse> getHealthState() {

        DeviceHealthStateResponse response = DeviceHealthStateResponse.builder()
                .dispatcher(UP_RESPONSE)
                .build();
        String apiKeyHeader = serviceSecurityConfiguration.getApiKeyServiceHeader();
        ApplicationResponse<DeviceHealthStateResponse> applicationResponse = ApplicationResponse.buildBlankResponse();
        List<ApiError> errors = new CopyOnWriteArrayList<>();

        List<Callable<Void>> tasks = Arrays.stream(DeviceResponseDescriptor.values())
                .map(type -> (Callable<Void>) () -> {
                    try {
                        var config = serviceSecurityConfiguration.getServices().get(type.getServiceName());
                        if (config == null) return null;

                        String healthUrl = config.getUrl() + config.getHealthEndpoint();

                        ApplicationResponse<Void> healthResponse = connector.get(
                                healthUrl,
                                Void.class,
                                HttpMethod.GET,
                                Map.of(apiKeyHeader, config.getApiKeyValue())
                        );

                        String status = healthResponse.hasErrors() ? UNREACHABLE_RESPONSE : UP_RESPONSE;
                        if (healthResponse.hasErrors()) {
                            errors.addAll(healthResponse.getErrors());
                        }
                        type.getHealthSetter().accept(response, status);

                    } catch (ServiceBusinessException ex) {
                        errors.add(ApiError.builder()
                                .code(ex.getError().getCode())
                                .details(type.getServiceName())
                                .build());
                        type.getHealthSetter().accept(response, UNREACHABLE_RESPONSE);
                    } catch (Exception ex) {
                        log.error("[DISPATCHER_GET_DEVICE_DATA] error: ", ex);
                    }
                    return null;
                })
                .toList();

        try {
            executorService.invokeAll(tasks, 5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!CollectionUtils.isEmpty(errors)) {
            applicationResponse.setErrors(errors);
        }
        applicationResponse.setPayload(response);
        applicationResponse.setStatus(ApiOperationStatus.SUCCESS);

        return applicationResponse;
    }

}
