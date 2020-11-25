package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.serviceImpl.WebOCR;

@RestController
public class WebOCRController {

	@Autowired
	private WebOCR webOCR;
	
	@GetMapping("/getOCRText")
	public Map<String, Object> getOCRText(String filePath){
		return webOCR.getOCR(filePath);
	}
	@GetMapping("/applet/getOCRText")
	public Map<String, Object> wxGetOCRText(String filePath){
		return webOCR.getOCR(filePath);
	}
}
