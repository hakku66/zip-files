package com.patwa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.patwa.model.GstRate;
import com.patwa.repository.GstRateRepository;


@Service
public class GstRateService {
	@Autowired
	private GstRateRepository gstRepo;
	
	
	public GstRate save(GstRate o){
		return gstRepo.save(o);
	}
	public void delete(GstRate o){
		
		gstRepo.delete(o);
	}

	public Map<Double, GstRate> getAllgstrateMap(){
		Map<Double, GstRate> map = new HashMap<>();
		for(GstRate c : getAllGstRate()){
			map.put(c.getGstrate(), c);
		}
		return map;
	}
	private  List<GstRate> getAllGstRate() {
		return gstRepo.findAll();
	}
}
