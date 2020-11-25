package com.siwo.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

	public Map<String, Object> importExcul(MultipartFile file,String name);
	
	public void exportExculStudent(HttpServletResponse response,Integer schoolId);
	
	public void exportExculApplyMessage(HttpServletResponse response,Integer schoolId);
	
	public Map<String, Object> importGuardianExcul(MultipartFile file,String name);
	
	public void exportExculGuardian(HttpServletResponse response,Integer schoolId);

	public void exportExculScoreOrder(HttpServletResponse response, Integer schoolId);


}
