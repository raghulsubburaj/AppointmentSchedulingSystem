package com.patientschedulingsystem.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientschedulingsystem.dao.UserLoginDAO;
import com.patientschedulingsystem.model.User;

@RestController
@RequestMapping("/user")
public class UserLoginController {

	@Autowired
	private UserLoginDAO userLoginDAO;

	@PostMapping("/login")
	public ResponseEntity<String> userLogin(@RequestBody User user) {

		try {

			if (user.getEmail() == null || user.getPassword() == null) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Enter All The Value");
			} else if (emailValidation(user.getEmail()) && passwordValidation(user.getPassword())) {

				User userDetails = userLoginDAO.loginUser(user.getEmail(), user.getPassword());

				if (userDetails == null) {

					return ResponseEntity.status(HttpStatus.CREATED).body("Invalid Username And Password");

				} else {
					return ResponseEntity.status(HttpStatus.CREATED).body("Login Successfully");
				}

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Authentication Failure");
			}
		} catch (Exception e) {
			System.out.println("User Login Exception : " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception During login");
		}

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

	private boolean passwordValidation(String password) {

		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(password);

		return matcher.matches();

	}

}
