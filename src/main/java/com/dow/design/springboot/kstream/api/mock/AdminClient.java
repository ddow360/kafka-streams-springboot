package com.dow.design.springboot.kstream.api.mock;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;

import static com.dow.design.springboot.kstream.api.util.Constants.INPUT_TOPIC;
import static com.dow.design.springboot.kstream.api.util.Constants.OUTPUT_TOPIC;

public class AdminClient {
    @Bean
    @Profile("desktop")
    public CommandLineRunner createTopicOnStartup(KafkaAdmin adminClient) {
        return args -> {
            int numPartitions = 3;
            short replicationFactor = 1;
            NewTopic input =  new NewTopic(INPUT_TOPIC, numPartitions, replicationFactor);
            NewTopic output =  new NewTopic(OUTPUT_TOPIC, numPartitions, replicationFactor);
            adminClient.createOrModifyTopics(input, output);
        };
    }
}
