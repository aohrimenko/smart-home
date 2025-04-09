package com.smarthome.application.eventData;


public record MusicChangeEvent(
        Integer musicVolume,
        String musicTheme
) {

}
