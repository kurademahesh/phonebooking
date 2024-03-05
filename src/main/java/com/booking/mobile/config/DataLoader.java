package com.booking.mobile.config;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.booking.mobile.dto.FonoApiResponse;
import com.booking.mobile.entity.PhoneEntity;
import com.booking.mobile.entity.PhoneReservationEntity;
import com.booking.mobile.repository.PhoneRepository;
import com.booking.mobile.repository.PhoneReservationRepository;
import com.booking.mobile.service.PhoneInfoService;

import lombok.extern.slf4j.Slf4j;

/*
 * Load required data in database
 */
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

	@Autowired
	private PhoneRepository phoneRepository;

	@Autowired
	private PhoneReservationRepository phoneReservationRepository;

	@Autowired
	PhoneInfoService phoneInfoService;

	@Override
	public void run(String... args) {
		initialisePhoneEntity();
	}

	// This is scheduled job which refresh the data from external API at configured
	// schedule.
	// TODO: cron need to put in configuration

	// TODO: Instead of traveling and updating all records, can set flag for new
	// records and update only those records via cron

	@Scheduled(cron = "1 * * * * *")
	public void loadExtraPhoneDetails() {
		log.info("updating data");
		List<PhoneEntity> phoneEntities = phoneRepository.findAll();

		for (PhoneEntity phoneEntity : phoneEntities) {
			try {
				FonoApiResponse phoneDetails = phoneInfoService.getPhoneDetails(phoneEntity.getModel());
				
				log.info("Phone details {}", phoneDetails);
				if (!Objects.isNull(phoneDetails)) {
					phoneEntity.setTechnology(phoneDetails.getTechnology());
					phoneEntity.setBands2g(phoneDetails.get_2g_bands());
					phoneEntity.setBands3g(phoneDetails.get_3g_bands());
					phoneEntity.setBands4g(phoneDetails.get_4g_bands());

					phoneRepository.save(phoneEntity);
				}

			} catch (Exception e) {
				// Unable to get details from fono api
				log.error("Error occured while fetching phone data from Fono Service", e);
			}

		}
	}

	/**
	 * Load initial data into the phone table and phone reservation table
	 * 
	 * TODO: For high volume application, instead of loading data at application
	 * start, it should be part of dev-ops process
	 */
	private void initialisePhoneEntity() {

		PhoneEntity phone = phoneRepository.save(new PhoneEntity("Samsung Galaxy S9"));
		PhoneReservationEntity phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Samsung Galaxy S8"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Samsung Galaxy S8"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Motorola Nexus 6"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Oneplus 9"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Apple iPhone 13"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Apple iPhone 12"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Apple iPhone 11"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("iPhone X"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);

		phone = phoneRepository.save(new PhoneEntity("Nokia 3310"));
		phoneReservationEntity = new PhoneReservationEntity();
		phoneReservationEntity.setPhoneId(phone.getId());
		phoneReservationRepository.save(phoneReservationEntity);
	}
}
