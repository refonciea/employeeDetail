package com.form.EmployeeDetails.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.form.EmployeeDetails.config.TokenUtil;
import com.form.EmployeeDetails.config.JwtUserDetailsService;
import com.form.EmployeeDetails.dao.Empdao;

import com.form.EmployeeDetails.model.Employe;
import com.form.EmployeeDetails.model.LoginRequest;
import com.form.EmployeeDetails.model.TokenResponse;
import com.form.EmployeeDetails.model.User;







@RestController
@CrossOrigin(origins = "*")

public class Employeedetaliscontroller {
	

	@Autowired
	private Empdao empdao;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;


	@Autowired
	private TokenUtil tokenUtil;
	
	
	@RequestMapping(value = "/saveemployee",
            method = RequestMethod.POST) 
	 public ResponseEntity<String> saveOrUpdate(@RequestBody Employe employe) {
		try {
			System.out.println(employe.getName());
			empdao.createEmployee(employe);
			String response= "Employee details added successfully";
			  return new ResponseEntity<String>(response,HttpStatus.OK);
		} catch (Exception e) {
			   String response= "Employee details not updated successfully";
			  return new ResponseEntity<String>(response,HttpStatus.OK);
		}
		
	  }
	
	@RequestMapping(value = "/saveAndUpdtaeemployee",
            method = RequestMethod.POST) 
	 public ResponseEntity<String> saveOrUpdate1(@RequestBody Employe employe) {
		try {
			System.out.println(employe.getName());
			System.out.println(employe.getId());
			String id=employe.getId();
			if(id != "") {
				empdao.updateemploye(employe);
				String response= "Employee details updated successfully";
				return new ResponseEntity<String>(response,HttpStatus.OK);
			}else {
			empdao.createEmployee(employe);
			String response= "Employee details added successfully";
			  return new ResponseEntity<String>(response,HttpStatus.OK);
			}
		} catch (Exception e) {
			   String response= "Employee details not updated successfully";
			  return new ResponseEntity<String>(response,HttpStatus.OK);
		}
		
	  }
	
//	 @RequestMapping(value="/getemployee", method=RequestMethod.GET)
//	 public ResponseEntity<Object> retrieve() {
//		 List<Employe>employe=empdao.getAllDetails();
//		 try {
//			 System.out.println(employe);
//			 return new ResponseEntity<Object>(employe,HttpStatus.OK);
//		 }catch (Exception e){
//			 return new ResponseEntity<Object>(employe,HttpStatus.NO_CONTENT);
//		 }
//
//	}
	 
		
	 
		@RequestMapping(value = "/register",method = RequestMethod.POST) 
		 public ResponseEntity<String> createuser(@RequestBody User user) throws Exception {
			
			try {
				String userName=user.getUserName();
				System.out.println("1 "+userName);
				User getName= empdao.searchByName(userName);
//				System.out.println("3"+getName);			
				
					if(getName!=null) {
						 String response="email address already exists";
						 return new ResponseEntity<String>(response,HttpStatus.OK);
					
					}else {
					     empdao.register(user);
				         String response= "Registered sucessfully.";
				         return new ResponseEntity<String>(response,HttpStatus.OK);
					}
				    
			} catch (Exception e) {
				        String response= "Registration failed"+e.getMessage();
				     return new ResponseEntity<String>(response,HttpStatus.OK);
		   }
				
				
		  }
	
		
		
		@RequestMapping(value = "/employeelogin", method = RequestMethod.POST)
		public ResponseEntity<?> createToken(@RequestBody LoginRequest request) throws Exception {
			//front end req authenticate
		  String loginstatus =checkCredential(request.getUserName(), request.getUserPassword());
		   if (loginstatus.equalsIgnoreCase("Failed")) {
			   String response= "Invalid Credentials";
				final String token = "";
				String userName="";
				return ResponseEntity.ok(new TokenResponse(token,response,userName));			   
		   }
				final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
//				final UserDetails userDetailsPassword = userDetailsService.loadUserByUsername(request.getUserName());
//				String encryptPassword=userDetailsPassword.getPassword();	
//				System.out.println("5"+ bcryptEncoder.matches(getPassword,encryptPassword));
					if(userDetails!=null){
						String response="Login Sucessfully";
						String userName=userDetails.getUsername();
						User getName= empdao.searchByName(userName);
						String name=getName.getFirstName()+" "+getName.getLastName();
						System.out.println(userDetails.getUsername());
						System.out.println(response);
						final String token = tokenUtil.tokenGenerate(userDetails);
//					TokenResponse message1=new TokenResponse(token);
						return ResponseEntity.ok(new TokenResponse(token,response,name));
					}
			return null;			
		}
				
	
		
		private String checkCredential(String userName, String userPassword) throws Exception {
			
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
				return "Success";
			}catch (BadCredentialsException e) {
				return "Failed";
			}	
		}
		
		
//		 @RequestMapping(value="/getemployee", method=RequestMethod.POST)
//		 public ResponseEntity<Object> retrieve(@RequestBody Employe emlpoye) {
//			 String startdate=emlpoye.getStartdate();
//			 String enddate=emlpoye.getEnddate();
//			 try {
//				 if(startdate != "" && enddate != "") {
//					 System.out.println("if");
//					 List<Employe>datefilter=empdao.searchByDates(emlpoye.getStartdate(), emlpoye.getEnddate());
//					 return new ResponseEntity<Object>(datefilter,HttpStatus.OK);
//				 }else {
//					 System.out.println("else");
//					 List<Employe>allemploye=empdao.getAllDetails();
//					 return new ResponseEntity<Object>(allemploye,HttpStatus.OK);
//				 }
//			 }catch (Exception e){
//				 return new ResponseEntity<Object>(HttpStatus.OK);
//			 }			 
//
//		}	
		
		
		@RequestMapping(value="/getemployee", method=RequestMethod.POST)
		 public ResponseEntity<Object> retrieve(@RequestBody Employe emlpoye) {
			 String startdate=emlpoye.getStartdate();
			 String enddate=emlpoye.getEnddate();
			 try {				
					 List<Employe>datefilter=empdao.searchByDates(startdate, enddate);
					 return new ResponseEntity<Object>(datefilter,HttpStatus.OK);
				 
			 }catch (Exception e){
				 return new ResponseEntity<Object>(HttpStatus.OK);
			 }			 

		}
		
		
		
		@RequestMapping(value="/searchbydate",method=RequestMethod.POST)
		public ResponseEntity<Object>findByDates(@RequestBody Employe emlpoye){
			List<Employe>datefilter=empdao.searchByDates(emlpoye.getStartdate(), emlpoye.getEnddate());
			try {
				 System.out.println(datefilter);
				 return new ResponseEntity<Object>(datefilter,HttpStatus.OK);
			 }catch (Exception e){
				 return new ResponseEntity<Object>(datefilter,HttpStatus.NO_CONTENT);
			 }
			
		}
		
		@RequestMapping(value = "/updatemployee",
	            method = RequestMethod.POST) 
		 public ResponseEntity<String> update(@RequestBody Employe employe) {
			try {
				System.out.println(employe.getName());
				empdao.updateemploye(employe);
				String response= "";
				  return new ResponseEntity<String>(response,HttpStatus.OK);
			} catch (Exception e) {
				   String response= "Employee details not updated successfully";
				  return new ResponseEntity<String>(response,HttpStatus.OK);
			}
			
		  }
		


}
