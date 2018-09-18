package com.patwa.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.patwa.model.Item;
import com.patwa.model.ItemBean;
import com.patwa.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepo;
	

	public List<Item> getAllItems(){
		return itemRepo.findAll();
	}	
	
	
	public List<ItemBean> getAllItemBeans(){
		List<Item> itemList =  getAllItems();
		List<ItemBean> Obean = new ArrayList<>();
		for(Item o : itemList){
			Obean.add(new ItemBean(
					o.getName(),
					o.getHsnNo(),
					o.getQtyType(),
					o.getGstP(),
					o.isIgst(),
					o.getCess(),
					o.getStock(),
					o.getCostPrice(),
					o.getSellingPrice()));
		}
		return Obean;
	}
	
	
	public Item save(Item o){
		return itemRepo.save(o);
	}
	
	public Item getByName(String name){
		return itemRepo.findByName(name);
	}
	
	public Item getByHSN(String hsn){
		return itemRepo.findByHsnNo(hsn);
	}
	
	public void delete(String hsn){
		Item o = getByHSN(hsn);
		itemRepo.delete(o);
	}
	
	
	
	public Map<String, Item> getAllItemMap(){
		Map<String, Item> map = new HashMap<>();
		for(Item i : getAllItems()){
			map.put(i.getName(), i);
		}
		return map;
	}
	public Item getItemById(int id){
		return itemRepo.findOne(id);
	}
}
