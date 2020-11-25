package com.siwo.service;

import java.util.Date;
import java.util.Map;

public interface CommonsService {

	public Map<String, Object> addColorImg(String homeImg,Integer schoolId,String colour,Integer WhetherNeed,String creator,Date creationTime);
	
	public Map<String, Object> updateColorImg(String homeImg,Integer schoolId,String colour,Integer imgColorId,Integer WhetherNeed,String creator,Date creationTime);
	
	public Map<String, Object> limitColorImg(Integer page,Integer limit,Integer schoolId);
	
	public Map<String, Object> deleteColorImgById(Integer imgColorId);
	
	public Map<String, Object> deleteManyColorImg(String[] imgColorIds);
	
	public Map<String, Object> queryColorImgBySchoolId(Integer schoolId);
	
	public Map<String, Object> learningCondition(Long startTime,Long endTime,Integer classId);
	
	public Map<String, Object> queryStudentMessage(Long startTime,Long endTime,Integer classId,String studentName);
}
