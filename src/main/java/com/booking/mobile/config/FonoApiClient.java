package com.booking.mobile.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.mobile.dto.FonoApiResponse;

/**
 * Fono Api Client to fetch phone model data
 */
@FeignClient(name = "fono-api-client", url = "${fono.baseUrl}")
public interface FonoApiClient {
	
    @GetMapping("/v1/getdevice")
    List<FonoApiResponse> getPhoneDetails(@RequestParam("token") String token, @RequestParam("device") String device);
}
