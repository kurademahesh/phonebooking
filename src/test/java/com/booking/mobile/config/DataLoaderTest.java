package com.booking.mobile.config;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.mobile.dto.FonoApiResponse;
import com.booking.mobile.entity.PhoneEntity;
import com.booking.mobile.repository.PhoneRepository;
import com.booking.mobile.repository.PhoneReservationRepository;
import com.booking.mobile.service.PhoneInfoService;

@ExtendWith(MockitoExtension.class)
public class DataLoaderTest {

	@Mock
	private PhoneRepository phoneRepository;

	@Mock
	private PhoneReservationRepository phoneReservationRepository;

	@Mock
	PhoneInfoService phoneInfoService;

	@InjectMocks
	private DataLoader dataLoader;

	@Test
	void testLoadExtraPhoneDetails() {
		// Mocking phoneEntities
		List<PhoneEntity> phoneEntities = new ArrayList<>();
		PhoneEntity phoneEntity1 = new PhoneEntity();
		phoneEntity1.setModel("model1");
		phoneEntities.add(phoneEntity1);

		// Mocking phoneDetails
		FonoApiResponse phoneDetails = new FonoApiResponse();
		phoneDetails.setTechnology("4G");
		phoneDetails.set_2g_bands("2G Bands");
		phoneDetails.set_3g_bands("3G Bands");
		phoneDetails.set_4g_bands("4G Bands");

		when(phoneRepository.findAll()).thenReturn(phoneEntities);
		when(phoneInfoService.getPhoneDetails("model1")).thenReturn(phoneDetails);

		// Call the method to test
		dataLoader.loadExtraPhoneDetails();

		// Verifying if save method is called with updated phoneEntity
		verify(phoneRepository).save(phoneEntity1);
	}

}
