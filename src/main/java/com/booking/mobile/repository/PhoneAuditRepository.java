package com.booking.mobile.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.mobile.entity.PhoneAudit;

@Repository
public interface PhoneAuditRepository extends JpaRepository<PhoneAudit, Long> {

    List<PhoneAudit> findByPhoneId(Long phoneId, Pageable pageable);

}
