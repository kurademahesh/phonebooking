package com.booking.mobile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phone entity holds phone resource data
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String model;
	private String technology;
	private String bands2g;
	private String bands3g;
	private String bands4g;

	public PhoneEntity(String model) {
		this.model = model;
	}

}
