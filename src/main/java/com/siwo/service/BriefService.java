package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Brief;
import com.siwo.model.MyClass;

public interface BriefService {

	public Map<String, Object> addBrief(Brief brief,BindingResult result);
	
	public Map<String, Object> queryBriefByClassId(Integer classId);
	
	public Map<String, Object> queryAllBrief(Integer schoolId);
	
	public Map<String, Object> limiBriefs(Integer page, Integer limit,Integer schoolId);
	
	public Map<String, Object> queryBriefsBySchoolIdAndIsChoiceness(Integer schoolId,Integer isChoiceness,Integer classId,String briefContent);
	
	public Map<String, Object> updateBrief(Brief brief);
	
	/**
	 * 	绑定班级信息
	 * @param classId
	 * @param briefId
	 * @return
	 */
	public Map<String, Object> addBriefClass(String[] classIds,Integer briefId);
	
	/**
	 * 	查询课程绑定的班级
	 * @param briefId
	 * @return
	 */
	public Map<String, Object> queryMyClassByBriefId(Integer briefId);
	
	/**
	 * 	删除课程
	 * @param briefIds
	 * @return
	 */
	public Map<String, Object> deleteBrief(String[] briefIds);
	
	/**
	 * 	小程序查询课程详细信息
	 * @param briefId
	 * @return
	 */
	public Map<String, Object> queryBriefByBriefId(Integer briefId);
	
	public Map<String, Object> queryBriefsByBriefContent(String briefContent,Integer schoolId);
	
//	public Map<String, Object> queryBriefsBySchoolIdAndLikeSelect(Integer schoolId,String briefContent);
}
