package com.smarthome.application.service;

import com.smarthome.application.eventData.HomeThemeEvent;
import com.smarthome.application.mapper.DeviceEventDataMapper;
import com.smarthome.application.producer.ChangeClimateEventProducer;
import com.smarthome.application.producer.ChangeLightEventProducer;
import com.smarthome.application.producer.ChangeMusicEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeEventDispatcherService {

    private final ChangeMusicEventProducer changeMusicEventProducer;

    private final ChangeClimateEventProducer changeClimateEventProducer;

    private final ChangeLightEventProducer changeLightEventProducer;

    private final DeviceEventDataMapper deviceEventDataMapper;

    public void dispatchToServices(final HomeThemeEvent combinedEvent) {

        Optional.ofNullable(deviceEventDataMapper.mapToMusicEvent(combinedEvent))
                .ifPresent(changeMusicEventProducer::sendEvent);

        Optional.ofNullable(deviceEventDataMapper.mapToClimateEvent(combinedEvent))
                .ifPresent(changeClimateEventProducer::sendEvent);

        Optional.ofNullable(deviceEventDataMapper.mapToLightEvent(combinedEvent))
                .ifPresent(changeLightEventProducer::sendEvent);
    }

}
