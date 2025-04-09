package com.smarthome.application.eventData;

public record ChangeMusicEvent(
        String musicTheme,
        Integer musicVolume
) {

}
