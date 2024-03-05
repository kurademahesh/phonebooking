package com.booking.mobile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.mobile.dto.ApplicationResponse;
import com.booking.mobile.dto.PhoneBookingRequest;
import com.booking.mobile.dto.PhoneDetailsDTO;
import com.booking.mobile.entity.PhoneAudit;
import com.booking.mobile.entity.PhoneEntity;
import com.booking.mobile.entity.PhoneReservationEntity;
import com.booking.mobile.service.PhoneReservationService;
import com.booking.mobile.service.PhoneService;

import lombok.extern.slf4j.Slf4j;

/**
 * Phone controller is used to handle reservation and phone return
 * functionality.
 */

@RestController
@RequestMapping("/phones")
@Slf4j
public class PhoneController {

	@Autowired
	private PhoneService phoneService;

	@Autowired
	private PhoneReservationService phoneReservationService;

	/**
	 * Get phone details by phoneId
	 * 
	 * @param phoneId
	 * @return PhoneDetailsDTO
	 */
	@GetMapping("/{phoneId}")
	public ApplicationResponse<PhoneDetailsDTO> getPhoneDetails(@PathVariable Long phoneId) {
		log.info("getPhoneDetails called for id {}", phoneId);
		PhoneDetailsDTO phoneDetailsDTO = phoneService.getPhoneDetails(phoneId);
		if (phoneDetailsDTO != null) {
			return ApplicationResponse.success(phoneDetailsDTO);
		} else {
			return ApplicationResponse.error("Error occured while retrieving phone details");
		}
	}

	/**
	 * Get all phones
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping
	public ApplicationResponse<List<PhoneEntity>> getAllPhones(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.info("getAllPhones called");
		return ApplicationResponse.success(phoneService.getAllPhones(page, size));
	}

	/**
	 * Reserve phone
	 *
	 * @param phoneBookingRequest
	 * @return
	 */
	@PostMapping("/reserve")
	public ApplicationResponse<PhoneReservationEntity> reservePhone(@RequestBody PhoneBookingRequest phoneBookingRequest) {
		log.info("reservePhone called :  {}", phoneBookingRequest);
		PhoneReservationEntity reserveredPhone = phoneReservationService.reservePhone(phoneBookingRequest);
		return ApplicationResponse.success(reserveredPhone);
	}

	/**
	 * return phone. Since it accept only one parameter as input kept the
	 * 
	 * @param phoneId
	 * @return
	 */
	@PostMapping("/{phoneId}/return")
	public ApplicationResponse<PhoneReservationEntity> returnPhone(@PathVariable Long phoneId) {
		log.info("returnPhone called :  {}", phoneId);
		PhoneReservationEntity returnedPhone = phoneReservationService.returnPhone(phoneId);
		return ApplicationResponse.success(returnedPhone);
	}

	/**
	 * Retrieve audits of the phone.
	 * 
	 * @param phoneId
	 * @param direction
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/audits/{phoneId}")
	public ApplicationResponse<List<PhoneAudit>> getPhoneAudits(
			@PathVariable Long phoneId,
			@RequestParam(defaultValue = "DESC") Sort.Direction direction, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ApplicationResponse.success(phoneService.getPhoneAudits(phoneId, direction, page, size));
	}

}
