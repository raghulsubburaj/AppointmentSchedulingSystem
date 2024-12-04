package com.patientschedulingsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.patientschedulingsystem.model.Appointment;
import com.patientschedulingsystem.model.Doctor;
import com.patientschedulingsystem.model.User;

@Repository
public class EmailDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public List<Appointment> sendEmail(long appointmentId) {
		
		List<Appointment> arrayLists = new ArrayList<Appointment>();
		
		try {
			
		
		String sql = "SELECT app.appointment_id,app.patient_id,app.doctor_id,app.appointment_time,app.status,u.email FROM appointments AS app INNER JOIN users AS u ON u.user_id = app.patient_id WHERE app.appointment_id = ?";
		jdbcTemplate.query(sql, new Object[] {appointmentId}, new ResultSetExtractor<Appointment>() {

			@Override
			public Appointment extractData(ResultSet rs) throws SQLException {

				while (rs.next()) {
					
					Appointment appointmentList = new Appointment();
					Doctor doctorList = new Doctor();
					User userList = new User();

					appointmentList.setAppointmentId(rs.getLong("appointment_id"));
					userList.setUserId(rs.getLong("patient_id"));
					doctorList.setDoctorId(rs.getLong("doctor_id"));
					appointmentList.setAppointmentTime(rs.getTime("appointment_time").toLocalTime());
					appointmentList.setStatus(rs.getString("status"));
					userList.setEmail(rs.getString("email"));
					appointmentList.setUser(userList);
					appointmentList.setDoctor(doctorList);
					arrayLists.add(appointmentList);
					

				}
				return null;
				
			}

		});
	} catch (Exception e) {
		System.out.println("Fetch Email Details in EmailDAO :" +e);
		
	}
		
		return arrayLists;
		
}

}
