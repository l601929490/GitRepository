package com.siwo.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreGoodsType;
import com.siwo.model.ScoreTypePage;
import com.siwo.service.ScoreGoodsTypeService;

@RestController
public class ScoreGoodsTypeController {
	@Autowired
	private ScoreGoodsTypeService service;
	
	@GetMapping("/applet/queryGoodsTypeByStudentId")
	public Map<String, Object> queryGoodsTypeByStudentId(Integer studentId){
		return service.queryGoodsTypeByStudentId(studentId);
	}
	
	@GetMapping("queryGoodsTypeBySchoolId")
	public Map<String, Object> queryGoodsTypeBySchoolId(Integer schoolId){
		 
			return service.queryGoodsTypeBySchoolId(schoolId);
		
		
	}
	
	@PostMapping("addGoodsType")
	public Map<String, Object> addGoodsType(@RequestBody ScoreGoodsType type){
		if (type.getTypeId()==null) {
			return service.addGoodsType(type);
		}else {
			return service.updateGoodsType(type);
		}
	
	}
	@GetMapping("deleteGoodsType")
	public Map<String, Object> deleteGoodsType(String typeId){
		
		 String[] strs = typeId.split(",");
	 
		return service.deleteGoodsType(strs);
	}
	
}
