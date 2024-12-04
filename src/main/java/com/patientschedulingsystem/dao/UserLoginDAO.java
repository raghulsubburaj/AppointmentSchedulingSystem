package com.patientschedulingsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.patientschedulingsystem.model.User;

@Repository
public class UserLoginDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	public User loginUser(String email, String password) {

		User user = new User();

		try {

			String sql = "SELECT * FROM users WHERE email = ? and password = ?";

			jdbcTemplate.query(sql, new Object[] { email, password }, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException {

					if (rs.next()) {

						user.setUserId(rs.getLong("user_id"));
						user.setEmail(rs.getString("email"));
						user.setPassword(rs.getString("password"));
						user.setRole(rs.getString("role"));

					}
					return null;
				}

			});

		} catch (Exception e) {
			System.out.println("Database Exception For User Exception :" + e);
			return null;
		}
		return user;

	}
}
