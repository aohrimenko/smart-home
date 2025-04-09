package com.smarthome.application.eventData;


public record LightChangeEvent(
        Integer lightsPercentage,
        Integer lightMode
) {

}
