package com.geosensorx.microservice.vinapi.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import com.geosensorx.microservice.vinapi.model.VinConfigRequest;

import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class ReactiveKafkaConsumerConfig {
	@Bean
	public ReceiverOptions<String, VinConfigRequest> kafkaReceiverOptions(
			@Value(value = "${CONFIG_REQUST_CONSUMER_TOPIC}") String topic, KafkaProperties kafkaProperties) {
		ReceiverOptions<String, VinConfigRequest> basicReceiverOptions = ReceiverOptions
				.create(kafkaProperties.buildConsumerProperties());
		return basicReceiverOptions.subscription(Collections.singletonList(topic));
	}

	@Bean
	public ReactiveKafkaConsumerTemplate<String, VinConfigRequest> reactiveKafkaConsumerTemplate(
			ReceiverOptions<String, VinConfigRequest> kafkaReceiverOptions) {
		return new ReactiveKafkaConsumerTemplate<String, VinConfigRequest>(kafkaReceiverOptions);
	}
}
