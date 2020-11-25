package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.ScoreDisable;
import com.siwo.service.ScoreDisableService;

@RestController
public class ScoreDisableController {
	
	@Autowired
	private ScoreDisableService service;
	@GetMapping("applet/queryScoreDisable")
	public Map<String, Object> appletqueryScoreDisable(Integer schoolId){
		return service.queryScoreDisable(schoolId);
	}
	
	@GetMapping("queryScoreDisable")
	public Map<String, Object> queryScoreDisable(Integer schoolId){
		return service.queryScoreDisable(schoolId);
	}
	@GetMapping("insertScoreDisable")
	public Map<String, Object> insertScoreDisable(ScoreDisable scoreDisable){
		return service.insertScoreDisable(scoreDisable);
	}
	@GetMapping("updateScoreDisable")
	public Map<String, Object> updateScoreDisable(ScoreDisable scoreDisable){
		return service.updateScoreDisable(scoreDisable);
	}
}
