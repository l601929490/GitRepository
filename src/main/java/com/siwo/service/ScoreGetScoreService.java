package com.siwo.service;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.siwo.model.ScoreGetScore;

public interface ScoreGetScoreService {
	public Map<String,Object> addScore(ScoreGetScore score);

	public Map<String, Object> scoreService(Integer uid, Integer flag);


	public Map<String, Object> addGoodsReview(Integer attendanceId,Integer affirm);

	public Map<String, Object> addStudentUserIntegral( Integer attendanceId, String flag,Integer makeup);
	
	
	public Map<String, Object> addGoodsReviewEvaluation(Integer attendanceId, Integer score1) ;

	public Map<String, Object> querySignAddScore(Integer uid, Integer flag);

	public Map<String, Object> queryRecipient(Integer teacher);

	public Map<String, Object> addDynamicCondition(Integer schoolId);
}
