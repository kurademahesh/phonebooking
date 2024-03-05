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
 * /**
 * Phone entity holds phone reservation data. 
 * Each time when new entry created that entry should be available in this table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneReservationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long phoneId;
	private String bookedBy;
	private LocalDateTime bookedAt;
	private boolean availability = true;

}
