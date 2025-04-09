package com.smarthome.application.service;

import com.smarthome.application.eventData.ClimateChangeEvent;
import com.smarthome.application.restApiData.ClimateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ClimateService {

    private final AtomicInteger temperature = new AtomicInteger(0);

    public void processEvent(final ClimateChangeEvent event) {
        temperature.set(event.temperature());
    }

    public ClimateResponse getCurrentConfig() {
        return new ClimateResponse(temperature.get());
    }

}
