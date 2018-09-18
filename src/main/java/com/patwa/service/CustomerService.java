package com.patwa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patwa.model.Customer;
import com.patwa.model.CustomerBean;
import com.patwa.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository custRepo;
	
	public List<Customer> getAllCustomers(){
		return custRepo.findAll();
	}	
	
	public List<CustomerBean> getAllCustomerBeans(){
		List<Customer> customerList =  getAllCustomers();
		List<CustomerBean> Obean = new ArrayList<>();
		for(Customer o : customerList){
			Obean.add(new CustomerBean(
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
					o.getActive()));
		}
		return Obean;
	}
	
	public Customer save(Customer o){
		return custRepo.save(o);
	}
	
	public Customer getByName(String name){
		return custRepo.findByName(name);
	}
	
	public Customer getByPanNo(String panNo){
		return custRepo.findByPanNo(panNo);
	}
	
	public void delete(String panNo){
		Customer o = getByPanNo(panNo);
		custRepo.delete(o);
	}
	
	public Customer getByNameAndPanNo(String name, String panNo){
		return custRepo.findByNameAndPanNo(name, panNo);
	}
	
	public List<Customer> getAllCustomer(){
		return custRepo.findAll();
	}
	
	public Map<String, Customer> getAllCustomerMap(){
		Map<String, Customer> map = new HashMap<>();
		for(Customer c : getAllCustomer()){
			map.put(c.getAliasName(), c);
		}
		return map;
	}
	public Customer getCustomerById(int id){
		return custRepo.findOne(id);
	}
}
