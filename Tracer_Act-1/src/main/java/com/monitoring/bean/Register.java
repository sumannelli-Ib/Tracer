package com.monitoring.bean;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Register")
public class Register {
	private Map<String,String> data;
	
	
	private String username;
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String password;
	
}
