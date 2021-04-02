package com.form.EmployeeDetails.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.form.EmployeeDetails.dao.UserDao;
import com.form.EmployeeDetails.model.User;

 @Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userdao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user=userdao.findByUsername(userName);
		if (user ==  null) {
			throw new UsernameNotFoundException("User not found with username: " + userName);
		}
//		System.out.println("1"+user);
//		System.out.println("2"+userName);
		//map into user obj provided by spring security
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
				new ArrayList<>());
	}

}
