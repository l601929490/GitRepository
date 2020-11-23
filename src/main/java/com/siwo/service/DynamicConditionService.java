package com.siwo.service;

import java.util.List;
import java.util.Map;


import com.siwo.model.Activity;
import com.siwo.model.DynamicCondition;

public interface DynamicConditionService {

	public Map<String, Object> queryDynamicConditionByCompanyId(Integer companyId);
	
	public Map<String, Object> queryDynamicConditionBySchoolId(Integer schoolId);
	
	 
	public Map<String, Object> addDynamicCondition(DynamicCondition dynamicCondition);
	public Map<String, Object> changeDynamicCondition(DynamicCondition dynamicCondition);

	public Map<String, Object> queryDynamicConditionByStudentId(Integer studentId);

 
}
