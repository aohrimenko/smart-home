package com.smarthome.application.consumer;

import com.smarthome.application.eventData.HomeThemeEvent;
import com.smarthome.application.service.ThemeEventDispatcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThemeDispatcherEventConsumer {

    private final ThemeEventDispatcherService themeEventDispatcherService;

    @KafkaListener(topics = "home-theme-topic", groupId = "home-theme-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(HomeThemeEvent homeThemeEventEvent, Acknowledgment acknowledgment) {
        log.info("Processing event: {}", homeThemeEventEvent);
        themeEventDispatcherService.dispatchToServices(homeThemeEventEvent);
        log.info("Done processing event: {}", homeThemeEventEvent);
        acknowledgment.acknowledge();
    }

}
