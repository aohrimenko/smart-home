package com.smarthome.application.producer;

import com.smarthome.application.eventData.ChangeMusicEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangeMusicEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${kafka.topic.music_change.name}")
    private String themeName;

    public void sendEvent(final ChangeMusicEvent event) {

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(themeName, event);

        future.thenAccept((result) -> {
            log.info("ChangeMusicEventProducer: Successfully sent device data: {}", event);
        }).exceptionally(ex -> {
            log.error("ChangeMusicEventProducer: Unable to send device data: {}", event, ex);
            return null;
        });
    }

}
