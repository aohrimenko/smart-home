package com.smarthome.application.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;

import java.util.Optional;

public class NewTopicDefaultReplication extends NewTopic {

    public NewTopicDefaultReplication(String name, int numPartitions) {
        super(name, Optional.of(numPartitions), Optional.empty());
    }

}