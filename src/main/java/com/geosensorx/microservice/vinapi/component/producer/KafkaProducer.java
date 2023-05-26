package com.geosensorx.microservice.vinapi.component.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class KafkaProducer {

    private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;
    private final String firstTopic;
    private final String secondTopic;

    public KafkaProducer(ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate, @Value("${kafka.topic.first}") String firstTopic, @Value("${kafka.topic.second}") String secondTopic) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
        this.firstTopic = firstTopic;
        this.secondTopic = secondTopic;
    }

    public Mono<Void> publish(String topic, String vin) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, vin);
        return reactiveKafkaProducerTemplate.send(record).then();
    }

    // Getter for firstTopic
    public String getFirstTopic() {
        return firstTopic;
    }

    // Getter for firstTopic
    public String getSecondTopic() {
        return secondTopic;
    }
}
