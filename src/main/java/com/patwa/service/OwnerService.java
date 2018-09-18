package com.patwa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patwa.model.Company;
import com.patwa.model.Owner;
import com.patwa.model.OwnerBean;
import com.patwa.repository.CompanyRepository;
import com.patwa.repository.OwnerRepository;

@Service
public class OwnerService {
	
	@Autowired
	private OwnerRepository oRepo;
	@Autowired
	private CompanyRepository comRepo;
	
	public List<Owner> getAllOwners(){
		return oRepo.findAll();
	}
	
	public List<OwnerBean> getAllOwnerBeans(){
		List<Owner> ownerList =  getAllOwners();
		List<OwnerBean> Obean = new ArrayList<>();
		for(Owner o : ownerList){
			Company c = comRepo.findOne(o.getCompanyId());
			Obean.add(new OwnerBean(
					o.getName(),
					o.getEmail(),
					o.getPhone1(),
					o.getPhone2(),
					o.getState(),
					o.getCity(),
					o.getAddress(),
					o.getPin(),
					o.getCreated(),
					o.getAliasName(),
					o.getPanNo(),
					o.getGstNo(),
					o.getActive(),
					c.getName()));
		}
		return Obean;
	}
	
	public Owner save(Owner o){
		return oRepo.save(o);
	}
	
	public Owner getByName(String name){
		return oRepo.findByName(name);
	}
	
	public Owner getByPanNo(String panNo){
		return oRepo.findByPanNo(panNo);
	}
	
	public void delete(String name, String panNo){
		Owner o = getByNameAndPanNo(name, panNo);
		oRepo.delete(o);
	}
	
	public Owner getByNameAndPanNo(String name, String panNo){
		return oRepo.findByNameAndPanNo(name, panNo);
	}
	
	public List<Owner> getAllSeller(){
		return oRepo.findAll();
	}
	
	public Map<String, Owner> getAllSellerMap(){
		Map<String, Owner> map = new HashMap<>();
		for(Owner o : getAllSeller()){
			map.put(o.getAliasName(), o);
		}
		return map;
	}
	public Owner getOwnerById(int id){
		return oRepo.findOne(id);
	}
}
