package com.siwo.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.ShiroException;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(value= {UnavailableSecurityManagerException.class,
			ShiroException.class})
    public Map<String, Object> testExceprion() {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 403);
		map.put("msg", "您没有权限，请联系管理员授权后再试");
        return map;
    }
	
}
