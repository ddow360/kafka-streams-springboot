package com.dow.design.springboot.kstream.api.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.*;

import static com.dow.design.springboot.kstream.api.util.Constants.INPUT_TOPIC;
import static com.dow.design.springboot.kstream.api.util.Constants.OUTPUT_TOPIC;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        return new KafkaStreamsConfiguration(Map.of(
                StreamsConfig.APPLICATION_ID_CONFIG, "stream-api-id",
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest",
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName()
        ));
    }

    @Bean
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
