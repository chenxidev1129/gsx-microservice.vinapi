package com.geosensorx.microservice.vinapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VinConfig {
	private String vin;
	private String configJson;
}
