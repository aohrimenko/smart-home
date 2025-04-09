package com.smarthome.application.consumer;

import com.smarthome.application.eventData.MusicChangeEvent;
import com.smarthome.application.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MusicChangeEventConsumer {

    private final MusicService musicService;

    @KafkaListener(topics = "music-topic", groupId = "music-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(MusicChangeEvent event, Acknowledgment acknowledgment) {
        log.info("Processing event: {}", event);
        musicService.processEvent(event);
        log.info("Done processing event: {}", event);
        acknowledgment.acknowledge();
    }

}
