package com.smarthome.application.config;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HomeThemeConfiguration {

    private Integer temperature;

    private String musicTheme;

    private Integer musicVolume;

    private Integer lightMode;

    private Integer lightsPercentage;

}
