package com.smarthome.application.config.kafka;


import com.smarthome.application.eventData.MusicChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(
        value = "kafka.enabled",
        havingValue = "true",
        matchIfMissing = true)
@Slf4j
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value(value = "${kafka.max-attempts}")
    private Long maxAttempts;

    @Value(value = "${kafka.pooling-interval}")
    private Long poolingInterval;

    @Bean
    public ConsumerFactory<String, MusicChangeEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        JsonDeserializer<MusicChangeEvent> valueDeserializer = new JsonDeserializer<>(MusicChangeEvent.class);
        valueDeserializer.setRemoveTypeHeaders(true);
        valueDeserializer.addTrustedPackages("*");


        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MusicChangeEvent> kafkaListenerContainerFactory(ConsumerFactory<String, MusicChangeEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, MusicChangeEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler());
        factory.getContainerProperties().setObservationEnabled(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }

    @Bean
    @DependsOn({"messageHandlerMethodFactory"})
    public KafkaListenerEndpointRegistrar kafkaListenerEndpointRegistrar(ConcurrentKafkaListenerContainerFactory<String, MusicChangeEvent> kafkaListenerContainerFactory,
                                                                         KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry) {
        KafkaListenerEndpointRegistrar endpointRegistrar = new KafkaListenerEndpointRegistrar();
        endpointRegistrar.setContainerFactory(kafkaListenerContainerFactory);
        endpointRegistrar.setEndpointRegistry(kafkaListenerEndpointRegistry);

        return endpointRegistrar;
    }

    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        return new DefaultMessageHandlerMethodFactory();
    }

    @Bean
    public KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry() {
        return new KafkaListenerEndpointRegistry();
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        return new DefaultErrorHandler((rec, ex) ->
                log.error("Failed to consume message: {}. Reason: {}", rec, ex.getMessage(), ex),
                new FixedBackOff(poolingInterval, maxAttempts)
        );
    }

}
