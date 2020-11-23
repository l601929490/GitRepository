package com.siwo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.siwo.dao.WinXiApi;
import com.siwo.model.OpenComponentParams;

@RestController
public class PreAuthController {

	@Resource
    private WinXiApi winXiApi;
 
    @GetMapping("/tpreauthapi")
    public ModelAndView testPreAuthApi() {
 
        Map<String, Object> params = new HashMap<>();
        params.put("component_appid", OpenComponentParams.appId);// 前面已经获取到的平台appid
 
        // 获取预授权码 preAuthCode
        Map<String, Object> preauthcode =
                winXiApi.preauthcode(OpenComponentParams.ACCESS_TOKEN, params);// 前面已经获取的平台token
        
        System.out.println(preauthcode+"--------------------------PPPPP");
        
        // 授权地址需要的参数信息
        Map<String, Object> result = new HashMap<>();
        result.put("component_appid", OpenComponentParams.appId);
        result.put("pre_auth_code", preauthcode.get("pre_auth_code"));// 预授权码
        result.put("redirect_uri", "https://edu.siwonet.com/callback");// 回调url
        result.put("auth_type", "2");
        return new ModelAndView("auth", result);// 返回我们自己写的授权页面
    }
}
