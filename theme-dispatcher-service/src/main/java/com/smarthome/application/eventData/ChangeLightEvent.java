package com.smarthome.application.eventData;

public record ChangeLightEvent(
        Integer lightMode,
        Integer lightsPercentage
) {

}
