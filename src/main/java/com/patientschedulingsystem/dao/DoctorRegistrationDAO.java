package com.patientschedulingsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.patientschedulingsystem.model.User;

@Repository
public class DoctorRegistrationDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean newDoctorRegistrtion(String specialization, long userId) {

		try {

			String sql = "INSERT INTO doctors (user_id, specialization) VALUES (?, ?)";

			if (jdbcTemplate.update(sql, userId, specialization) > 0) {

				return true;

			}
		} catch (Exception e) {

			System.out.println("Database Connection :" + e);

		}

		return false;

	}

	@SuppressWarnings("deprecation")
	public String roleCheck(long userId) {
		try {

			String sql = "SELECT role FROM users where user_id = ?";
			String role = (String) jdbcTemplate.queryForObject(sql, new Object[] { userId }, String.class);

			return role;

		} catch (Exception e) {
			System.out.println("Role Check Exception :" + e);
			return null;
		}

	}
}
