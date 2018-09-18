package com.patwa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patwa.model.Company;
import com.patwa.repository.CompanyRepository;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository comRepo;
	
	public Company save(Company c){
		return comRepo.save(c);
	}
	
	public Company getByName(String name){
		return comRepo.findCompanyByName(name);
	}
	
	public List<Company> getAll(){
		return comRepo.findAll();
	}
	
	public Company getByCompanyId(int id){
		return comRepo.findOne(id);
	}
}
