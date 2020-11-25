package com.siwo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.School;
import com.siwo.model.SchoolHonor;
import com.siwo.model.SchoolSlideshow;

public interface SchoolService {

	public Map<String, Object> addSchool(School school,BindingResult result);
	
	public Map<String, Object> limitSchool(Integer page,Integer limit,Integer companyId);
	
	public Map<String, Object> superAdminLimitSchool(Integer page,Integer limit);
	
	public Map<String, Object> addSchoolSlideshow(String slideshowImg,Integer schoolId,Date slideshowCreationTime,String slideshowCreator);
	
	public Map<String, Object> limitSchoolSlideshow(Integer page,Integer limit,Integer schoolId);
	
	public Map<String, Object> deleteManySchool(String[] schoolIds);
	
	public Map<String, Object> addSchoolHonor(String honorImg,Integer schoolId,String honorCreator,Date honorCreationTime);
	
	public Map<String, Object> limitSchoolHonor(Integer page,Integer limit,Integer schoolId);
	
	public Map<String, Object> updateSchoolSlideshow(SchoolSlideshow slideshow);
	
	public Map<String, Object> updateSchoolHonor(SchoolHonor honor);
	
	public Map<String, Object> deleteSchoolSlideshowById(Integer schoolSlideshowId);
	
	public Map<String, Object> deleteManySchoolSlideshow(String[] schoolSlideshowId);
	
	public Map<String, Object> deleteSchoolSchoolHonorId(Integer schoolHonorId);
	
	public Map<String, Object> deleteManySchoolHonor(String[] SchoolHonorIds);
	
	public Map<String, Object> querySchoolBySchoolId(Integer schoolId);
	
	public Map<String, Object> queryCompanyBySchoolId(Integer schoolId);
	
	public Map<String, Object> querySchoolByCompanyId(Integer companyId);
	
	public Map<String, Object> wxQuerySchoolByCompanyId(Integer companyId,String phone,Integer schoolId);
	
	public Map<String, Object> querySchoolById(Integer schoolId);
	
	public Map<String, Object> updateSchool(School school);
	
	//	切换校区
	public Map<String, Object> switchToSchoolId(Integer schoolId,String phone,String openId);
	
	//	模糊查询校区
	public Map<String, Object> querySchoolIdLikeSchoolName(String schoolName,Integer companyId);
}
