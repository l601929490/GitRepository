package com.siwo.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.ScoreGetScore;
import com.siwo.service.ScoreGetScoreService;

@RestController
public class ScoreGetScoreController {

	@Autowired
	private ScoreGetScoreService service;
	
	
	@PostMapping("/applet/addScore")
	public Map<String, Object> addScore(ScoreGetScore score){
		return service.addScore(score);
	}
	//签到
	@GetMapping("/applet/scoreService")
	public Map<String, Object> scoreService(Integer uid, Integer flag) {
		return service.scoreService(uid,flag);
	}
	//老师评优
	@GetMapping("/applet/addGoodsReview")
	public Map<String, Object> addGoodsReview(Integer attendanceId,Integer affirm){
		return service.addGoodsReview(attendanceId,affirm);
	}
	//打卡
	@GetMapping("/applet/addStudentUserIntegral")
	public Map<String, Object> addStudentUserIntegral(Integer attendanceId,String flag,Integer makeup){
		return service.addStudentUserIntegral(attendanceId,flag,makeup);
	}
	//老师打分
	@GetMapping("/applet/addGoodsReviewEvaluation")
	public Map<String, Object> addGoodsReviewEvaluation(Integer attendanceId, Integer score1){
		return service.addGoodsReviewEvaluation(attendanceId,score1);
	}
	//查看老师是否是收件人
	@GetMapping("/applet/queryRecipient")
	public Map<String, Object> queryRecipient(Integer teacher){
		return service.queryRecipient(teacher);
	}
	//增加积分配置和状态
	@GetMapping("addDynamicConditions")
	public Map<String, Object> addDynamicCondition(Integer schoolId){
 
		return service.addDynamicCondition(schoolId);
	}
	 
}
