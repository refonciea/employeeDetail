package com.form.EmployeeDetails.dao;

import java.util.List;

import com.form.EmployeeDetails.model.Employe;
import com.form.EmployeeDetails.model.User;



public interface Empdao { 
	public abstract String createEmployee(Employe employe);
	
	public List<Employe>getAllDetails();
	
	public String register(User user);
	
	public User searchByName(String UserName);
	
//	public Integer searchByName(String UserName);
	
	public List<Employe>searchByDates(String Startdate, String Enddate);
	
	public String updateemploye(Employe employe);
	
//	public String update(Employe employe,String id );


}
