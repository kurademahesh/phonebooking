package com.booking.mobile.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.booking.mobile.config.FonoApiClient;
import com.booking.mobile.config.FonoConfig;
import com.booking.mobile.dto.FonoApiResponse;
import com.booking.mobile.service.PhoneInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FonoApiPhoneInfoService implements PhoneInfoService {

	private final FonoApiClient fonoApiClient;
	private final FonoConfig fonoConfig;

	public FonoApiResponse getPhoneDetails(String phoneModel) {
		FonoApiResponse fonoApiResponse = null;
		try {
			List<FonoApiResponse> response = fonoApiClient.getPhoneDetails(fonoConfig.getToken(), phoneModel);

			if (response != null && response.size() > 0) {
				fonoApiResponse = response.get(0);
			} else {
				log.info("Unable to fetch phone detail for model: {]", phoneModel);
			}
		} catch (Exception e) {
			log.error("Error while fetching phone details from fono api", e.getMessage());
			throw e;
		}
		return fonoApiResponse;
	}

}
