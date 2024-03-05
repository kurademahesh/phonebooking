package com.booking.mobile.exception;

public class PhoneServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PhoneServiceException() {

	}

	public PhoneServiceException( String message) {
		super(message);
	}

}
