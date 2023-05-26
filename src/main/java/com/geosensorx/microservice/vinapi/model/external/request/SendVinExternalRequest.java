package com.geosensorx.microservice.vinapi.model.external.request;

public class SendVinExternalRequest {

    private String vin;

    public SendVinExternalRequest(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "VinRequest{" +
                "vin='" + vin + '\'' +
                '}';
    }
}
