package com.smarthome.application.service;

import com.smarthome.application.eventData.LightChangeEvent;
import com.smarthome.application.restApiData.LightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class LightService {

    private final AtomicInteger lightsPercentage = new AtomicInteger(0);

    private final AtomicInteger lightMode = new AtomicInteger(0);

    public void processEvent(final LightChangeEvent event) {
        lightsPercentage.set(event.lightsPercentage());
        lightMode.set(event.lightMode());
    }

    public LightResponse getCurrentConfig() {
        return new LightResponse(lightsPercentage.get(), lightMode.get());
    }

}
