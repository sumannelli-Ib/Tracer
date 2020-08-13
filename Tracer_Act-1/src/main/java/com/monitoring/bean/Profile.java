package com.monitoring.bean;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Profile")
public class Profile {
	
	private String firstname;
	private String lastname;
	private String address;
	
	private Map<String,String> data;
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
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
