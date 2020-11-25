package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.service.ClientService;

@RestController
public class ClientController {

	@Autowired
	private ClientService service;
	
	@GetMapping("/limitClientsAccomplish")
	public Map<String, Object> limitClientsAccomplish(Integer companyId,Integer page,Integer limit,Integer schoolId,Integer classId) {
		if (classId != null) {
			return service.limitClientsAccomplishByClassId(companyId, schoolId, classId);
		}
		return service.limitClientsAccomplish(companyId,page,limit,schoolId);
	}
	
	@GetMapping("/limitClientsFail")
	public Map<String, Object> limitClientsFail(Integer companyId,Integer page,Integer limit,Integer schoolId){
		return service.limitClientsFail(companyId,page,limit,schoolId);
	}
	
	//	全机构用户
	@GetMapping("/limitClients")
	public Map<String, Object> limitClients(Integer companyId,Integer page,Integer limit,Integer schoolId){
		return service.limitClients(companyId,page,limit,schoolId);
	}
	
}
