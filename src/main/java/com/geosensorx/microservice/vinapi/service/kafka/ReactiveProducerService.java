package com.geosensorx.microservice.vinapi.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.geosensorx.microservice.vinapi.model.VinConfigRequest;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Service
public class ReactiveProducerService {

	private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);
	private final ReactiveKafkaProducerTemplate<String, VinConfigRequest> reactiveKafkaProducerTemplate;

	@Value(value = "${CONFIG_REQUST_PRODUCER_TOPIC}")
	private String topic;

	public ReactiveProducerService(
			ReactiveKafkaProducerTemplate<String, VinConfigRequest> reactiveKafkaProducerTemplate) {
		this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
	}

	public Mono<SenderResult<Void>> send(VinConfigRequest vinConfigRequest) {
		log.info("send to topic={}, {}={},", topic, VinConfigRequest.class.getSimpleName(), vinConfigRequest);
		return reactiveKafkaProducerTemplate.send(topic, vinConfigRequest).doOnSuccess(senderResult -> log
				.info("sent {} offset : {}", vinConfigRequest, senderResult.recordMetadata().offset()));
	}
}
