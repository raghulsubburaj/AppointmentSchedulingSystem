package com.patientschedulingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.UpdateAppointmentStatusDAO;
import com.patientschedulingsystem.model.Appointment;

@RestController
@RequestMapping("/appointment")

public class UpdateAppointmentStatusController {

	@Autowired
	private UpdateAppointmentStatusDAO updateAppointmentStatusDAO;

	@PutMapping("/status")
	public ResponseEntity<String> updateAppointmentStatus(@RequestBody Appointment appointment) {

		try {

			if (appointment.getAppointmentId() <= 0 || appointment.getUser().getUserId() <= 0
					|| appointment.getStatus() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter All The value");
			} else if (statusValidation(appointment.getStatus())) {

				String role = updateAppointmentStatusDAO.roleCheck(appointment.getUser().getUserId());

				if (("patient").equals(role) && ("cancelled").equals(appointment.getStatus())) {

					if (updateAppointmentStatusDAO.updateAppointmentStatus(appointment.getAppointmentId(),
							appointment.getStatus())) {
						return ResponseEntity.status(HttpStatus.CREATED)
								.body("Patient Appointment Status Update Successfully");
					} else {

						return ResponseEntity.status(HttpStatus.CREATED)
								.body("Patient Appointment Status Update Failure");
					}
				} else {

					if (("doctor").equals(role) && ("confirmed").equals(appointment.getStatus())
							|| ("cancelled").equals(appointment.getStatus())) {

						if (updateAppointmentStatusDAO.updateAppointmentStatus(appointment.getAppointmentId(),
								appointment.getStatus())) {
							return ResponseEntity.status(HttpStatus.CREATED)
									.body("Doctor Appointment Status Update Successfully");
						} else {

							return ResponseEntity.status(HttpStatus.CREATED)
									.body("Doctor Appointment Status Update Failure");
						}
					} else {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body("Doctor Role And Status Validation Failure");
					}
				}

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status Validation Failure");
			}

		} catch (Exception e) {
			System.out.println("Update Appointment Status Exception :" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Update Appointment Status Exception :" + e);
		}
		// return null;

	}

	public boolean statusValidation(String status) {
		if ("cancelled".equals(status) || ("confirmed").equals(status)) {
			return true;
		}
		return false;

	}
}
