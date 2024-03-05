package com.booking.mobile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.booking.mobile.dto.ApplicationResponse;
import com.booking.mobile.dto.ErrorRespose;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Application exception handler
 *
 */
@ControllerAdvice
@Slf4j
class GlobalExceptionHandler {

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<ApplicationResponse<ErrorRespose>> handleNotFoundException(
			HttpRequestMethodNotSupportedException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApplicationResponse
				.error(ErrorRespose.builder().errorCode("1001").message("Invalid HTTP Action.").build())); // error code needs to be externalised
	}

	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<ApplicationResponse<ErrorRespose>> handleNoResourceFoundException(NoResourceFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApplicationResponse.error(ErrorRespose.builder().errorCode("1002").message("Invalid URL.").build()));
	}

	@ExceptionHandler(PhoneServiceException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<ApplicationResponse<ErrorRespose>> handlePhoneReservedException(PhoneServiceException ex) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApplicationResponse.error(ErrorRespose.builder().errorCode("1003").message(ex.getMessage()).build()));
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<ErrorRespose> handleGenericException(Exception ex) {
		log.error("Exception occured {}", ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				ErrorRespose.builder().errorCode("9999").message("Internal Server Error. Please try again.").build());
	}

}