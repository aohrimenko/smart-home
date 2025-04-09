package com.smarthome.application.service;

import com.smarthome.application.eventData.MusicChangeEvent;
import com.smarthome.application.restApiData.MusicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final AtomicReference<String> musicTheme = new AtomicReference<>();

    private final AtomicInteger musicVolume = new AtomicInteger(0);

    public void processEvent(final MusicChangeEvent event) {
        musicVolume.set(event.musicVolume());
        musicTheme.set(event.musicTheme());
    }

    public MusicResponse getCurrentConfig() {
        return new MusicResponse(musicVolume.get(), musicTheme.get());
    }

}
