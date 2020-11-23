package com.siwo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siwo.service.ExcelService;

@RestController
public class ExcelController {

	@Autowired
	private ExcelService service;
	
	@PostMapping("/importExcul")
	public Map<String, Object> importExcul(MultipartFile file,String name){
		return service.importExcul(file,name);
	}
	
	@GetMapping("/exportExcul")
	public void exportExculStudent(HttpServletResponse response,Integer schoolId) {
		System.out.println(schoolId);
		service.exportExculStudent(response,schoolId);
	}
	
	@GetMapping("/exportExculApplyMessage")
	public void exportExculApplyMessage(HttpServletResponse response, Integer schoolId) {
		service.exportExculApplyMessage(response, schoolId);
	}
	
	@PostMapping("/importGuardianExcul")
	public Map<String, Object> importGuardianExcul(MultipartFile file,String name){
		return service.importGuardianExcul(file,name);
	}
	
	@GetMapping("/exportExculGuardian")
	public void exportExculGuardian(HttpServletResponse response, Integer schoolId) {
		service.exportExculGuardian(response, schoolId);
	}
	//订单导出
	@GetMapping("/exportExculScoreOrder")
	public void exportExculScoreOrder(HttpServletResponse response, Integer schoolId) {
		service.exportExculScoreOrder(response, schoolId);
	}
//	//商品导入
//	@PostMapping("/importScoreGoodsExcul")
//	public Map<String, Object> importScoreGoodsExcul(MultipartFile file,String name){
//		return service.importScoreGoodsExcul(file,name);
//	}
//	//商品导出
//	@GetMapping("/exportScoreGoodsExcul")
//	public void exportScoreGoodsExcul(HttpServletResponse response, Integer schoolId){
//	    service.exportScoreGoodsExcul(response,schoolId);
//	}
}
