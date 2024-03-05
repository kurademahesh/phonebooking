package com.booking.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Generic Application Response
 * @param <T>
 */
@Data
@Builder
@AllArgsConstructor
public class ApplicationResponse<T> {
	private boolean success;
	private T data;

	public static <T> ApplicationResponse<T> success(T data) {
		return ApplicationResponse.<T>builder().data(data).success(true).build();
	}

	public static <T> ApplicationResponse<T> success(T data, String message) {
		return ApplicationResponse.<T>builder().data(data).success(true).build();
	}

	public static <T> ApplicationResponse<T> error(String message) {
		return ApplicationResponse.<T>builder().success(false).build();
	}

	public static <T> ApplicationResponse<T> error(T data) {
		return ApplicationResponse.<T>builder().data(data).success(false).build();
	}

}