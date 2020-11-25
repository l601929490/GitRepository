package com.siwo.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ShareService {

	public Map<String, Object> ShareQrCode(String scene,String page,String width,String appId,String secret,HttpServletRequest req);
}
