package com.booking.mobile.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.booking.mobile.dto.PhoneDetailsDTO;
import com.booking.mobile.entity.PhoneAudit;
import com.booking.mobile.entity.PhoneEntity;
import com.booking.mobile.entity.PhoneReservationEntity;
import com.booking.mobile.exception.PhoneServiceException;
import com.booking.mobile.repository.PhoneAuditRepository;
import com.booking.mobile.repository.PhoneRepository;
import com.booking.mobile.service.PhoneReservationService;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceTest {

	@Mock
	private PhoneRepository phoneRepository;

	@Mock
	private PhoneReservationService phoneReservationService;

	@Mock
	PhoneAuditRepository phoneAuditRepository;

	@InjectMocks
	private PhoneServiceImpl phoneService;

	@Test
	public void testGetAllPhones() {
		// Mock data
		int page = 0;
		int size = 10;
		Pageable pageable = PageRequest.of(page, size);
		when(phoneRepository.findAll(pageable))
				.thenReturn(new PageImpl<>(List.of(new PhoneEntity(), new PhoneEntity())));

		// Call the method under test
		List<PhoneEntity> phones = phoneService.getAllPhones(page, size);

		// Verify the result
		assertEquals(2, phones.size());
	}

	@Test
	public void testGetPhoneDetails_WithExistingPhoneId() {
		// Mock data
		Long phoneId = 1L;
		PhoneEntity phoneEntity = new PhoneEntity();
		phoneEntity.setId(phoneId);
		phoneEntity.setModel("Test Model");
		// Mocking phoneRepository
		when(phoneRepository.findById(phoneId)).thenReturn(Optional.of(phoneEntity));

		// Mocking phoneReservationService
		PhoneReservationEntity phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setBookedBy("Test User");
		phoneReservationEntity.setAvailability(true);
		phoneReservationEntity.setBookedAt(LocalDateTime.now());
		when(phoneReservationService.findByPhoneId(phoneId)).thenReturn(phoneReservationEntity);

		// Call the method under test
		PhoneDetailsDTO phoneDetailsDTO = phoneService.getPhoneDetails(phoneId);

		// Verify the result
		assertEquals(phoneId, phoneDetailsDTO.getId());
		assertEquals("Test Model", phoneDetailsDTO.getModel());
		assertEquals("Test User", phoneDetailsDTO.getBookedBy());
		assertEquals(true, phoneDetailsDTO.isAvailability());
	}

	@Test
	public void testGetPhoneDetails_WithNonExistingPhoneId() {
		// Mock data
		Long phoneId = 1L;
		// Mocking phoneRepository
		when(phoneRepository.findById(phoneId)).thenReturn(Optional.empty());

		assertThrows(PhoneServiceException.class, () -> phoneService.getPhoneDetails(phoneId),
				"Expected doThing() to throw, but it didn't");

	}

	@Test
    void testGetPhoneAudits() {
        Long phoneId = 1L;
        Sort.Direction direction = Sort.Direction.DESC;
        int page = 0;
        int size = 10;
        Sort sort = Sort.by(direction, "timestamp");
		Pageable pageable = PageRequest.of(page, size, sort);

        List<PhoneAudit> phoneAuditList = new ArrayList<>();
        phoneAuditList.add(new PhoneAudit());
        phoneAuditList.add(new PhoneAudit());
        
        when(phoneAuditRepository.findByPhoneId(phoneId, pageable)).thenReturn(phoneAuditList);
        
        List<PhoneAudit> phoneAudits = phoneService.getPhoneAudits(phoneId, direction, page, size);

        assertNotNull(phoneAudits);
        
        assertTrue(phoneAudits.size() > 0);

    }
}
