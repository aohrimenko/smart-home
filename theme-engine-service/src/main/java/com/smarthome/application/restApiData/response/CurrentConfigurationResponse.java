package com.smarthome.application.restApiData.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class CurrentConfigurationResponse {

    @Schema(title = "The current room(s) temperature setting.")
    private Integer temperature;

    @Schema(title = "The current room(s) music theme setting.")
    private String musicTheme;

    @Schema(title = "The current room(s) music volume setting.")
    private Integer musicVolume;

    @Schema(title = "The current room(s) light mode setting.")
    private Integer lightMode;

    @Schema(title = "The current room(s) light percentage setting.")
    private Integer lightsPercentage;

}
