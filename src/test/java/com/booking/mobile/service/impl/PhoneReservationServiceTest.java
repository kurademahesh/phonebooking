package com.booking.mobile.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.booking.mobile.dto.PhoneBookingRequest;
import com.booking.mobile.entity.PhoneAudit;
import com.booking.mobile.entity.PhoneReservationEntity;
import com.booking.mobile.exception.PhoneServiceException;
import com.booking.mobile.repository.PhoneAuditRepository;
import com.booking.mobile.repository.PhoneReservationRepository;

@ExtendWith(MockitoExtension.class)
public class PhoneReservationServiceTest {

	@Mock
	private PhoneReservationRepository phoneReservationRepository;

	@Mock
	private PhoneAuditRepository phoneAuditRepository;

	@InjectMocks
	private PhoneReservationServiceImpl phoneReservationService;

	@Test
	public void testReservePhone_SuccessfulReservation() {
		// Mock data
		Long phoneId = 1L;
		String bookedBy = "testUser";
		PhoneBookingRequest phoneBookingRequest = new PhoneBookingRequest(phoneId, bookedBy);
		PhoneReservationEntity phoneReservation = new PhoneReservationEntity();
		phoneReservation.setPhoneId(phoneId);
		phoneReservation.setAvailability(true);
		when(phoneReservationRepository.findByPhoneId(phoneId)).thenReturn(Optional.of(phoneReservation));

		// Call the method under test
		PhoneReservationEntity result = phoneReservationService.reservePhone(phoneBookingRequest);

		// Verify the result
		assertEquals(phoneReservation, result);
		assertEquals(false, phoneReservation.isAvailability());
		assertEquals(bookedBy, phoneReservation.getBookedBy());

		// Verify interactions with mocks
		verify(phoneReservationRepository).save(phoneReservation);
		verify(phoneAuditRepository).save(any());
	}

	@Test
	public void testReservePhone_UnsuccessfulReservation() {
		// Mock data
		Long phoneId = 1L;
		PhoneBookingRequest phoneBookingRequest = new PhoneBookingRequest(phoneId, "testUser");
		PhoneReservationEntity phoneReservation = new PhoneReservationEntity();
		phoneReservation.setPhoneId(phoneId);
		phoneReservation.setAvailability(false); // Phone not available
		when(phoneReservationRepository.findByPhoneId(phoneId)).thenReturn(Optional.of(phoneReservation));

		// Call the method under test
		assertThrows(PhoneServiceException.class, () -> phoneReservationService.reservePhone(phoneBookingRequest),
				"Expected doThing() to throw, but it didn't");

		// Verify interactions with mocks
		verify(phoneReservationRepository).findByPhoneId(phoneId);
		verify(phoneReservationRepository, never()).save(phoneReservation); // Should not save if reservation is
																			// unsuccessful
		verify(phoneAuditRepository, never()).save(any()); // No audit should be saved
	}

	@Test
	public void testReturnPhone_Success() {
		// Mock data
		Long phoneId = 1L;
		PhoneReservationEntity phone = new PhoneReservationEntity();
		phone.setId(1L);
		phone.setAvailability(false);

		when(phoneReservationRepository.findByPhoneId(phoneId)).thenReturn(Optional.of(phone));
		when(phoneReservationRepository.save(any(PhoneReservationEntity.class))).thenReturn(phone);

		PhoneReservationEntity result = phoneReservationService.returnPhone(phoneId);

		assertEquals(true, result.isAvailability());
		assertNull(result.getBookedAt());
		assertNull(result.getBookedBy());

		verify(phoneReservationRepository).findByPhoneId(phoneId);
		verify(phoneReservationRepository).save(any(PhoneReservationEntity.class));
		verify(phoneAuditRepository).save(any(PhoneAudit.class));
	}

	@Test
	public void testReturnPhone_WhenPhoneIsUnavailable() {
		// Mock data
		Long phoneId = 1L;
		PhoneReservationEntity phone = new PhoneReservationEntity();
		phone.setId(1L);
		phone.setAvailability(true);

		when(phoneReservationRepository.findByPhoneId(phoneId)).thenReturn(Optional.of(phone));
		assertThrows(PhoneServiceException.class, () -> phoneReservationService.returnPhone(phoneId),
				"Expected doThing() to throw, but it didn't");

		verify(phoneReservationRepository).findByPhoneId(phoneId);
	}

	@Test
    void testGetLastBookings_WithPhoneId() {
        Long phoneId = 1L;
        Sort.Direction direction = Sort.Direction.DESC;
        int page = 0;
        int size = 10;

        List<PhoneAudit> phoneAuditList = new ArrayList<>();
        phoneAuditList.add(new PhoneAudit());
        phoneAuditList.add(new PhoneAudit());

        when(phoneAuditRepository.findByPhoneId(phoneId, PageRequest.of(page, size, Sort.by(direction, "timestamp")))).thenReturn(phoneAuditList);

        List<PhoneAudit> result = phoneReservationService.getLastBookings(phoneId, direction, page, size);

        assertEquals(phoneAuditList.size(), result.size());
        verify(phoneAuditRepository).findByPhoneId(phoneId, PageRequest.of(page, size, Sort.by(direction, "timestamp")));
    }

    @Test
    void testGetLastBookings_WithoutPhoneId() {
        Long phoneId = null;
        Sort.Direction direction = Sort.Direction.DESC;
        int page = 0;
        int size = 10;

        List<PhoneAudit> phoneAuditList = new ArrayList<>();
        phoneAuditList.add(new PhoneAudit());
        phoneAuditList.add(new PhoneAudit());

        Page<PhoneAudit> phoneAuditPage = new PageImpl<>(phoneAuditList);

        when(phoneAuditRepository.findAll(PageRequest.of(page, size, Sort.by(direction, "timestamp"))))
            .thenReturn(phoneAuditPage);

        List<PhoneAudit> result = phoneReservationService.getLastBookings(phoneId, direction, page, size);

        assertEquals(phoneAuditList.size(), result.size());
        verify(phoneAuditRepository).findAll(PageRequest.of(page, size, Sort.by(direction, "timestamp")));
    }
	
	@Test
	public void testfindByPhoneId() {

		Long phoneId = 1L;
		PhoneReservationEntity phone = new PhoneReservationEntity();
		phone.setId(phoneId);
		phone.setAvailability(true);

		when(phoneReservationRepository.findByPhoneId(anyLong())).thenReturn(Optional.of(phone));

		PhoneReservationEntity phoneReservationEntity = phoneReservationService.findByPhoneId(phoneId);

		assertNotNull(phoneReservationEntity);
		verify(phoneReservationRepository).findByPhoneId(phoneId);
	}

}
