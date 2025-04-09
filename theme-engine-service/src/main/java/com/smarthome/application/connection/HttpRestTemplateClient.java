package com.smarthome.application.connection;

import com.smarthome.application.restApiData.enums.ApiError;
import com.smarthome.application.restApiData.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class HttpRestTemplateClient {

    private final RestTemplate restTemplate;

    public <T> ApplicationResponse<T> request(String url, Class<T> responseType, HttpMethod method) {
        return request(url, responseType, method, null);
    }

    public <T> ApplicationResponse<T> request(String url, Class<T> responseType, HttpMethod method, Map<String, String> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();

        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        try {
            ParameterizedTypeReference<ApplicationResponse<T>> typeReference =
                    new ParameterizedTypeReference<>() {
                    };

            ResponseEntity<ApplicationResponse<T>> response = restTemplate.exchange(url, method, entity, typeReference);

            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("[ERROR] - HttpRestTemplateClient: ", ex);
            return ApplicationResponse.buildErrorResponse(
                    ApiError.builder()
                            .code("HTTP_ERROR")
                            .build()
            );
        } catch (Exception ex) {
            log.error("[ERROR] - HttpRestTemplateClient: ", ex);
            return ApplicationResponse.buildErrorResponse(
                    ApiError.builder()
                            .code("INTERNAL_SERVER_ERROR")
                            .build());
        }
    }

}