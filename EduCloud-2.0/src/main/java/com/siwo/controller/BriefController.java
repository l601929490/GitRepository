package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Brief;
import com.siwo.service.BriefService;

@RestController
public class BriefController {

	@Autowired
	private BriefService service;

	@PostMapping("/addBrief")
	public Map<String, Object> addBrief(@Valid @RequestBody Brief brief, BindingResult result) {
		return service.addBrief(brief,result);
	}

	@GetMapping("/applet/getBriefByClassId")
	public Map<String, Object> queryBriefByClassId(String classId,Integer schoolId) {
		Map<String, Object>map = null;
		if (classId == null) {
			map = service.queryAllBrief(schoolId);
		}else {
			Integer id = Integer.valueOf(classId);
			map = service.queryBriefByClassId(id);
		}
		return map;
	}

	@GetMapping("/queryAllBrief")
	public Map<String, Object> queryAllBrief(Integer schoolId) {
		return service.queryAllBrief(schoolId);
	}
	
	@GetMapping("/limiBriefs")
	public Map<String, Object> limiBriefs(Integer page, Integer limit,Integer schoolId,String briefContent){
		if (StringUtils.isEmpty(briefContent)) {
			return service.limiBriefs(page, limit, schoolId);
		}else {
			//	后台系统按课程标题模糊查询课程
			return service.queryBriefsByBriefContent(briefContent, schoolId);
		}
		
	}
	
	@GetMapping("/applet/queryBriefsBySchoolIdAndIsChoiceness")
	public Map<String, Object> queryBriefsBySchoolIdAndIsChoiceness(Integer schoolId, Integer isChoiceness,Integer classId,String briefContent) {
		return service.queryBriefsBySchoolIdAndIsChoiceness(schoolId, isChoiceness,classId,briefContent);
	}
	
	@PostMapping("/updateBrief")
	public Map<String, Object> updateBrief(@RequestBody Brief brief) {
		brief.setEditSession(new Date());
		return service.updateBrief(brief);
	}
	
	/**
	 * 	绑定班级
	 * @param map
	 * @return
	 */
	@PostMapping("/addBriefClass")
	public Map<String, Object> addBriefClass(@RequestBody Map<String, Object>map) {
		String classIds = (String)map.get("classIds");
		Integer briefId = (Integer)map.get("briefId");
		
		String[] ids = null;
		if (!StringUtils.isEmpty(classIds)) {
			ids = classIds.split(",");
		}
		return service.addBriefClass(ids, briefId);
	}
	
	/**
	 * 	查询一个课程绑定的班级
	 */
	@GetMapping("/queryMyClassByBriefId")
	public Map<String, Object> queryMyClassByBriefId(Integer briefId) {
		return service.queryMyClassByBriefId(briefId);
	}
	
	/**
	 * 	删除课程
	 * @param briefIds
	 * @return
	 */
	@GetMapping("/deleteBrief")
	public Map<String, Object> deleteBrief(String briefIds) {
		String[]ids = briefIds.split(",");
		return service.deleteBrief(ids);
	}
	

	/**
	 * 	小程序查询课程详细信息
	 * @param briefId
	 * @return
	 */
	@GetMapping("/applet/queryBriefByBriefId")
	public Map<String, Object> queryBriefByBriefId(Integer briefId) {
		return service.queryBriefByBriefId(briefId);
	}
	
}
