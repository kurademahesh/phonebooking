package com.booking.mobile.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.mobile.config.FonoApiClient;
import com.booking.mobile.config.FonoConfig;
import com.booking.mobile.dto.FonoApiResponse;

/**
 * test class for FonoApiPhoneInfoService
 * 
 * This test class test basic code coverage
 */
@ExtendWith(MockitoExtension.class)
class FonoApiPhoneInfoServiceTest {

	@Mock
	private FonoApiClient fonoApi;

	@Mock
	FonoConfig fonoConfig;

	@InjectMocks
	private FonoApiPhoneInfoService fonoApiPhoneInfoService;

	@Test
	void testGetPhoneDetails_Success() throws IOException {
		// Arrange
		String model = "Apple 15";
		List<FonoApiResponse> list = new ArrayList<>();
		list.add(new FonoApiResponse());

		when(fonoConfig.getToken()).thenReturn("token");
		when(fonoApi.getPhoneDetails(anyString(), anyString())).thenReturn(list);

		FonoApiResponse actualResponse = fonoApiPhoneInfoService.getPhoneDetails(model);

		assertNotNull(actualResponse);

	}

	@Test
	void testGetPhoneDetails_UnsuccessfulResponse() throws IOException {
		// Arrange
		String model = "Apple 15";

		when(fonoConfig.getToken()).thenReturn("token");

		when(fonoApi.getPhoneDetails(anyString(), anyString())).thenThrow(new RuntimeException("message"));

		assertThrows(RuntimeException.class, () -> fonoApiPhoneInfoService.getPhoneDetails(model),
				"Expected doThing() to throw, but it didn't");

	}
}