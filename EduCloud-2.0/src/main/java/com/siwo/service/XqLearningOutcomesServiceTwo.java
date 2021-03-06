package com.siwo.service;

import java.util.List;
import java.util.Map;

import com.siwo.model.XqLearningOutcomes;

public interface XqLearningOutcomesServiceTwo {

	public Map<String, Object> addXqLearningOutcomes(XqLearningOutcomes outcome);
	
	public Map<String, Object> queryXqLearningOutcomesByStudentId(Integer studentId,Integer schoolId,Integer classId);
	
	public Map<String, Object> queryXqLearningOutcomesByClassId(Integer classId, Integer classDayId,Integer schoolId);
	
	public Map<String, Object> queryLineGraph(Integer classId,Integer studentId,Integer schoolId);
	
	public void automaticGrading();
}
