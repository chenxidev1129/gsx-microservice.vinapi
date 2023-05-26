package com.geosensorx.microservice.vinapi.component.consumer;

import com.geosensorx.microservice.vinapi.component.producer.KafkaProducer;
import com.geosensorx.microservice.vinapi.model.vendor.response.ConfigVendorResponse;
import com.geosensorx.microservice.vinapi.service.external.ExternalApiService;
import com.geosensorx.microservice.vinapi.service.vendor.VendorApiService;
import org.apache.kafka.common.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SecondKafkaConsumer {

    private final VendorApiService vendorApiService;
    private final KafkaProducer kafkaProducer;
    private final String secondTopic;

    @Autowired
    public SecondKafkaConsumer(VendorApiService vendorApiService, KafkaProducer kafkaProducer, @Value("${kafka.topic.second}") String secondTopic) {
        this.vendorApiService = vendorApiService;
        this.kafkaProducer = kafkaProducer;
        this.secondTopic = secondTopic;
    }

    @KafkaListener(topics = "${kafka.topic.second}", groupId = "${kafka.consumer.group}")
    public Mono<Void> processMessage(String vin) {
        // Call external API (vendor) to retrieve config
        Mono<ConfigVendorResponse> configMono = vendorApiService.retrieveConfigFromVendor(vin);

        return configMono.flatMap(config -> {
            // Add the record to MongoDB
            Mono<Void> mongoResultMono = Mono.empty(); // Save the config in MongoDB

            return mongoResultMono.then(Mono.defer(() -> {
                // Publish request to the first queue
                return kafkaProducer.publish("first-topic", vin);
            }));
        });
    }
}
