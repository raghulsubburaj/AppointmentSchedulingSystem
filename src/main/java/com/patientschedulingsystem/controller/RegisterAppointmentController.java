package com.patientschedulingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.AppointmentDAO;
import com.patientschedulingsystem.model.Appointment;

@RestController
@RequestMapping("/appointment")
public class RegisterAppointmentController {

	@Autowired
	private AppointmentDAO appointmentDAO;

	@PostMapping("/register")
	public ResponseEntity<String> registerNewAppointment(@RequestBody Appointment appointment) {

		if (appointment.getUser().getUserId() <= 0 || appointment.getDoctor().getDoctorId() <= 0) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter All The Value");

		} else if (appointmentDAO.roleCheck(appointment.getUser().getUserId())) {

			if (appointmentDAO.bookNewAppointment(appointment.getUser().getUserId(),
					appointment.getDoctor().getDoctorId())) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Appointment Register");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Appointment Registration Failure After Database Connection");
			}

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body("Role Validation Failue");
		}

	}

}
