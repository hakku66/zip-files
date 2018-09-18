package com.patwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patwa.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	public Customer findByName(String name);
	
	public Customer findByPanNo(String panNo);
	
	public Customer findByNameAndPanNo(String name, String panNo);
}
