package com.siwo.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siwo.model.Client;
import com.siwo.service.WxLoginService;

@Controller
@RequestMapping("/applet")
public class WxLoginController {

	@Autowired
	private WxLoginService service;
	
	/**
	 * 	微信小程序登录
	 * @param loginRequest
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@PostMapping("/wxLogin")
	public Map<String, Object> wxLogin(@Valid Client loginRequest,BindingResult bindingResult){
		return service.addUserInfo(loginRequest, bindingResult);
	}
	
	@ResponseBody
	@PostMapping("/wxGetPhone")
	public Map<String, Object> wxGetPhone(Client request){
		return service.getPhoneNumber(request);
	}
	
	@ResponseBody
	@GetMapping("/selectIdentity")
	public Map<String, Object> selectIdentity(String appId,String phone,Integer schoolId,String openId){
		return service.selectIdentity(appId, phone,schoolId,openId);
	}
	
	@ResponseBody
	@GetMapping("/everyLogon")
	public Map<String, Object> everyLogon(String phone, Integer schoolId, String openId) {
		return service.everyLogon(phone, schoolId, openId);
	}
}
