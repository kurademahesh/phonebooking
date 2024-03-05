package com.booking.mobile.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorRespose {

	private String errorCode;
	private String message;

}
