package com.booking.mobile.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phone entity holds phone audit data
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long phoneId;
	private String phoneModel;
	private String action;
	private String bookedBy;
	private LocalDateTime timestamp;

}
