package com.smarthome.application.data;

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
public class ThemeDeviceSettings {

    @Schema(title = "The resolved temperature that would be set by the climate-service.")
    private Integer temperature;

    @Schema(title = "The resolved music theme that would be set by the music-service.")
    private String musicTheme;

    @Schema(title = "The resolved music volume settings that would be set by the music-service.")
    private Integer musicVolume;

    @Schema(title = "The resolved light mode that would be set by the light-service.")
    private Integer lightMode;

    @Schema(title = "The resolved light percentage that would be set by the light-service.")
    private Integer lightsPercentage;

}
