package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Apply;
import com.siwo.service.ApplyService;

@RestController
public class ApplyController {

	@Autowired
	private ApplyService service;
	
	@PostMapping("/addApply")
	public Map<String, Object> addApply(@RequestBody Apply apply){
		return service.addApply(apply);
	}
	
	@GetMapping("/limitApply")
	public Map<String, Object> limitApply(Integer page,Integer limit,Integer schoolId,String applyTitle){
		if (!StringUtils.isEmpty(applyTitle)) {
			return service.queryApplyByApplyTitle(applyTitle, schoolId);
		}
		return service.limitApply(page, limit, schoolId);
	}
	
	@GetMapping("/applet/queryApplyBySchoolIdAndApplyShow")
	public Map<String, Object> queryApplyBySchoolIdAndApplyShow(Integer schoolId,String openId){
		return service.queryApplyBySchoolIdAndApplyShow(schoolId,openId);
	}
	
	//	修改报名活动信息
	@PostMapping("/updateApply")
	public Map<String, Object> updateApply(@RequestBody Apply apply){
		apply.setApplyEditSession(new Date());
		return service.updateApply(apply);
	}
	
	//	删除报名活动
	@GetMapping("/deleteApply")
	public Map<String, Object> deleteApply(String applyIds){
		if (StringUtils.isEmpty(applyIds)) {
			return null;
		}
		String[] ids = applyIds.split(",");
		return service.deleteApply(ids);
	}
	
}
