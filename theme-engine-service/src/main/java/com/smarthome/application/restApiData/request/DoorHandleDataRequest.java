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
public class DoorHandleDataRequest {

    @Schema(title = "The(presumably by future technology or other device) detected heart rate of the home owner.")
    private Integer hearthRate;

    @Schema(title = "The detected blood pressure data.")
    private BloodPressureData bloodPressureData;

    @Schema(title = "The noise data, detected by the door.")
    private DoorNoiseData doorNoiseData;

}
