package com.siwo.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.siwo.commons.HttpClientUntil;
import com.siwo.dao.WinXiApi;
import com.siwo.model.OpenComponentParams;;

@RestController
public class CallbackController {

	@Resource
	private WinXiApi winXiApi;

	@RequestMapping("/callback")
	public ModelAndView callback(HttpServletRequest request) {

		Map<String, String[]> parameterMap = request.getParameterMap();
		if (parameterMap.containsKey("auth_code") && parameterMap.containsKey("expires_in")) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("authorization_code", StringUtils.join(parameterMap.get("auth_code"), ","));
			params.put("component_appid", OpenComponentParams.appId);

			// 获取小程序的凭据和授权信息
			JSONObject result = winXiApi.apiQueryAuth(OpenComponentParams.ACCESS_TOKEN, params);

			OpenComponentParams.userAppid = (String) result.getJSONObject("authorization_info").get("authorizer_appid");

			OpenComponentParams.authorizer_refresh_token = (String) result.getJSONObject("authorization_info")
					.get("authorizer_refresh_token");

			// 保存调用凭据和授权信息
			if (OpenComponentParams.ACCESS_TOKEN == null || OpenComponentParams.ACCESS_TOKEN.trim().length() == 0) {
				System.out.println("空");
			} else {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("component_appid", OpenComponentParams.appId);
				param.put("authorizer_appid", OpenComponentParams.userAppid);
				param.put("authorizer_refresh_token", OpenComponentParams.authorizer_refresh_token);
				Map<String, Object> TOKEN = winXiApi.getAuthorizerAccessToken(OpenComponentParams.ACCESS_TOKEN, param);

				System.out.println("TOKEN:" + TOKEN);

				OpenComponentParams.authorizer_access_token = (String) TOKEN.get("authorizer_access_token");
				System.out.println("获取authorizer_access_token");
				System.out.println(OpenComponentParams.authorizer_access_token);

//				System.out.println("开始上传");
//				this.uploadCode();
			}
		}
		ModelAndView modelAndView = new ModelAndView("info");
		return modelAndView;
	}

	/**
	 * 授权之后，上传代码
	 */
	@RequestMapping("/uploadCode")
	public Map<String, Object> uploadCode() {
		JSONObject params = new JSONObject();
		params.put("template_id", OpenComponentParams.template_id);
		params.put("user_version", "eduCloud-1.0");
		params.put("user_desc", "uploadCode");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("extEnable", false);
		jsonObject.put("extAppid", OpenComponentParams.userAppid);
		params.put("ext_json", jsonObject.toString());

		Map<String, Object> codeResult = winXiApi.uploadCode(OpenComponentParams.authorizer_access_token, params);

		System.out.println("uploadCode-------------------:" + codeResult);

		this.getCodePage(codeResult);

		return codeResult;
	}

	/**
	 * 上传代码成功之后，获取已上传的代码列表
	 */
	public void getCodePage(Map<String, Object> codeResult) {
		Integer code = (Integer) codeResult.get("errcode");
		if (code == 0) {
			JSONObject result = winXiApi.getCodePage(OpenComponentParams.authorizer_access_token);
			OpenComponentParams.item_list = result.getJSONArray("page_list");
			System.out.println("获取已上传的代码列表-------------------:" + OpenComponentParams.item_list);
			this.getcategory();
		}
	}

	/**
	 * 获取已设置的所有类目
	 */
	public void getcategory() {
		JSONObject json = winXiApi.getAllcategories(OpenComponentParams.authorizer_access_token);
		if (json.getInteger("errcode") == 0) {

			OpenComponentParams.categories = json.getJSONArray("categories");

			System.out.println("获取所有设置的类目-------" + OpenComponentParams.categories);
		} else {
			System.out.println("错误信息-------" + json.get("errmsg"));
		}
	}

	/**
	 * 获取体验二维码
	 * 
	 * @param codeResult
	 */
	@GetMapping("/getQrCode")
	public Map<String, Object> getQrCode(HttpServletResponse response) {
		String path1 = "pages/index/index";
		@SuppressWarnings("deprecation")
		String path = URLEncoder.encode(path1);

		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", OpenComponentParams.authorizer_access_token);
		param.put("path", path);

		String result = HttpClientUntil.doGetQr("https://api.weixin.qq.com/wxa/get_qrcode", param);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("img", result);
		System.out.println("返回数据：-----" + result);
		return map;
	}

	/**
	 * 提交审核
	 */
	@GetMapping("/submitAudit")
	public Map<String, Object> SubmitAudit() {

		Map<String, Object> params = new HashMap<String, Object>();

		JSONArray item_list = new JSONArray();

		String address = OpenComponentParams.item_list.getString(0);
		JSONObject json = OpenComponentParams.categories.getJSONObject(0);

		Map<String, Object> itemList = new HashMap<String, Object>();
		// 拼接类目与页面地址
		itemList.put("address", address);
		itemList.put("tag", "教育助手");
		itemList.put("first_class", json.getString("first_class"));
		itemList.put("second_class", json.getString("second_class"));
		itemList.put("first_id", json.getString("first_id"));
		itemList.put("second_id", json.get("second_id"));
		itemList.put("title", "云教育助手");
		item_list.add(itemList);

		JSONObject ugc = new JSONObject();
		JSONArray ugcArray = new JSONArray();
		ugcArray.add(1);
		ugcArray.add(2);
		ugcArray.add(3);
		ugcArray.add(4);
		ugc.put("scene", ugcArray);
		JSONArray ugcMethod = new JSONArray();
		ugcMethod.add(1);
		ugc.put("method", ugcMethod);
		ugc.put("has_audit_team", 1);
		ugc.put("audit_desc", "安全");

		params.put("item_list", item_list);
		params.put("version_desc", "eduCloud 教育助手，帮助老师与家长更有效的互动");
		params.put("ugc_declare", ugc);

		Map<String, Object> map = winXiApi.submitAudit(OpenComponentParams.authorizer_access_token, params);

		System.out.println("请求的参数：----------" + params);
		System.out.println("返回的结果:-----------" + map);
		return map;
	}

	@GetMapping("/getAuditResult")
	public Map<String, Object> getAuditResult() {

		Map<String, Object> map = winXiApi.getAuditResult(OpenComponentParams.authorizer_access_token);

		return map;
	}

	@GetMapping("/issuedAudit")
	public Map<String, Object> issuedAudit() {

		JSONObject params = new JSONObject();

		Map<String, Object> map = winXiApi.issuedAudit(OpenComponentParams.authorizer_access_token, params);

		return map;
	}
}
