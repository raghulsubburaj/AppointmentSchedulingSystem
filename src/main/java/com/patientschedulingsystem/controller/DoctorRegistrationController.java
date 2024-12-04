package com.patientschedulingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.DoctorRegistrationDAO;
import com.patientschedulingsystem.model.Doctor;
import com.patientschedulingsystem.model.User;

@RestController
@RequestMapping("/doctor")
public class DoctorRegistrationController {

	@Autowired
	private DoctorRegistrationDAO doctorRegistrationDAO;

	@PostMapping("/register")
	public ResponseEntity<String> doctorRegistration(@RequestBody Doctor doctor) {

		try {

			if (doctor.getSpecialization() == null || doctor.getUser().getUserId() == 0) {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter The All Value");

			} else if (doctor.getUser().getUserId() != 0) {

				String role = doctorRegistrationDAO.roleCheck(doctor.getUser().getUserId());

				if (!role.isEmpty() && ("doctor").equals(role)) {
					if (doctorRegistrationDAO.newDoctorRegistrtion(doctor.getSpecialization(),
							doctor.getUser().getUserId())) {
						return ResponseEntity.status(HttpStatus.CREATED).body("Doctor Registration Successfully");
					} else {
						return ResponseEntity.status(HttpStatus.CREATED)
								.body("Doctor Registration Failure After Database Connection");
					}

				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Role Validation failure");
				}

			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Doctor Registration failure");
			}

		}

		catch (Exception e) {
			System.out.println("Doctor Registration failue : " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Doctor Register Exception :" + e);
		}

	}

	
}
