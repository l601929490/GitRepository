package com.siwo.dao;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

@FeignClient(name = "weixinapi", url = "https://api.weixin.qq.com")
@Component
public interface WinXiApi {

	/**
	 * 获取第三方平台component_access_token.
	 * 
	 * @param params 发送的参数
	 * @return 请求结果
	 */
	@PostMapping("/cgi-bin/component/api_component_token")
	Map<String, Object> componentToken(Map<String, Object> params);

	/**
	 * 获取预授权码pre_auth_code.
	 * 
	 * @param componentAccessToken 请求token
	 * @param params               第三方平台方appid
	 * @return 预授权码pre_auth_code
	 */
	@PostMapping(value = "/cgi-bin/component/api_create_preauthcode")
	Map<String, Object> preauthcode(@RequestParam("component_access_token") String componentAccessToken,
			Map<String, Object> params);

	/**
	 * 使用授权码换取公众号或小程序的接口调用凭据和授权信息.
	 * 
	 * @param componentAccessToken 平台可访问授权码
	 * @param params               post的参数
	 * @return api执行结果
	 */
	@PostMapping("/cgi-bin/component/api_query_auth")
	JSONObject apiQueryAuth(@RequestParam("component_access_token") String componentAccessToken,
			Map<String, Object> params);
	
	
	/**
	 * 上传代码
	 * @param access_token
	 * @param params
	 * @return
	 */
	@PostMapping("/wxa/commit")
	Map<String, Object> uploadCode(@RequestParam("access_token") String access_token,
			Map<String, Object> params);
	
	/**
	 * 获取上传的代码列表
	 */
	@GetMapping("/wxa/get_page")
	JSONObject getCodePage(@RequestParam("access_token") String access_token);
	
	/**
	 * 获取体验二维码
	 * @param access_token
	 * @param path
	 * @return
	 */
	@GetMapping("/wxa/get_qrcode")
	Map<String, Object> getQrCode(@RequestParam("access_token") String access_token,String path);
	
	/**
	 * 获取授权凭证
	 * @param component_access_token
	 * @param params
	 * @return
	 */
	@PostMapping("/cgi-bin/component/api_authorizer_token")
	Map<String, Object> getAuthorizerAccessToken(@RequestParam("access_token") String component_access_token,Map<String, Object> params);
	
	/**
	 * 提交审核
	 * @param access_token
	 * @param params
	 * @return
	 */
	@PostMapping("/wxa/submit_audit")
	Map<String, Object> submitAudit(@RequestParam("access_token") String access_token,Map<String, Object> params);
	
	/**
	 * 获取所有已经设置的类目
	 * @param access_token
	 * @return
	 */
	@GetMapping("cgi-bin/wxopen/getcategory")
	JSONObject getAllcategories(@RequestParam("access_token") String access_token);
	
	/**
	 * 查询审核结果
	 */
	@GetMapping("wxa/get_latest_auditstatus")
	Map<String, Object> getAuditResult(@RequestParam("access_token") String access_token);
	
	/**
	 * 	发布审核
	 * @param access_token
	 * @return
	 */
	@PostMapping("wxa/release")
	Map<String, Object> issuedAudit(@RequestParam("access_token") String access_token,Map<String, Object> params);
	
	@PostMapping("wxa/release")
	Map<String, Object> placeAnOrder (@RequestParam("access_token") String access_token,Map<String, Object> params);
}
