package com.booking.mobile.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.booking.mobile.dto.PhoneDetailsDTO;
import com.booking.mobile.entity.PhoneAudit;
import com.booking.mobile.entity.PhoneEntity;

public interface PhoneService {
	/**
	 * Get all phones
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	List<PhoneEntity> getAllPhones(int page, int size);

	/**
	 * Get phone details by phoneId
	 * 
	 * @param phoneId
	 * @return
	 */
	PhoneDetailsDTO getPhoneDetails(Long phoneId);

	/**
	 * 
	 * @param phoneId
	 * @param direction
	 * @param page
	 * @param size
	 * @return
	 */
	List<PhoneAudit> getPhoneAudits(Long phoneId, Sort.Direction direction, int page, int size);

}