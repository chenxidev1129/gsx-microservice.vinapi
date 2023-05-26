package com.geosensorx.microservice.vinapi.controller;

import com.geosensorx.microservice.vinapi.service.VinRequestServiceDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.geosensorx.microservice.vinapi.model.VinConfigRequest;
import com.geosensorx.microservice.vinapi.service.VinRequestService;

import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@RestController
@RequestMapping("/vin")
public class VinAPIController {

	@Autowired
	private VinRequestServiceDefault vinRequestService;

	@GetMapping("/ok")
	@ResponseStatus(HttpStatus.OK)
	public Mono<String> ok() {
		return vinRequestService.getOk();
	}

	@PostMapping("/send")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Mono<SenderResult<Void>> sendVinConfigRequest(@RequestBody VinConfigRequest vinConfigRequest) {
		return vinRequestService.requestDeviceConfigByVin(vinConfigRequest);
	}
}
