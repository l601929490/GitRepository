package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Admin;
import com.siwo.model.Client;

public interface UserLoginService {

	public Map<String, Object> theBackgroundLogin(Admin admin);
	
	public Map<String, Object> refreshUser(String appId);
}
