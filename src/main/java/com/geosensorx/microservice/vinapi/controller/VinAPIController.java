package com.geosensorx.microservice.vinapi.controller;

import com.geosensorx.microservice.vinapi.component.producer.KafkaProducer;
import com.geosensorx.microservice.vinapi.model.kafka.request.VinRequest;
import com.geosensorx.microservice.vinapi.service.kafka.ReactiveProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private VinRequestService vinRequestService;

    private final Logger log = LoggerFactory.getLogger(VinAPIController.class);
    private final KafkaProducer kafkaProducer;
    private final String firstTopic;

    public VinAPIController(KafkaProducer kafkaProducer, @Value("${kafka.topic.first}") String firstTopic) {
        this.kafkaProducer = kafkaProducer;
        this.firstTopic = firstTopic;
    }

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

    @PostMapping("/config")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> getVehicleConfig(@RequestBody VinRequest vinRequest) {
        String vin = vinRequest.getVin();

        // Publish the request to the first Kafka topic
        return kafkaProducer.publish(firstTopic, vin);
    }
}
