package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Consult;
import com.siwo.service.ConsultService;

@RestController
public class ConsultController {

	@Autowired
	private  ConsultService service;
	
	@PostMapping("/addConsult")
	public Map<String, Object> addConsult(@RequestBody Consult consult){
		consult.setTime(new Date());
		return service.addConsult(consult);
	}
	
	//	查询全部和查询单个咨询公用一个接口
	@GetMapping("/getAllConsult")
	public Map<String, Object> queryAllConsult(Integer consultId,Integer schoolId){
		Map<String, Object> map = null;
		
		if (consultId == null) {
			map = service.queryAllConsult(schoolId);
		}else if (consultId != null) {
			map = service.queryConsultById(consultId);
		}
		return map;
	}
	
	@GetMapping("/applet/getAllConsult")
	public Map<String, Object> queryAllConsultx(Integer consultId,Integer schoolId,String param){
		Map<String, Object> map = null;
		
		if (consultId == null && schoolId != null) {
			map = service.queryAllConsult(schoolId);
		}else if (consultId != null) {
			map = service.queryConsultById(consultId);
		}
		
		if (param != null && schoolId != null) {
			map = service.fuzzyQueryConsults(param, schoolId);
		}
		return map;
	}
	
	@PostMapping("/updateConsult")
	public Map<String, Object> updateConsult(@RequestBody Consult consult){
		return service.updateConsult(consult);
	}
	 
	
	@GetMapping("/deleteConsult")
	public Map<String, Object> deleteConsult(String consultIds){
		Map<String, Object> map = null;
		
		String[] ids = consultIds.split(",");
		
		if (ids.length == 1) {
			map = service.deleteConsult(consultIds);
		}else if (ids.length > 1) {
			
			map = service.deleteManyConsult(ids);
		}
		return map;
	}
	
	@GetMapping("/limitConsults")
	public Map<String, Object> limitClass(Integer page, Integer limit,Integer schoolId,String consultTitle){
		if (!StringUtils.isEmpty(consultTitle)) {
			return service.fuzzyQueryConsults(consultTitle, schoolId);
		}
		return service.limitConsults(page, limit, schoolId);
	}
}
