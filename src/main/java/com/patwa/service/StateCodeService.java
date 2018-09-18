package com.patwa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patwa.model.StateCode;
import com.patwa.repository.StateCodeRepository;

@Service
public class StateCodeService {
	@Autowired
	private StateCodeRepository scRepo;
	
	public List<StateCode> getAll(){
		return scRepo.findAll();
	}
	
	public List<String> getAllState(){
		List<String> stateList = new ArrayList<>();
		for(StateCode state : scRepo.findAll()){
			stateList.add(state.getName());
		}
		return stateList;
	}
}
