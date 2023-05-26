package com.geosensorx.microservice.vinapi.model.kafka.request;

public class VinRequest {
    private String vin;

    public VinRequest(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
