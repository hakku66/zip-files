package com.patwa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patwa.model.User;
import com.patwa.model.UserBean;
import com.patwa.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User getUserByUserId(String u){
		return userRepo.findByUserId(u);
	}
	
	public User getUserByUserIdPassword(String userId, String password){
		return userRepo.findByUserIdAndPassword(userId, password);
	}
	
	public List<User> findAllUser(){
		return userRepo.findAll();
	}
	
	public List<UserBean> findAllUserBean(){
		List<UserBean> userBeanList = new ArrayList<>();
		int count =1;
		for(User user : findAllUser()){
			userBeanList.add(new UserBean(count++, user.getUserId(), user.getPassword(), user.getActive(), user.getCreated()));
		}
		return userBeanList;
	}
	
	public User save(User u){
		return userRepo.save(u);
	}
	
	public void delete(String u, String p){
		User user = userRepo.findByUserIdAndPassword1(u, p);
		if(user != null)
			userRepo.delete(user);
	}
	
	public void update(UserBean userBean, String u, String p, int active){
		User user = userRepo.findByUserIdAndPassword1(userBean.getRUserId(), userBean.getRPassword());
		user.setUserId(u);
		user.setPassword(p);
		user.setActive(active);
		userRepo.save(user);
	}
}
