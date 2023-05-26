package com.geosensorx.microservice.vinapi;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(topics = {"${VIN_CONFIG_REQUEST_PRODUCER_TOPIC}", "${VIN_CONFIG_REQUEST_CONSUMER_TOPIC}"})
public class KafkaProducerTest {


    @Test
    void testPublishVinConfigProducerTopic() {
        // TODO
    }
}
