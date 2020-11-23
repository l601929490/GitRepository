package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.ScoreGoodsDetail;
import com.siwo.service.ScoreGoodsDetailService;

@RestController
public class ScoreGoodsDetailController {

	@Autowired
	private  ScoreGoodsDetailService service;
	/**
	 * 小程序的学生积分明细
	 * @param studentId
	 * @param flag
	 * @return
	 */
	@GetMapping("/applet/queryGoodsDetail")
	public Map<String, Object> queryGoodsDetail(Integer studentId,Integer flag){
		return service.queryGoodsDetail(studentId,flag);
	}
	/**
	 * 校区的积分明细
	 */
	@GetMapping("/queryGoodsDetailBySchoolId")
	public Map<String, Object> queryGoodsDetailBySchoolId(Integer schoolId){
		return service.queryGoodsDetailBySchoolId(schoolId);
	}
	/**
	 * 后台查询学生积分明细
	 */
	@GetMapping("queryGoodsDetailByStudentId")
	public Map<String, Object> queryGoodsDetailByStudentId(Integer studentId){
		return service.queryGoodsDetailByStudentId(studentId);
	}
	
	/**
	 * 增加积分详情	
	 */
	@PostMapping("applet/addscoreDetail")
	public Map<String, Object> addscoreDetail(ScoreGoodsDetail detail){
		return service.addscoreDetail(detail);
	}
}
