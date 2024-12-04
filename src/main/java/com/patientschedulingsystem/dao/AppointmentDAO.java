package com.patientschedulingsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppointmentDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean bookNewAppointment(long userId, long doctorId) {

		try {

			String sql = "INSERT INTO appointments (patient_id, doctor_id) VALUES (?, ?)";

			if (jdbcTemplate.update(sql, userId, doctorId) > 0) {

				return true;

			} else {
				return false;
			}
		} catch (Exception e) {

			System.out.println("Database Connection :" + e);
			return false;
		}

	}

	@SuppressWarnings("deprecation")
	public boolean roleCheck(long userId) {

		try {
			String role = null;
			String sql = "SELECT role FROM users where user_id = ?";
			role = (String) jdbcTemplate.queryForObject(sql, new Object[] { userId }, String.class);
			if (("patient").equals(role)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println("Role Check Exception :" + e);
			return false;
		}

	}

}
