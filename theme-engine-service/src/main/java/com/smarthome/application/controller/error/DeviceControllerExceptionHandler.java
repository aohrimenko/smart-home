package com.smarthome.application.controller.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.smarthome.application.controller.DeviceController;
import com.smarthome.application.restApiData.enums.ApiError;
import com.smarthome.application.restApiData.response.ApplicationResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(assignableTypes = DeviceController.class)
public class DeviceControllerExceptionHandler {

    private static final String HTTP_MESSAGE_NOT_READABLE_INVALID_FORMAT_EXCEPTION_ERROR_MESSAGE = "The value [%s] is not valid for type [%s] in field [%s]";

    private static final String TYPE_OUT_OF_RANGE_ERROR_MESSAGE = "%s for field [%s]";

    /**
     * The default exception handling fallback
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ApplicationResponse<Void> handleException(Exception exception) {
        return ApplicationResponse.buildErrorResponse(ApiError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .details(exception.getMessage())
                .build());
    }

    /**
     * This exception handling method purpose is to demonstrate proper exception parsing and output
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ApplicationResponse<Void> handleException(HttpMessageNotReadableException exception) {
        String errorDetails = "Http Message Not Readable";

        if (exception.getCause() instanceof final InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType() != null && !CollectionUtils.isEmpty(invalidFormatException.getPath())) {
                errorDetails = String.format(HTTP_MESSAGE_NOT_READABLE_INVALID_FORMAT_EXCEPTION_ERROR_MESSAGE,
                        invalidFormatException.getValue(),
                        invalidFormatException.getTargetType().getSimpleName(),
                        invalidFormatException.getPath().get(invalidFormatException.getPath().size() - 1).getFieldName());
            }
        } else if (exception.getCause() instanceof final JsonMappingException jsonMappingException) {
            errorDetails = String.format(TYPE_OUT_OF_RANGE_ERROR_MESSAGE,
                    jsonMappingException.getOriginalMessage(),
                    jsonMappingException.getPath().get(jsonMappingException.getPath().size() - 1).getFieldName());
        }

        return ApplicationResponse.buildErrorResponse(List.of(ApiError.builder()
                .code("MESSAGE_NOT_READABLE")
                .details(errorDetails)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ApplicationResponse<Void> handleException(ConstraintViolationException exception) {
        return ApplicationResponse.buildErrorResponse(ApiError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .details("Constraint Violation error!")
                .build());
    }


    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ApplicationResponse<Void> handleException(IllegalArgumentException exception) {
        return ApplicationResponse.buildErrorResponse(ApiError.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .details("Illegal Argument error!")
                .build());
    }

}
