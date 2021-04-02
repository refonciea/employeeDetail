package com.form.EmployeeDetails.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.form.EmployeeDetails.dao.Empdao;
import com.form.EmployeeDetails.model.Employe;
import com.form.EmployeeDetails.model.User;

@Repository
public class EmpDaoImpl implements Empdao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public String createEmployee(Employe employe) {
		String status = "";
		String CREATE_EMPLOYEE_SQL = "Insert into Empform(Name,Gender,Dateofjoin,Designation,CTC,PF,ESI,TAX) VALUES(?,?,?,?,?,?,?,?)";
		try {
			int update = jdbcTemplate.update(CREATE_EMPLOYEE_SQL, employe.getName(), employe.getGender(),
					employe.getDateofjoin(), employe.getDesignation(), employe.getCTC(), employe.getPF(),
					employe.getESI(), employe.getTax());

			if (update == 1) {
				status = "Success";
			} else {
				status = "Failed";
			}
		} catch (Exception e) {
			System.out.println(e);
			status = "Failed";
		}
		return status;

	}

	@Override
	public List<Employe> getAllDetails() {
		
		String GET_ALL_EMPLOYEE = "SELECT Name,Gender,DATE_FORMAT(Dateofjoin,\"%d-%b-%Y\")as Dateofjoin,Designation,CTC,PF,ESI,TAX,((CTC/12)-(PF+ESI+TAX)) as Takehome FROM Empform ORDER BY Created_date DESC";
		List<Employe> employe = jdbcTemplate.query(GET_ALL_EMPLOYEE, new EmpRowMapper());
		return employe;
	}

	@Override
	public String register(User user) {
		String status = "";
		String USER_SQL = "Insert into user_detail(user_name,user_password,first_name,last_name)VALUES(?,?,?,?)";
		try {
			int update = jdbcTemplate.update(USER_SQL, user.getUserName(), bcryptEncoder.encode(user.getUserPassword()),
					user.getFirstName(), user.getLastName());
			if (update == 1) {
				status = "Success";
			} else {
				status = "Failed";
			}

		} catch (Exception e) {
			System.out.println(e);
			status = "Failed";

		}
		System.out.println(status);
		return status;
	}


	public User searchByName(String userName) {
		String FETCH_SQL = "SELECT * FROM user_detail WHERE user_name = '" + userName +"'";
		User user;
		try {
			user = jdbcTemplate.queryForObject(FETCH_SQL,
					new RowMapper<User>() {
						public User mapRow(ResultSet rs, int rownumber)
								throws SQLException {
							User e = new User();
							e.setUserName(rs.getString("user_name"));
							e.setUserPassword(rs.getString("user_password"));
							e.setFirstName(rs.getString("first_name"));
							e.setLastName(rs.getString("last_name"));
							return e;
						}
					});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return user;
	}

//	@Override
//	public List<Employe> searchByDates(String Startdate, String Enddate) {
//		String FETCH_BYDATES="SELECT Name,Gender,DATE_FORMAT(Dateofjoin,\"%d-%b-%Y\")as Dateofjoin,Designation,CTC,PF,ESI,TAX,((CTC/12)-(PF+ESI+TAX)) as Takehome FROM Empform WHERE Dateofjoin BETWEEN '"+Startdate+"' And '"+Enddate+"'";
//		List<Employe> datefilter= jdbcTemplate.query(FETCH_BYDATES, new EmpRowMapper());
//		return datefilter;
//	}

	@Override
	public List<Employe> searchByDates(String Startdate, String Enddate) {
		String FETCH_BYDATES="";
		String date="1";
		try {
			if(Startdate != "" && Enddate!= "") {
				date="Dateofjoin BETWEEN '"+Startdate+"' And '"+Enddate+"'";
			}
		FETCH_BYDATES="SELECT id,Name,Gender,DATE_FORMAT(Dateofjoin,\"%d-%b-%Y\")as Dateofjoin,Designation,CTC,PF,ESI,TAX,((CTC/12)-(PF+ESI+TAX)) as Takehome FROM Empform WHERE "+date+" ORDER BY Created_date DESC ";
		List<Employe> datefilter= jdbcTemplate.query(FETCH_BYDATES, new EmpRowMapper());
		return datefilter;
		}catch(EmptyResultDataAccessException e) 
		{
			System.out.println(e.getMessage());
			return null;
			}
		
	}


	@Override
	public String updateemploye(Employe employe) {
//		String id=employe.getId();
//		System.out.println(id);
		String status="";
		String UPDATE_EMP= "UPDATE Empform SET Name=?, Gender=?,Dateofjoin=?,Designation=?,CTC=?,PF=?,ESI=?,Tax=? WHERE id =?";
		
		try {
		int count = jdbcTemplate.update( UPDATE_EMP,employe.getName(), employe.getGender(),
				employe.getDateofjoin(), employe.getDesignation(), employe.getCTC(), employe.getPF(),
				employe.getESI(), employe.getTax(),employe.getId());
		if(count!=0) {
			status="success";
		}else {
			status="failed";
		}
		}catch(Exception e) {
			System.out.println(e);
			status = "Failed";
		}
		
		System.out.println(status);
		return status;
	}
	

}