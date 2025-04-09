package com.smarthome.application.producer;

import com.smarthome.application.config.HomeThemeConfiguration;
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
public class ThemeDispatcherEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${kafka.topic.home_theme.name}")
    private String topicName;

    public void sendThemeDeviceSettings(HomeThemeConfiguration homeThemeConfiguration) {

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, homeThemeConfiguration);

        future.thenAccept((result) -> {
            log.info("ThemeDispatcherEventProducer: Successfully sent device data: {}", homeThemeConfiguration);
        }).exceptionally(ex -> {
            log.error("ThemeDispatcherEventProducer: Unable to send device data: {}", homeThemeConfiguration, ex);
            return null;
        });
    }

}
