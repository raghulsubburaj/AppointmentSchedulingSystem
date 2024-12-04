package com.patientschedulingsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.ViewAppointmentDAO;
import com.patientschedulingsystem.model.Appointment;

@RestController
@RequestMapping("/appointment")
public class ViewAppointmentController {

	@Autowired
	private ViewAppointmentDAO viewAppointmentDAO;

	@GetMapping("/view")
	public ResponseEntity<List<Appointment>> viewAppointment(@RequestBody Appointment appointment) {
		try {

			List<Appointment> arrayList = new ArrayList<Appointment>();
			if (appointment.getAppointmentId() <= 0) {

				return ResponseEntity.noContent().build();
			} else {

				List<Appointment> appointmentsList = viewAppointmentDAO.viewAppointment(appointment.getAppointmentId());

				if (appointmentsList.isEmpty()) {
					return ResponseEntity.noContent().build();
				} else {
					return ResponseEntity.ok(appointmentsList);
				}
			}

		} catch (Exception e) {
			System.out.println("Get Specific Appointment List Exception :" + e);
			return ResponseEntity.noContent().build();
		}
		// return null;

	}

}
