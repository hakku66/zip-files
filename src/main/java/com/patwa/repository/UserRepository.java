package com.patwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.patwa.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUserId(String userId);
	
	@Query(value="SELECT u FROM User u where u.userId = ?1 and u.password = ?2 and u.active=1 ")
	User findByUserIdAndPassword(String userId, String password);
	
	@Query(value="SELECT u FROM User u where u.userId = ?1 and u.password = ?2 ")
	User findByUserIdAndPassword1(String userId, String password);
}
