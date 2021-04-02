package com.form.EmployeeDetails.model;


public class TokenResponse  {


	private String token;
	private String message;
	private String name;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TokenResponse(String token,String message,String name) {
		
		this.token = token;
		this.message = message;
		this.name = name;
	}

	
	

}
