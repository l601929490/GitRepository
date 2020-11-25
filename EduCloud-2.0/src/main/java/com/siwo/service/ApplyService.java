package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Update;

import com.siwo.model.Apply;
import com.siwo.model.applyDefaultImg;

public interface ApplyService {

	public Map<String, Object> addApply(Apply apply);
	
	public Map<String, Object> limitApply(Integer page,Integer limit,Integer schoolId);
	
	public Map<String, Object> queryApplyBySchoolIdAndApplyShow(Integer schoolId,String openId);
	
	//	修改报名活动信息
	public Map<String, Object> updateApply(Apply apply);
	
	//	删除报名活动
	public Map<String, Object> deleteApply(String[] applyIds);
	
	public Map<String, Object> queryApplyByApplyTitle(String title,Integer schoolId);
	
	
	public Map<String, Object> addApplyDefaultImg(applyDefaultImg applyDefaultImg);
	
	public Map<String, Object> queryApplyDefaultImg(Integer schoolId);
}
