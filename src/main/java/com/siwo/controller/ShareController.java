package com.siwo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.service.ShareService;

@RestController
public class ShareController {

	@Autowired
	private ShareService service;
	
	@GetMapping("/applet/shareQrCode")
	public Map<String, Object> shareQrCode(String scene,String page,String width,String appId,String secret,HttpServletRequest req){
		return service.ShareQrCode(scene, page, width,appId,secret,req);
	}
	
}
