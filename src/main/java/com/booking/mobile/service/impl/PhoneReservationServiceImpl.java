package com.booking.mobile.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.booking.mobile.dto.PhoneBookingRequest;
import com.booking.mobile.entity.PhoneAudit;
import com.booking.mobile.entity.PhoneReservationEntity;
import com.booking.mobile.exception.PhoneServiceException;
import com.booking.mobile.repository.PhoneAuditRepository;
import com.booking.mobile.repository.PhoneReservationRepository;
import com.booking.mobile.service.PhoneReservationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhoneReservationServiceImpl implements PhoneReservationService {

	private final PhoneReservationRepository phoneReservationRepository;
	private final PhoneAuditRepository phoneAuditRepository;

	@Override
	@Transactional
	public PhoneReservationEntity reservePhone(PhoneBookingRequest phoneBookingRequest) {
		PhoneReservationEntity phone = phoneReservationRepository.findByPhoneId(phoneBookingRequest.getPhoneId())
				.orElse(null);
		if (phone != null && phone.isAvailability()) {
			phone.setAvailability(false);
			phone.setBookedAt(LocalDateTime.now());
			phone.setBookedBy(phoneBookingRequest.getBookedBy());
			phoneReservationRepository.save(phone);

			PhoneAudit audit = new PhoneAudit();
			audit.setPhoneId(phone.getPhoneId());
			audit.setAction("reserve");
			audit.setBookedBy(phoneBookingRequest.getBookedBy());
			audit.setTimestamp(LocalDateTime.now());
			phoneAuditRepository.save(audit);

			return phone;
		} else {
			// exception handling flow need to be improved.
			throw new PhoneServiceException("Phone already reserved");
		}
	}

	@Override
	@Transactional
	public PhoneReservationEntity returnPhone(Long phoneId) {
		PhoneReservationEntity phone = phoneReservationRepository.findByPhoneId(phoneId).orElse(null);
		if (phone != null && !phone.isAvailability()) {
			phone.setAvailability(true);
			phone.setBookedAt(null);
			phone.setBookedBy(null);
			phoneReservationRepository.save(phone);

			PhoneAudit audit = new PhoneAudit();
			audit.setPhoneId(phone.getPhoneId());
			audit.setAction("return");
			audit.setTimestamp(LocalDateTime.now());
			phoneAuditRepository.save(audit);

			return phone;
		} else {
			// exception handling flow need to be improved.
			throw new PhoneServiceException("Phone already returned");
		}
	}

	@Override
	public List<PhoneAudit> getLastBookings(Long phoneId, Sort.Direction direction, int page, int size) {
		Sort sort = Sort.by(direction, "timestamp");
		Pageable pageable = PageRequest.of(page, size, sort);
		if (phoneId != null) {
			return phoneAuditRepository.findByPhoneId(phoneId, pageable);
		} else {
			return phoneAuditRepository.findAll(pageable).getContent();
		}
	}

	@Override
	public PhoneReservationEntity findByPhoneId(Long id) {
		return phoneReservationRepository.findByPhoneId(id).get();
	}
}
