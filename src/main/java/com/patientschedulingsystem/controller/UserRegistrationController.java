package com.patientschedulingsystem.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.UserDAO;
import com.patientschedulingsystem.model.User;

@RestController
@RequestMapping("/user")
public class UserRegistrationController {

	@Autowired
	private UserDAO userDAO;

	@PostMapping("register")
	public ResponseEntity<String> newUserRegistration(@RequestBody User user) {

		try {

			if ((user.getEmail() == null || user.getPassword() == null || user.getRole() == null)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter all the value");
			} else if (emailValidation(user.getEmail()) && passwordValidation(user.getPassword())
					&& roleValidation(user.getRole())) {

				if (userDAO.userRegistration(user.getEmail(), user.getPassword(), user.getRole())) {

					return ResponseEntity.status(HttpStatus.CREATED).body("User Registration Successfully");
				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("User Registration Failure After Database Connection");
				}

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registrion Failure");
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Exception :" + e);
		}

	}

	private boolean roleValidation(String role) {

		if (("patient").equals(role) || ("doctor").equals(role)) {
			return true;
		}

		return false;
	}

	private boolean passwordValidation(String password) {

		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(password);

		return matcher.matches();

	}

	public boolean emailValidation(String email) {

		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();

		} catch (AddressException ex) {

			System.out.println("Email Validation :" + ex);
			result = false;
		}
		return result;

	}

}
