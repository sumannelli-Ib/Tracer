package com.monitoring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.monitoring.bean.Register;

@Repository
public interface RegisterRepo extends MongoRepository<Register, Long> {
	
	public Register findByUsernameAndPassword(String username,String password);

}
