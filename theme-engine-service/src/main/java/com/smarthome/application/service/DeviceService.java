package com.smarthome.application.service;

import com.smarthome.application.config.SecurityConfiguration;
import com.smarthome.application.connection.HttpRestTemplateClient;
import com.smarthome.application.restApiData.response.ApplicationResponse;
import com.smarthome.application.restApiData.response.CurrentConfigurationResponse;
import com.smarthome.application.restApiData.response.DeviceHealthStateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private static final String DISPATCHER_SERVICE_NAME = "theme-dispatcher";

    private final SecurityConfiguration securityConfiguration;

    private final HttpRestTemplateClient connector;

    /**
     * Method for retrieving the current configuration set for the micro-services
     *
     * @return response with the current configuration of all the reachable micro-services
     */
    public ApplicationResponse<CurrentConfigurationResponse> getCurrentDeviceConfiguration() {
        return connector.request(securityConfiguration.getServiceUrl(DISPATCHER_SERVICE_NAME),
                CurrentConfigurationResponse.class,
                HttpMethod.GET,
                Map.of(securityConfiguration.getApiKeyServiceHeader(), securityConfiguration.getServiceApiKey(DISPATCHER_SERVICE_NAME)));
    }

    public ApplicationResponse<DeviceHealthStateResponse> getDeviceHealth() {
        return connector.request(securityConfiguration.getServiceHealthUrl(DISPATCHER_SERVICE_NAME),
                DeviceHealthStateResponse.class,
                HttpMethod.GET,
                Map.of(securityConfiguration.getApiKeyServiceHeader(), securityConfiguration.getServiceApiKey(DISPATCHER_SERVICE_NAME)));
    }

}
