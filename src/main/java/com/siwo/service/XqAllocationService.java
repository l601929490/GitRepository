package com.siwo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.siwo.model.XqClassDay;
import com.siwo.model.XqMonthly;
import com.siwo.model.XqType;

public interface XqAllocationService {

	//	课时
	public Map<String, Object> addXqClassDay(List<XqClassDay> classDays);
	
	public Map<String, Object> addXqClassWeek(Date begin,Date end);
	
	public Map<String, Object> deleteManyXqClassDay(String[] xqClassdayIds);
	
	public Map<String, Object> updateXqClassDay(XqClassDay xqClassDay);
	
	public Map<String, Object> queryXqClassDaysBySchoolId(Integer schoolId);
	
	public Map<String, Object> queryXqClassDaysByClassId(Integer classId);
	
	
	
	//	学情类别
	public Map<String, Object> addXqType(XqType xqType);
	
	public Map<String, Object> deleteManyXqType(String[] xqTypeIds);
	
	public Map<String, Object> updateXqType(XqType xqType);
	
	public Map<String, Object> queryXqTypes(Integer schoolId);
	
	
	
	//	月度点评
	public Map<String, Object> addXqMonthly(XqMonthly xqMonthly);
	
	public Map<String, Object> updateXqMonthly(XqMonthly xqMonthly);
	
	public Map<String, Object> deleteXqMonthly(Integer xqMonthlyId);
	
	public Map<String, Object> queryStudentAndXqMonthly(Integer classId,Integer schoolId,String month);
	
	public Map<String, Object> queryXqMonthly(Integer studentId,Integer classId);
}
