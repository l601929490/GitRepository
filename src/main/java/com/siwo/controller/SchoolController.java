package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.School;
import com.siwo.model.SchoolHonor;
import com.siwo.model.SchoolSlideshow;
import com.siwo.service.SchoolService;

@RestController
public class SchoolController {

	@Autowired
	private SchoolService service;
	
	@PostMapping("/addSchool")
	public Map<String, Object> addSchool(@RequestBody School school,BindingResult result){
		school.setCreationTime(new Date());
		return service.addSchool(school, result);
	}
	
	@GetMapping("/limitSchool")
	public Map<String, Object> limitSchool(Integer page, Integer limit,Integer companyId,String schoolName){
		if (companyId == null && StringUtils.isEmpty(schoolName)) {
			return service.superAdminLimitSchool(page, limit);
		}
		if (!StringUtils.isEmpty(schoolName)) {
			return service.querySchoolIdLikeSchoolName(schoolName, companyId);
		}
		return service.limitSchool(page, limit,companyId);
	}
	
	@PostMapping("/addSchoolSlideshow")
	public Map<String, Object> addSchoolSlideshow(@RequestBody Map<String, Object> map){
		String img = (String)map.get("slideshowImg");
		String id = (String)map.get("schoolId");
		String creator = (String)map.get("slideshowCreator");
		return service.addSchoolSlideshow(img, Integer.valueOf(id),new Date(),creator);
	}
	
	@GetMapping("/limitSchoolSlideshow")
	public Map<String, Object> limitSchoolSlideshow(Integer page, Integer limit, Integer schoolId) {
		return service.limitSchoolSlideshow(page, limit, schoolId);
	}
	
	@PostMapping("/updateSchoolSlideshow")
	public Map<String, Object> updateSchoolSlideshow(@RequestBody SchoolSlideshow slideshow) {
		slideshow.setSlideshowEditSession(new Date());
		return service.updateSchoolSlideshow(slideshow);
	}
	
	@PostMapping("/addSchoolHonor")
	public Map<String, Object> addSchoolHonor(@RequestBody Map<String, Object> map,String honorImg,String schoolId,String honorCreator){
		String img = (String)map.get("honorImg");
		String id = (String)map.get("schoolId");
		String creator = (String)map.get("honorCreator");
		return service.addSchoolHonor(img, Integer.valueOf(id),creator,new Date());
	}
	
	@GetMapping("/limitSchoolHonor")
	public Map<String, Object> limitSchoolHonor(Integer page, Integer limit, Integer schoolId) {
		return service.limitSchoolHonor(page, limit, schoolId);
	}
	
	@PostMapping("/updateSchoolHonor")
	public Map<String, Object> updateSchoolHonor(@RequestBody SchoolHonor honor) {
		honor.setHonorEditSession(new Date());
		return service.updateSchoolHonor(honor);
	}
	
	@GetMapping("/deleteSchoolSlideshowIds")
	public Map<String, Object> deleteSchoolSlideshow(String schoolSlideshowIds) {

		Map<String, Object> map = null;
		
		String[] ids = schoolSlideshowIds.split(",");
		
		if (ids.length == 1) {
			int studentId = Integer.parseInt(schoolSlideshowIds);
			map = service.deleteSchoolSlideshowById(studentId);
		} else if (ids.length > 1) {
			map = service.deleteManySchoolSlideshow(ids);
		}
		return map;
	}
	
	@GetMapping("/deleteSchoolHonorIds")
	public Map<String, Object> deleteSchoolHonor(String schoolHonorIds) {
		
		String[] ids = schoolHonorIds.split(",");
		Map<String, Object> map = null;
		if (ids.length == 1) {
			int SchoolHonorId = Integer.parseInt(schoolHonorIds);
			map = service.deleteSchoolSchoolHonorId(SchoolHonorId);
		}else if (ids.length > 1) {
			map = service.deleteManySchoolHonor(ids);
		}
		return map;
	}
	
	@GetMapping("/applet/querySchoolBySchoolId")
	public Map<String, Object> querySchoolBySchoolId(Integer schoolId) {
		return service.querySchoolBySchoolId(schoolId);
	}
	
	@GetMapping("/applet/queryCompanyBySchoolId")
	public Map<String, Object> queryCompanyBySchoolId(Integer schoolId) {
		return service.queryCompanyBySchoolId(schoolId);
	}
	
	@GetMapping("/applet/querySchoolByCompanyId")
	public Map<String, Object> wxQuerySchoolByCompanyId(Integer companyId,String phone,Integer schoolId) {
		if (StringUtils.isEmpty(phone)) {
			return service.querySchoolByCompanyId(companyId);
		}
		return service.wxQuerySchoolByCompanyId(companyId, phone,schoolId);
	}
	
	@GetMapping("/querySchoolByCompanyId")
	public Map<String, Object> querySchoolByCompanyId(Integer companyId) {
		return service.querySchoolByCompanyId(companyId);
	}
	
//	@RequiresRoles(value = {"Company","teacher"},logical = Logical.OR)
	@GetMapping("/querySchoolById")
	public Map<String, Object> querySchoolById(Integer schoolId){
		@SuppressWarnings("unused")
		Subject subject = SecurityUtils.getSubject();
		return service.querySchoolById(schoolId);
	}
	
	@PostMapping("/updateSchool")
	public Map<String, Object> updateSchool(@RequestBody School school) {
		return service.updateSchool(school);
	}
	
	@GetMapping("/deleteManySchool")
	public Map<String, Object> deleteManySchool(String schoolIds) {
		String[] id = schoolIds.split(",");
		return service.deleteManySchool(id);
	}
	
	@GetMapping("/applet/switchToSchoolId")
	public Map<String, Object> switchToSchoolId(Integer schoolId, String phone,String openId) {
		return service.switchToSchoolId(schoolId, phone,openId);
	}

}
