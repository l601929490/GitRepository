package com.siwo.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.service.ScoreGetScoreService;

/**
 * 签到获取积分
 * @author jiale xu
 *
 */
@RestController
public class SignController {
	@Autowired
	private ScoreGetScoreService scoreService;
	//签到
	@GetMapping("/applet/signAddScore")
	public Map<String, Object> signAddScore(Integer uid,Integer flag){	
	
		     return scoreService.scoreService(uid,flag);	
	}
	//查询孩子今天签到了没
	@GetMapping("/applet/querySignAddScore") 
	public Map<String, Object> querySignAddScore(Integer uid,Integer flag){
		return scoreService.querySignAddScore(uid,flag);
	}
	
      
}
