package com.siwo.service;

import java.util.Map;

import com.siwo.model.TeachingMaterial;

public interface TeachingMaterialService {

	public Map<String, Object> addTeachingMaterial(TeachingMaterial teachingMaterial);
	
	public Map<String, Object> deleteManyTeachingMaterial(String[] materialIds);
	
	public Map<String, Object> updateTeachingMaterial(TeachingMaterial teachingMaterial);
	
	public Map<String, Object> limitTeachingMaterials(Integer page,Integer limit,Integer schoolId);
	
	public Map<String, Object> queryTeachingMaterialsByGradeAndSubject(Integer grade,Integer subject,Integer schoolId);
	
	public Map<String, Object> queryTeachingMaterialByTeachingMaterialId(Integer teachingMaterialId);
}
