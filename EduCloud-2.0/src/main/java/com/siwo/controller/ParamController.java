package com.siwo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Param;
import com.siwo.service.ParamService;

@RestController
public class ParamController {

	@Autowired
	private ParamService service;
	
	/**
	 * 	添加基础参数
	 * @param param
	 * @return
	 */
	@PostMapping("/addParam")
	public Map<String, Object> addParam(@RequestBody Param param){
		param.setCreationTime(new Date());
		return service.addParam(param);
	}
	
	/**
	 * 	添加录音参数
	 * @param param
	 * @return
	 */
	@PostMapping("/addRecordParam")
	public Map<String, Object> addRecordParam(@RequestBody List<Param> params){
		System.out.println(params);
		return service.addRecordParam(params);
	}
	
	/**
	 * 	删除基础参数
	 * @param paramId
	 * @return
	 */
	@GetMapping("/deleteParamByParamId")
	public Map<String, Object> deleteParamByParamId(String paramIds){
		
		String[] ids = paramIds.split(",");
		return service.deleteManyParam(ids);
	}
	
	/**
	 * 	修改基础参数
	 * @param param
	 * @return
	 */
	@PostMapping("/updateParam")
	public Map<String, Object> updateParam(@RequestBody Param param){
		param.setEditSession(new Date());
		return service.updateParam(param);
	}
	
	/**
	 * 	按学校id和参数类型查
	 * @param schoolId
	 * @return
	 */
	@GetMapping("/queryParamBySchoolId")
	public Map<String, Object> queryParamBySchoolId(Integer schoolId,String paramType){
		return service.queryParamBySchoolId(schoolId, paramType);
	}
	@GetMapping("/applet/queryParamBySchoolId")
	public Map<String, Object> wxQueryParamBySchoolId(Integer schoolId,String paramType){
		return service.queryParamBySchoolId(schoolId, paramType);
	}
	
	/**
	 * 	分页查询
	 * @param page
	 * @param limit
	 * @param schoolId
	 * @param paramType
	 * @return
	 */
	@GetMapping("/limitParam")
	public Map<String, Object> limitParam(Integer page, Integer limit,Integer paramSchoolId,String paramType,String paramName){
		
		if (StringUtils.isEmpty(paramName)) {
			return service.limitParam(page, limit, paramSchoolId,paramType);
		}else {
			return service.queryParamByParamName(paramName, paramSchoolId, paramType);
		}
	}
	
	@GetMapping("/queryParamByParamName")
	public Map<String, Object> queryParamByParamName(String paramName, Integer paramSchoolId, String paramType) {
		return service.queryParamByParamName(paramName, paramSchoolId, paramType);
	}
}
