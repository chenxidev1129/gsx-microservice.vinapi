package com.geosensorx.microservice.vinapi.service.vendor;

import com.geosensorx.microservice.vinapi.model.vendor.request.RetrieveConfigVendorRequest;
import com.geosensorx.microservice.vinapi.model.vendor.response.ConfigVendorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VendorApiService {

    private final WebClient webClient;

    @Value("${vendor.api.url}")
    private String vendorApiUrl;

    @Value("${vendor.api.token}")
    private String vendorApiToken;

    public VendorApiService() {
        this.webClient = WebClient.builder()
                .baseUrl(vendorApiUrl)
                .defaultHeader("Authorization", "Bearer " + vendorApiToken)
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

    //    Find Config with FindVinRequest
    public Mono<ConfigVendorResponse> retrieveConfigFromVendor(String vin) {
        RetrieveConfigVendorRequest request = new RetrieveConfigVendorRequest(vin);

        return webClient.post()
                .uri("/endpoint")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(ConfigVendorResponse.class);
    }
}
