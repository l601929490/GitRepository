package com.siwo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.XqClassDay;
import com.siwo.model.XqMonthly;
import com.siwo.model.XqType;
import com.siwo.service.XqAllocationService;

@RestController
public class XqAllocationController {

	@Autowired
	private XqAllocationService service;
	
	//	课时
	@PostMapping("/addXqClassDay")
	public Map<String, Object> addXqClassDay(@RequestBody List<XqClassDay> classDays) {
		for (XqClassDay xqClassDay : classDays) {
			xqClassDay.setStart(new Date());
		}
		return service.addXqClassDay(classDays);
	}
	
	@GetMapping("/deleteManyXqClassDay")
	public Map<String, Object> deleteManyXqClassDay(String xqClassdayIds){
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(xqClassdayIds)) {
			map.put("code", 1);
			map.put("msg", "参数不能为空");
			return map;
		}
		String[] ids = xqClassdayIds.split(",");
		return service.deleteManyXqClassDay(ids);
	}
	
	@PostMapping("/updateXqClassDay")
	public Map<String, Object> updateXqClassDay(@RequestBody XqClassDay xqClassDay){
		xqClassDay.setLast(new Date());
		return service.updateXqClassDay(xqClassDay);
	}
	
	@GetMapping("/applet/queryXqClassDaysBySchoolId")
	public Map<String, Object> queryXqClassDaysBySchoolId(Integer schoolId){
		return service.queryXqClassDaysBySchoolId(schoolId);
	}
	
	@GetMapping("/applet/queryXqClassDaysByClassId")
	public Map<String, Object> xwqueryXqClassDaysByClassId(Integer classId){
		return service.queryXqClassDaysByClassId(classId);
	}
	
	@GetMapping("/queryXqClassDaysByClassId")
	public Map<String, Object> queryXqClassDaysByClassId(Integer classId){
		return service.queryXqClassDaysByClassId(classId);
	}
	
	
	
	//	学情类别
	@PostMapping("/addXqType")
	public Map<String, Object> addXqType(@RequestBody XqType xqType){
		xqType.setStart(new Date());
		return service.addXqType(xqType);
	}
	
	@GetMapping("/deleteManyXqType")
	public Map<String, Object> deleteManyXqType(String xqTypeIds){
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(xqTypeIds)) {
			map.put("code", 1);
			map.put("msg", "参数不能为空");
			return map;
		}
		String[] ids = xqTypeIds.split(",");
		return service.deleteManyXqType(ids);
	}
	
	@PostMapping("/updateXqType")
	public Map<String, Object> updateXqType(@RequestBody XqType xqType){
		xqType.setLast(new Date());
		return service.updateXqType(xqType);
	}
	
	@GetMapping("/applet/queryXqTypes")
	public Map<String, Object> wxqueryXqTypes(Integer schoolId){
		return service.queryXqTypes(schoolId);
	}
	
	@GetMapping("/queryXqTypes")
	public Map<String, Object> queryXqTypes(Integer schoolId){
		return service.queryXqTypes(schoolId);
	}
	
	
	//	月度点评
	@PostMapping("/applet/addXqMonthly")
	public Map<String, Object> addXqMonthly(XqMonthly xqMonthly){
		return service.addXqMonthly(xqMonthly);
	}
	
	@PostMapping("/applet/updateXqMonthly")
	public Map<String, Object> updateXqMonthly(XqMonthly xqMonthly){
		return service.updateXqMonthly(xqMonthly);
	}
	
	@PostMapping("/applet/deleteXqMonthly")
	public Map<String, Object> deleteXqMonthly(Integer xqMonthlyId){
		return service.deleteXqMonthly(xqMonthlyId);
	}
	
	@GetMapping("/applet/queryStudentAndXqMonthly")
	public Map<String, Object> queryStudentAndXqMonthly(Integer classId,Integer schoolId,String month){
		return service.queryStudentAndXqMonthly(classId, schoolId, month);
	}
	
	@GetMapping("/applet/queryXqMonthly")
	public Map<String, Object> queryXqMonthly(Integer studentId,Integer classId){
		return service.queryXqMonthly(studentId, classId);
	}
}
