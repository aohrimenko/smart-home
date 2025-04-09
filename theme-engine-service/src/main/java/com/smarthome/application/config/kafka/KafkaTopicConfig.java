package com.smarthome.application.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(
        value = "kafka.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value(value = "${kafka.topic.home_theme.name}")
    private String themeDispatcherTopicName;

    @Value(value = "${kafka.topic.home_theme.partitionsCount}")
    private int themeDispatcherPartitionCount;

    @Value(value = "${kafka.topic.climate_change.name}")
    private String climateChangeTopicName;

    @Value(value = "${kafka.topic.climate_change.partitionsCount}")
    private int climateChangePartitionCount;

    @Value(value = "${kafka.topic.light_change.name}")
    private String lightChangeTopicName;

    @Value(value = "${kafka.topic.light_change.partitionsCount}")
    private int lightPartitionCount;

    @Value(value = "${kafka.topic.music_change.name}")
    private String musicChangeTopicName;

    @Value(value = "${kafka.topic.music_change.partitionsCount}")
    private int musicPartitionCount;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic themeDispatcherTopic() {
        return new NewTopicDefaultReplication(themeDispatcherTopicName, themeDispatcherPartitionCount);
    }

    @Bean
    public NewTopic musicChangeTopic() {
        return new NewTopicDefaultReplication(musicChangeTopicName, musicPartitionCount);
    }

    @Bean
    public NewTopic lightChangeTopic() {
        return new NewTopicDefaultReplication(lightChangeTopicName, lightPartitionCount);
    }

    @Bean
    public NewTopic climateChangeTopic() {
        return new NewTopicDefaultReplication(climateChangeTopicName, climateChangePartitionCount);
    }

}
