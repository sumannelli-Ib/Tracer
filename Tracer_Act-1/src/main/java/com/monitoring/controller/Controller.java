package com.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.monitoring.bean.Profile;
import com.monitoring.bean.Register;

import com.monitoring.repo.ProfileRepo;
import com.monitoring.repo.RegisterRepo;

@RestController
@CrossOrigin("*")
public class Controller {
	
	
	@Autowired
	RegisterRepo reg;
	
	@Autowired
	ProfileRepo profile;

	
	
	@GetMapping("/test")
	public String hello() {
	
		return "Hello world";
	}
	
	@PostMapping(value = "/hello")
	public String test(@RequestParam String name) {
		return ""+name;
	}
	
	
	
	
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestBody Register login) {
		
		
		//System.out.println(t.name());
		Register register =  reg.findByUsernameAndPassword(login.getUsername(), login.getPassword());
		
		if(register==null) {
			
			return "No data exist";
		}
		else {
			
			return "Login Success";
		}
	}
	
	
	@PostMapping("/register")
	public String reg(@RequestBody Register regg) {
		
		reg.save(regg);
		
		return "Registration successful";
		
	}
	
	
	
	@PostMapping("/profile")
	@ResponseBody
	public String profile(@RequestBody Profile prf) {
		
		
		
		
		
		
		try {
			System.out.println(prf.getAddress());
			System.out.println(prf.getFirstname());
			System.out.println(prf.getLastname());
			Profile pr = profile.save(prf);
			if(pr==null) {
				return "Profile not saved";
			}
			else {
				return "Profile saved successfully";
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return  "Error";
		}
		
		
		
		
		}

	
}
