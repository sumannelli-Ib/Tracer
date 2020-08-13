package com.monitoring.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.monitoring.bean.Profile;

@Repository
public interface ProfileRepo extends MongoRepository<Profile, Long> {
	

}
