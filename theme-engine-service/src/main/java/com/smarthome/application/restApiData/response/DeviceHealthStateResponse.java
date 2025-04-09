package com.smarthome.application.restApiData.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class DeviceHealthStateResponse {

    @Schema(title = "The state of the theme-dispatcher-service.")
    private String dispatcher;

    @Schema(title = "The state of the music-service.")
    private String music;

    @Schema(title = "The state of the climate-service.")
    private String climate;

    @Schema(title = "The state of the light-service.")
    private String lights;

}
