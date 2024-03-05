package com.booking.mobile.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.mobile.dto.ApplicationResponse;
import com.booking.mobile.dto.PhoneDetailsDTO;
import com.booking.mobile.service.PhoneService;

@ExtendWith(MockitoExtension.class)
public class PhoneControllerTest {

	@Mock
	private PhoneService phoneService;

	@InjectMocks
	private PhoneController phoneController;

	@BeforeEach
	public void setUp() {
		// Setup any necessary mocks or data before each test case
	}

	@Test
	public void testGetPhoneDetails_WithValidPhoneId_ReturnsOK() {
		// Mock data
		Long phoneId = 1L;
		PhoneDetailsDTO phoneDetailsDTO = new PhoneDetailsDTO();
		phoneDetailsDTO.setId(phoneId);
		when(phoneService.getPhoneDetails(phoneId)).thenReturn(phoneDetailsDTO);

		// Call the method under test
		ApplicationResponse<PhoneDetailsDTO> responseEntity = phoneController.getPhoneDetails(phoneId);

		// Verify the result
		assertEquals(phoneDetailsDTO, responseEntity.getData());

		// Verify interactions with mocks
		verify(phoneService).getPhoneDetails(phoneId);
	}

	@Test
	public void testGetPhoneDetails_WithInvalidPhoneId_ReturnsNotFound() {
		// Mock data
		Long phoneId = 2L;
		when(phoneService.getPhoneDetails(phoneId)).thenReturn(null);

		ApplicationResponse<PhoneDetailsDTO> responseEntity = phoneController.getPhoneDetails(phoneId);

		// Verify the result
		PhoneDetailsDTO response = responseEntity.getData();

		assertNull(responseEntity.getData());
		assertEquals(false, responseEntity.isSuccess());

		// Verify interactions with mocks
		verify(phoneService).getPhoneDetails(phoneId);
	}
	


}