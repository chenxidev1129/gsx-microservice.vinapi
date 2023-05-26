package com.geosensorx.microservice.vinapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geosensorx.microservice.vinapi.model.VinConfigRequest;
import com.geosensorx.microservice.vinapi.service.kafka.ReactiveProducerService;

import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Service
public class VinRequestServiceDefault implements VinRequestService {

	@Autowired
	private ReactiveProducerService kafkaProducer;
	
	
	@Override
	public Mono<SenderResult<Void>> requestDeviceConfigByVin(VinConfigRequest vinRequest) {
		return kafkaProducer.send(vinRequest);
		// return Mono.empty();
	}

	@Override
	public Mono<String> getOk() {
		return Mono.<String>create(sink -> {
			sink.success("You are Ok.");
		});
	}

}
