package com.geosensorx.microservice.vinapi.config;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

import com.geosensorx.microservice.vinapi.model.VinConfigRequest;

@Configuration
public class ReactiveKafkaProducerConfig {
	@Bean
	public ReactiveKafkaProducerTemplate<String, VinConfigRequest> reactiveKafkaProducerTemplate(
			KafkaProperties properties) {
		Map<String, Object> props = properties.buildProducerProperties();
		return new ReactiveKafkaProducerTemplate<String, VinConfigRequest>(SenderOptions.create(props));
	}
}
