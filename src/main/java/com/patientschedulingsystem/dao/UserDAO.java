package com.patientschedulingsystem.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean userRegistration(String email, String password, String status) {

		try {

			String sql = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";

			if (jdbcTemplate.update(sql, email, password, status) > 0) {

				return true;

			}
		} catch (Exception e) {
			
			System.out.println("Database Connection :" + e);
			
		}

		return false;

	}
}