package com.form.EmployeeDetails.model;

public class Employe{

	private String Id;
	public String getId() {
		return Id;
	}



	public void setId(String id) {
		Id = id;
	}



	private String Name;
	private String Gender;
	private String Designation;
	private Float CTC;
	private Float PF;
	private Float ESI;
	private Float Tax;
	private String Dateofjoin;
	private Float Takehome;
	private String Startdate;
	private String Enddate;
	
	
	
	public String getStartdate() {
		return Startdate;
	}



	public void setStartdate(String startdate) {
		Startdate = startdate;
	}



	public String getEnddate() {
		return Enddate;
	}



	public void setEnddate(String enddate) {
		Enddate = enddate;
	}



	
	
	public Float getTakehome() {
		return Takehome;
	}



	public void setTakehome(Float takehome) {
		Takehome = takehome;
	}



	public Float getCTC() {
		return CTC;
	}



	public void setCTC(Float cTC) {
		CTC = cTC;
	}



	public Float getPF() {
		return PF;
	}



	public void setPF(Float pF) {
		PF = pF;
	}



	public Float getESI() {
		return ESI;
	}



	public void setESI(Float eSI) {
		ESI = eSI;
	}



	public Float getTax() {
		return Tax;
	}



	public void setTax(Float tax) {
		Tax = tax;
	}



	public String getName() {
		return Name;
	}



	public void setName(String name) {
		Name = name;
	}



	public String getGender() {
		return Gender;
	}



	public void setGender(String gender) {
		Gender = gender;
	}



	public String getDesignation() {
		return Designation;
	}



	public void setDesignation(String designation) {
		Designation = designation;
	}






	public String getDateofjoin() {
		return Dateofjoin;
	}



	public void setDateofjoin(String dateofjoin) {
		Dateofjoin = dateofjoin;
	}



	@Override
	public String toString() {
		return "Employe [Name=" + Name + ", Gender=" + Gender + ", Designation=" + Designation + ", CTC=" + CTC
				+ ", PF=" + PF + ", ESI=" + ESI + ", Tax=" + Tax + ", Dateofjoin=" + Dateofjoin + ", Takehome=" + Takehome + "]";
	}

	
//	@Override
//	public String toString() {
//		return "Employe [Name=" + Name + ", Gender=" + Gender + ", Designation=" + Designation
//				+ ", CTC=" + CTC + ", PF=" + PF + ", ESI=" + ESI + ", CTC=" + Tax + ", Dateofjoin=" + Dateofjoin + "]";
//	}

	


}
