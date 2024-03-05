package com.booking.mobile.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.booking.mobile.dto.PhoneDetailsDTO;
import com.booking.mobile.entity.PhoneAudit;
import com.booking.mobile.entity.PhoneEntity;
import com.booking.mobile.entity.PhoneReservationEntity;
import com.booking.mobile.exception.PhoneServiceException;
import com.booking.mobile.repository.PhoneAuditRepository;
import com.booking.mobile.repository.PhoneRepository;
import com.booking.mobile.service.PhoneReservationService;
import com.booking.mobile.service.PhoneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

	private final PhoneRepository phoneRepository;
	private final PhoneReservationService phoneReservationService;
	private final PhoneAuditRepository phoneAuditRepository;

	@Override
	public List<PhoneEntity> getAllPhones(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return phoneRepository.findAll(pageable).getContent();
	}

	@Override
	public PhoneDetailsDTO getPhoneDetails(Long phoneId) {
		log.info("getPhoneDetails called for id {}", phoneId);
		PhoneEntity phone = phoneRepository.findById(phoneId).orElseThrow(PhoneServiceException::new);
		if (phone != null) {
			PhoneReservationEntity phoneReservation = phoneReservationService.findByPhoneId(phone.getId());
			PhoneDetailsDTO phoneDetailsDTO = new PhoneDetailsDTO();

			phoneDetailsDTO.setId(phoneId);
			phoneDetailsDTO.setModel(phone.getModel());
			phoneDetailsDTO.setTechnology(phone.getTechnology());
			phoneDetailsDTO.setBands2g(phone.getBands2g());
			phoneDetailsDTO.setBands3g(phone.getBands3g());
			phoneDetailsDTO.setBands4g(phone.getBands4g());
			phoneDetailsDTO.setBookedBy(phoneReservation.getBookedBy());
			phoneDetailsDTO.setAvailability(phoneReservation.isAvailability());
			phoneDetailsDTO.setLastBooked(phoneReservation.getBookedAt());

			return phoneDetailsDTO;
		} else {
			throw new PhoneServiceException("Phone with id " + phoneId + " not found.");
		}
	}

	@Override
	public List<PhoneAudit> getPhoneAudits(Long phoneId, Sort.Direction direction, int page, int size) {
		Sort sort = Sort.by(direction, "timestamp");
		Pageable pageable = PageRequest.of(page, size, sort);
		return phoneAuditRepository.findByPhoneId(phoneId, pageable);
	}
}
