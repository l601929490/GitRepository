package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Client;

public interface WxLoginService {

	public Map<String, Object> addUserInfo(Client request,BindingResult result);
	
	public Map<String, Object> getPhoneNumber(Client request);
	
	public Map<String, Object> selectIdentity(String appId,String phone,Integer schoolId,String openId);

	public Map<String, Object> everyLogon(String phone,Integer schoolId,String openId);
}
