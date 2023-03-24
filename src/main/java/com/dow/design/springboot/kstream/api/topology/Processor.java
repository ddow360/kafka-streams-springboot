package com.dow.design.springboot.kstream.api.topology;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.dow.design.springboot.kstream.api.util.Constants.*;

@Slf4j
@Component
public class Processor {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {

        // Once you have Spring injected StreamsBuilder you are able to get access to all KStreams
        KStream<String, String> source = streamsBuilder.stream(INPUT_TOPIC);

        source.peek((k, v) -> log.info("Value of raw record coming through: {}", v))
                .mapValues(value -> value.toUpperCase())
                .peek((k, v) -> log.info("New value after transformation: {}", v))
                .to(OUTPUT_TOPIC);

        return source;
    }
}
