package com.form.EmployeeDetails.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.form.EmployeeDetails.model.Employe;



public class EmpRowMapper implements RowMapper<Employe>  {

	@Override
	public Employe mapRow(ResultSet rs, int rowNum) throws SQLException {
		Employe employe=new Employe();
		employe.setId(rs.getString("id"));
		employe.setName(rs.getString("Name"));
		employe.setGender(rs.getString("Gender"));
		employe.setDateofjoin(rs.getString("Dateofjoin"));
		employe.setDesignation(rs.getString("Designation"));
		employe.setCTC(rs.getFloat("CTC"));
		employe.setPF(rs.getFloat("PF"));
		employe.setESI(rs.getFloat("ESI"));
		employe.setTax(rs.getFloat("Tax"));
		employe.setTakehome(rs.getFloat("Takehome"));
		return employe;
	}

}
