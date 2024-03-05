package com.booking.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneBookingRequest {

	private Long phoneId;
	private String bookedBy;

}
