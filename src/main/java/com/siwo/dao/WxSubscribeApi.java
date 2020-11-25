package com.siwo.dao;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Param;

@FeignClient(name = "WxSubscribeApi", url = "https://api.weixin.qq.com")
@Component
public interface WxSubscribeApi {

	/**
	 * 获取小程序全局唯一后台接口调用凭据
	 * 
	 * @param params 发送的参数
	 * @return 请求结果
	 */
	@GetMapping("/cgi-bin/token")
	Map<String, Object> getAccessToken(@RequestParam("grant_type") String grant_type,@RequestParam("appid")String appid,@RequestParam("secret")String secret);
	
	/**
	 * 	获取订阅消息的个人模板列表
	 * @param access_token
	 * @return
	 */
	@GetMapping("/wxaapi/newtmpl/gettemplate")
	Map<String, Object> getTemplateList(@RequestParam("access_token") String access_token);
	
	/**
	 * 	向用户发送订阅消息
	 * @param params
	 * @return
	 */
	@PostMapping("/cgi-bin/message/subscribe/send")
	Map<String, Object> sendMessage(@RequestParam("access_token") String access_token,Map<String, Object> params);
	
	/**
	 * 	获取带参数的二维码
	 * @param access_token
	 * @param params
	 * @return
	 */
	@PostMapping("/wxa/getwxacodeunlimit")
	byte[] getQrcode(@RequestParam("access_token") String access_token,Map<String, Object> params);
}
