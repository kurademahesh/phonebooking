package com.booking.mobile.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDetailsDTO {

    private Long id;
    private String model;
    private boolean availability;
    private LocalDateTime lastBooked;
    private String bookedBy;

    private String technology;
    private String bands2g;
    private String bands3g;
    private String bands4g;
}
