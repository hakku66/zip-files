package com.patwa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patwa.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
	
	public Company findCompanyByName(String name);
}
