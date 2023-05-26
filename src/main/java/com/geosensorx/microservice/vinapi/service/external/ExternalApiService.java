package com.geosensorx.microservice.vinapi.service.external;

import com.geosensorx.microservice.vinapi.model.external.request.SendVinExternalRequest;
import com.geosensorx.microservice.vinapi.model.external.response.SendVinExternalResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiService {

    private final WebClient webClient;

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Value("${external.api.token}")
    private String externalApiToken;

    public ExternalApiService() {
        this.webClient = WebClient.builder()
                .baseUrl(externalApiUrl)
                .defaultHeader("Authorization", "Bearer " + externalApiToken)
                .build();
    }

    //    GET Request
    public Mono<String> getExternalData() {
        return webClient.get()
                .uri("/endpoint")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    //    POST Request
    public Mono<String> postExternalData(String requestPayload) {
        return webClient.post()
                .uri("/endpoint")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestPayload))
                .retrieve()
                .bodyToMono(String.class);
    }

    //    Send Vin Request
    public Mono<SendVinExternalResponse> sendVin(String vin) {
        SendVinExternalRequest request = new SendVinExternalRequest(vin);

        return webClient.post()
                .uri("/endpoint")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(SendVinExternalResponse.class);
    }
}
