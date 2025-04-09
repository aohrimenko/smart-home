package com.smarthome.application.connection;

import com.smarthome.application.restApiData.response.ApplicationResponse;
import exception.ServiceBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.*;

import java.lang.reflect.Type;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class HttpRestTemplateClient {

    private final RestTemplate restTemplate;

    public <T> ApplicationResponse<T> get(final String url, final Class<T> responseType, final HttpMethod method) {
        return get(url, responseType, method, null);
    }

    public <T> ApplicationResponse<T> get(final String url, final Class<T> responseType, final HttpMethod method, final Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        try {
            // Note on why we need this complication:
            // Without using ParameterizedTypeReference, the deserializer will treat the generic inner type
            // (e.g., ApplicationResponse<T>) as a raw type. As a result, the payload field inside the response
            // will be deserialized as a LinkedHashMap instead of the desired object.
            // This will cause a ClassCastException at runtime when trying to cast the payload to the expected type.
            // any other solution that will be way less readable and common-sense person friendly.
            Type responseTypeWrapped = ResolvableType
                    .forClassWithGenerics(ApplicationResponse.class, responseType)
                    .getType();

            ParameterizedTypeReference<?> typeRef = ParameterizedTypeReference.forType(responseTypeWrapped);

            ResponseEntity<?> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    typeRef
            );

            @SuppressWarnings("unchecked")
            ApplicationResponse<T> body = (ApplicationResponse<T>) response.getBody();

            return body;

        } catch (HttpClientErrorException ex) {
            log.warn("[CLIENT ERROR] - HttpRestTemplateClient: {}", ex.getStatusCode(), ex);
            throw new ServiceBusinessException("HTTP_CLIENT_ERROR", (HttpStatus) ex.getStatusCode());

        } catch (HttpServerErrorException ex) {
            log.error("[SERVER ERROR] - HttpRestTemplateClient: {}", ex.getStatusCode(), ex);
            throw new ServiceBusinessException("HTTP_SERVER_ERROR", (HttpStatus) ex.getStatusCode());

        } catch (ResourceAccessException ex) {
            log.error("[CONNECTION ERROR] - HttpRestTemplateClient: Failed to access resource", ex);
            throw new ServiceBusinessException("RESOURCE_ACCESS_ERROR", HttpStatus.SERVICE_UNAVAILABLE);

        } catch (RestClientException ex) {
            log.error("[REST CLIENT ERROR] - HttpRestTemplateClient: ", ex);
            throw new ServiceBusinessException("REST_CLIENT_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception ex) {
            log.error("[UNEXPECTED ERROR] - HttpRestTemplateClient: ", ex);
            throw new ServiceBusinessException("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}