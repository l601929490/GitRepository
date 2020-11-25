package com.siwo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.XqLearningOutcomes;
import com.siwo.service.XqLearningOutcomesService;
import com.siwo.service.XqLearningOutcomesServiceTwo;

@RestController
public class XqLearningOutcomesControllerTwo {

	@Autowired
	private XqLearningOutcomesServiceTwo service;
	
	@PostMapping("/applet/addXqLearningOutcomes")
	public Map<String, Object> addXqLearningOutcomes(@RequestBody XqLearningOutcomes outcomes) {
		return service.addXqLearningOutcomes(outcomes);
	}
	
	@GetMapping("/applet/queryXqLearningOutcomesByStudentId")
	public Map<String, Object> queryXqLearningOutcomesByStudentId(Integer studentId,Integer schoolId,Integer classId) {
		return service.queryXqLearningOutcomesByStudentId(studentId,schoolId,classId);
	}
	
	@GetMapping("/applet/queryXqLearningOutcomesByClassId")
	public Map<String, Object> queryXqLearningOutcomesByClassId(Integer classId, Integer classDayId,Integer schoolId){
		return service.queryXqLearningOutcomesByClassId(classId, classDayId,schoolId);
	}
	
	@GetMapping("/applet/queryLineGraph")
	public Map<String, Object> queryLineGraph(Integer classId, Integer studentId,Integer schoolId) {
		return service.queryLineGraph(classId, studentId, schoolId);
	}
}
