package com.smarthome.application.eventData;

public record HomeThemeEvent(
        Integer temperature,
        String musicTheme,
        Integer musicVolume,
        Integer lightMode,
        Integer lightsPercentage
) {

}
