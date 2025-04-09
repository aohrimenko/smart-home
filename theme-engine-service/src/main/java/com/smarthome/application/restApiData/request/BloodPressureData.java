package com.smarthome.application.restApiData.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@JsonInclude(NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BloodPressureData {

    @Schema(title = "The low \"border\" of the blood pressure measurement.")
    private Integer lowNumber;

    @Schema(title = "The high \"border\" of the blood pressure measurement.")
    private Integer highNumber;

}
