package com.booking.mobile.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.booking.mobile.dto.PhoneBookingRequest;
import com.booking.mobile.entity.PhoneAudit;
import com.booking.mobile.entity.PhoneReservationEntity;

public interface PhoneReservationService {

	PhoneReservationEntity reservePhone(PhoneBookingRequest phoneBookingRequest);

	PhoneReservationEntity returnPhone(Long phoneId);

	List<PhoneAudit> getLastBookings(Long phoneId, Sort.Direction direction, int page, int size);

	PhoneReservationEntity findByPhoneId(Long id);

}