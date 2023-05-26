package com.geosensorx.microservice.vinapi.model.vendor.request;

public class RetrieveConfigVendorRequest {

    private String vin;

    public RetrieveConfigVendorRequest(String vin) {
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
