package com.patientschedulingsystem.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.EmailDAO;
import com.patientschedulingsystem.model.Appointment;
import com.patientschedulingsystem.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private EmailDAO emailDAO;

	@Autowired
	private EmailService emailService;

	@PostMapping("/notify")
	public ResponseEntity<String> emailNotification(@RequestBody Appointment appointment) {

		long appointment_Id;
		long patient_Id;
		long doctor_Id = 0;
		LocalTime appointment_Time = null;
		String status = null;
		String emailId = null;
		if (appointment.getAppointmentId() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter All The Value");

		} else {
			List<Appointment> appointmentList = emailDAO.sendEmail(appointment.getAppointmentId());

			if (appointmentList.isEmpty()) {

				return ResponseEntity.status(HttpStatus.CREATED).body("AppointmentList not found for ypur id");

			} else {

				for (Appointment getAppointment : appointmentList) {

					appointment_Id = getAppointment.getAppointmentId();
					patient_Id = getAppointment.getUser().getUserId();
					doctor_Id = getAppointment.getDoctor().getDoctorId();
					appointment_Time = getAppointment.getAppointmentTime();
					status = getAppointment.getStatus();
					emailId = getAppointment.getUser().getEmail();

				}

				String subject = "Appointment Reminder From YYYY";
				String message = "Dear user, this is a reminder for your appointment with doctor  " + doctor_Id
						+ " Scheduled For " + appointment_Time;

				String emailResponse = emailService.emailsender(emailId, subject, message);

				if (("FAILURE").equals(emailResponse)) {
					return ResponseEntity.status(HttpStatus.CREATED).body("Email Send Failure");
				} else {
					return ResponseEntity.status(HttpStatus.CREATED).body("Email Send Successfully");
				}

			}

			// System.out.println("Email Details List :" + emailDetails);

		}

	}

}
