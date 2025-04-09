package com.smarthome.application.restApiData;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record MusicResponse(
        Integer musicVolume,
        String musicTheme
) {

}
