package com.booking.mobile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.mobile.entity.PhoneReservationEntity;

@Repository
public interface PhoneReservationRepository extends JpaRepository<PhoneReservationEntity, Long> {
    Optional<PhoneReservationEntity> findByPhoneId(Long id);
}
