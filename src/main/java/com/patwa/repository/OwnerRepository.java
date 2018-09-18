package com.patwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patwa.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	public Owner findByName(String name);
	
	public Owner findByPanNo(String panNo);
	
	public Owner findByNameAndPanNo(String name, String panNo);
}
