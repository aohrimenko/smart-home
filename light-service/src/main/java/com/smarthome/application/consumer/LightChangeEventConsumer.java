package com.smarthome.application.consumer;

import com.smarthome.application.eventData.LightChangeEvent;
import com.smarthome.application.service.LightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LightChangeEventConsumer {

    private final LightService lightService;

    @KafkaListener(topics = "light-topic", groupId = "light-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(LightChangeEvent event, Acknowledgment acknowledgment) {
        log.info("Processing event: {}", event);
        lightService.processEvent(event);
        log.info("Done processing event: {}", event);
        acknowledgment.acknowledge();
    }

}
