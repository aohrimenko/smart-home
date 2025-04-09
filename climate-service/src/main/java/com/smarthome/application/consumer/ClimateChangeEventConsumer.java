package com.smarthome.application.consumer;

import com.smarthome.application.eventData.ClimateChangeEvent;
import com.smarthome.application.service.ClimateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClimateChangeEventConsumer {

    private final ClimateService climateService;

    @KafkaListener(topics = "climate-topic", groupId = "climate-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(ClimateChangeEvent event, Acknowledgment acknowledgment) {
        log.info("Processing event: {}", event);
        climateService.processEvent(event);
        log.info("Done processing event: {}", event);
        acknowledgment.acknowledge();
    }

}
