package com.patientschedulingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String emailUsername;

	public String emailsender(String email, String subject, String message) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			// System.out.println("sender Email Address :" + email);
			mailMessage.setFrom(emailUsername);
			mailMessage.setTo(email);
			mailMessage.setSubject(subject);
			mailMessage.setText(message);

			javaMailSender.send(mailMessage);
			return "SUCCESS";

		} catch (

		Exception e) {
			System.out.println("EmailService Exception :" + e);
			return "FAILURE";
		}

	}
}