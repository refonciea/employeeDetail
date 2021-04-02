package com.form.EmployeeDetails.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.form.EmployeeDetails.dao.UserDao;
import com.form.EmployeeDetails.model.User;


@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
public User findByUsername(String username) {
		
		try {
			RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
			String FETCH_SQL = "SELECT * FROM user_detail WHERE user_name =?";
			//Table columns are automatically mapped to bean attributes
			User user = jdbcTemplate.queryForObject(FETCH_SQL,rowMapper,username);
			return  user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}	
}


}
