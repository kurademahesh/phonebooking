package com.booking.mobile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "fono")
@Data
public class FonoConfig {

	private String baseUrl;
	private String token;

}
