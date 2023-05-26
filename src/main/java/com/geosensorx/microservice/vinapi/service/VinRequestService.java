package com.geosensorx.microservice.vinapi.service;

import com.geosensorx.microservice.vinapi.model.VinConfigRequest;

import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

public interface VinRequestService {

	Mono<String> getOk();
	Mono<SenderResult<Void>> requestDeviceConfigByVin(VinConfigRequest vinRequest);
}
