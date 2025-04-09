package com.smarthome.application.restApiData.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class CurrentDeviceConfigurationResponse {

    private Integer temperature;

    private String musicTheme;

    private Integer musicVolume;

    private Integer lightMode;

    private Integer lightsPercentage;

}
