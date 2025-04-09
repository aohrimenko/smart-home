package com.smarthome.application.restApiData.enums;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@JsonInclude(NON_NULL)
public class ApiError {

    private HttpStatus httpStatus;

    private String code;

    private String details;

    @Override
    public String toString() {

        return httpStatus != null ?
                String.format("{error: %s, details: %s, httpStatus: %s}", code, details, httpStatus) :
                String.format("{error: %s, details: %s}", code, details);
    }

}