package com.patientschedulingsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.GetAppointmentDAO;
import com.patientschedulingsystem.model.Appointment;

@RestController
@RequestMapping("/appointment")
public class GetAppointmentListController {

	@Autowired
	private GetAppointmentDAO getAppointmentDAO;

	@GetMapping("/list")
	public ResponseEntity<List<Appointment>> getAppointmentList() {

		//List<Appointment> arrayList = new ArrayList<Appointment>();
		try {

			List<Appointment> appointmentList = getAppointmentDAO.viewAppointmentList();

			if (appointmentList.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			} else {
				
				//arrayList.add((Appointment) appointmentList);

				return ResponseEntity.ok(appointmentList);
                
			}

		} catch (Exception e) {
			System.out.println("Get Appointment List Exception : " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		

	}

}
