package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.ApplyMessage;
import com.siwo.service.ApplyMessageService;

@RestController
public class ApplyMessageController {

	
	@Autowired
	private ApplyMessageService service;
	
	@PostMapping("/applet/addApplyMessage")
	public Map<String, Object> addApplyMessage(@RequestBody ApplyMessage message,BindingResult result) {
		return service.addApplyMessage(message,result);
	}
	
	@GetMapping("/limitApplyMessage")
	public Map<String, Object> limitApplyMessage(Integer page, Integer limit, Integer schoolId) {
		return service.limitApplyMessage(page, limit, schoolId);
	}
}
