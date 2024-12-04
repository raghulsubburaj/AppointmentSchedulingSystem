package com.patientschedulingsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateAppointmentStatusDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	public boolean updateAppointmentStatus(long appointmentId, String status) {

		String sql = "UPDATE appointments SET status = ? where appointment_id = ?";

		if (jdbcTemplate.update(sql, status, appointmentId) > 0) {
			return true;
		}
		return false;
	}

}
