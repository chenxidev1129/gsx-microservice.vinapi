package com.geosensorx.microservice.vinapi.component.consumer;

import com.geosensorx.microservice.vinapi.component.producer.KafkaProducer;
import com.geosensorx.microservice.vinapi.model.external.response.SendVinExternalResponse;
import com.geosensorx.microservice.vinapi.service.external.ExternalApiService;
import org.apache.kafka.common.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FirstKafkaConsumer {

    private final ExternalApiService externalApiService;
    private final KafkaProducer kafkaProducer;
    private final String firstTopic;

    @Autowired
    public FirstKafkaConsumer(ExternalApiService externalApiService, KafkaProducer kafkaProducer, @Value("${kafka.topic.first}") String firstTopic) {
        this.externalApiService = externalApiService;
        this.kafkaProducer = kafkaProducer;
        this.firstTopic = firstTopic;
    }

    @KafkaListener(topics = "${kafka.topic.first}", groupId = "${kafka.consumer.group}")
    public Mono<Void> process(String vin) {
        // Look up config in MongoDB using vin as key
        Mono<Config> configMono = Mono.empty(); // Retrieve config from MongoDB based on vin

        return configMono.flatMap(config -> {
            // Call external API to send vin
            Mono<SendVinExternalResponse> apiResultMono = externalApiService.sendVin(vin);

            return apiResultMono.then();
        }).switchIfEmpty(Mono.defer(() -> {
            // Publish request to another Kafka topic if config not found
            return kafkaProducer.publish("second-topic", vin);
        }));
    }
}
