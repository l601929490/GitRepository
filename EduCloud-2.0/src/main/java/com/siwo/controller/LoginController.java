package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siwo.model.Admin;
import com.siwo.service.UserLoginService;

@Controller
public class LoginController {

	@Autowired
	private UserLoginService service;
	
	@ResponseBody
	@PostMapping("/adminLogin")
	public Map<String, Object> userLogin(@RequestBody Admin admin){
		return service.theBackgroundLogin(admin);
	}
	
	@ResponseBody
	@GetMapping("/refreshUser")
	public Map<String, Object> refreshUser(String appId) {
		return service.refreshUser(appId);
	}
	
	@GetMapping("/test")
	public String test() {
		return "test.html";
	}
	
	@GetMapping("file2")
	public String file2(){
		return "file2";
	}
	
	@GetMapping("/403")
	public String loginPage() {
		return "403";
	}
}
