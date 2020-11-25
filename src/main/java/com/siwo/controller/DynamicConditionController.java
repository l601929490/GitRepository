package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.DynamicCondition;
import com.siwo.service.DynamicConditionService;

@RestController
public class DynamicConditionController {

	@Autowired
	private DynamicConditionService service;
 
	//查询所有校区绑定的积分配置
	@GetMapping("/queryDynamicConditionByCompanyId")
	public Map<String, Object> queryDynamicConditionByCompanyId(Integer companyId) {
		return service.queryDynamicConditionByCompanyId(companyId);
	}
	//查询校区的积分配置
	@GetMapping("/queryDynamicConditionBySchoolId")
	public Map<String, Object> queryDynamicConditionBySchoolId(Integer schoolId){
		return service.queryDynamicConditionBySchoolId(schoolId);
	}
	//修改配置内容
	@PostMapping("/addDynamicCondition")
	public Map<String, Object> addDynamicCondition(@RequestBody DynamicCondition dynamicCondition){
		
		Integer dynamicConditionId=dynamicCondition.getDynamicConditionId();
		if (dynamicConditionId==null) {
			return service.addDynamicCondition(dynamicCondition);
		}else {
			return service.changeDynamicCondition(dynamicCondition);
		}
	}
	 //活动规则
	@GetMapping("applet/queryDynamicConditionByStudentId")
	public Map<String, Object> queryDynamicConditionByStudentId(Integer studentId){
		return service.queryDynamicConditionByStudentId(studentId);
	}
}
