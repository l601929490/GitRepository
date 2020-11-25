package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.TeachingMaterial;
import com.siwo.service.TeachingMaterialService;

@RestController
public class TeachingMaterialController {

	@Autowired
	private TeachingMaterialService service;
	
	@PostMapping("/addTeachingMaterial")
	public Map<String, Object> addTeachingMaterial(@RequestBody TeachingMaterial teachingMaterial){
		teachingMaterial.setCreationTime(new Date());
//		if (StringUtils.isEmpty(teachingMaterial.getVanRead())) {
//			teachingMaterial.setVanRead(null);
//		}
		return service.addTeachingMaterial(teachingMaterial);
	}
	
	@GetMapping("/deleteManyTeachingMaterial")
	public Map<String, Object> deleteManyTeachingMaterial(String materialIds){
		String[] ids= materialIds.split(",");
		return service.deleteManyTeachingMaterial(ids);
	}
	
	@PostMapping("/updateTeachingMaterial")
	public Map<String, Object> updateTeachingMaterial(@RequestBody TeachingMaterial teachingMaterial){
		teachingMaterial.setEditSession(new Date());
		return service.updateTeachingMaterial(teachingMaterial);
	}
	
	@GetMapping("/limitTeachingMaterials")
	public Map<String, Object> limitTeachingMaterials(Integer page,Integer limit,Integer schoolId){
		return service.limitTeachingMaterials(page, limit, schoolId);
	}
	
	@GetMapping("/applet/queryTeachingMaterialsByGradeAndSubject")
	public Map<String, Object> queryTeachingMaterialsByGradeAndSubject(Integer grade,Integer subject,Integer schoolId){
		return service.queryTeachingMaterialsByGradeAndSubject(grade, subject, schoolId);
	}
	
	@GetMapping("/applet/queryTeachingMaterialByTeachingMaterialId")
	public Map<String, Object> queryTeachingMaterialByTeachingMaterialId(Integer teachingMaterialId){
		return service.queryTeachingMaterialByTeachingMaterialId(teachingMaterialId);
	}
	
}
