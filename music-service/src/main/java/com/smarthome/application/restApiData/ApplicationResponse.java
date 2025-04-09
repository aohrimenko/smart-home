package com.smarthome.application.restApiData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smarthome.application.restApiData.enums.ApiError;
import com.smarthome.application.restApiData.enums.ApiOperationStatus;
import lombok.*;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@JsonInclude(NON_NULL)
public class ApplicationResponse<T> {

    private ApiOperationStatus status;

    private T payload;

    private List<ApiError> errors;

    public static <T> ApplicationResponse<T> buildSuccessResponse() {

        return ApplicationResponse.<T>builder()
                .status(ApiOperationStatus.SUCCESS)
                .build();
    }

    public static <T> ApplicationResponse<T> buildSuccessResponse(final T payload) {

        return ApplicationResponse.<T>builder()
                .status(ApiOperationStatus.SUCCESS)
                .payload(payload)
                .build();
    }

    public static <T> ApplicationResponse<T> buildErrorResponse(final ApiError error) {

        return ApplicationResponse
                .<T>builder()
                .status(ApiOperationStatus.FAILED)
                .errors(Collections.singletonList(error))
                .build();
    }

    public static <T> ApplicationResponse<T> buildErrorResponse(final List<ApiError> errors) {

        return ApplicationResponse.<T>builder()
                .status(ApiOperationStatus.FAILED)
                .errors(errors)
                .build();
    }

    public static <T> ApplicationResponse<T> buildErrorResponse(final String code, final String details) {

        return ApplicationResponse.<T>builder()
                .status(ApiOperationStatus.FAILED)
                .errors(Collections.singletonList(ApiError.builder()
                        .code(code)
                        .details(details)
                        .build()))
                .build();
    }

    public boolean hasErrors() {

        return errors != null && !errors.isEmpty();
    }

}
