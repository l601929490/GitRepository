package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.service.ScoreRecipientService;

@RestController
public class ScoreRecipientController {

	@Autowired
	private ScoreRecipientService  service;
	
	 
	//解绑n  绑定y
	@GetMapping("changeScoreRecipient")
	public Map<String, Object> changeScoreRecipient(String  teacherId,String flag){
		 String[] strs = teacherId.split(",");
		
		return service.changeScoreRecipient(strs,flag);
	}
	
	@GetMapping("queryRecipientByTeacherId")
	public Map<String, Object> queryRecipientByTeacherId(Integer teacherId){
		
		
		return service.queryRecipientByTeacherId(teacherId);
	}
}
