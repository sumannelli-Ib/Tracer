package com.monitoring.bean;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Profile")
public class Profile {
	
	private String firstname;
	private String lastname;
	private String address;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
