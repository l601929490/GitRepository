package com.siwo.service;

import java.util.Map;

import com.siwo.model.ScoreDisable;

public interface ScoreDisableService {

	Map<String, Object> insertScoreDisable(ScoreDisable scoreDisable);

	Map<String, Object> queryScoreDisable(Integer schoolId);

	Map<String, Object> updateScoreDisable(ScoreDisable scoreDisable);
	
}
