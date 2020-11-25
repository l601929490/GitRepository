package com.siwo.model;

import com.alibaba.fastjson.JSONArray;

public class OpenComponentParams {

	/**
	 * 第三方平台appId
	 */
	public static final String appId = "wxe641dd1568ab4dad";
	
	/**
	 * 	授权的APPid
	 */
	public static String userAppid;
	
	/**
	 * AppSecret：
	 */
	public static final String secret = "09788571bcda56f03a85811ac2ddaa0a";

	/**
	 * 第三方平台 消息加解密Key
	 */
	public static final String aesKey = "siwokeji43weizifuchuandexiaoxijiajiemiyong0";

	/**
	 * 第三方平台 消息校验Token
	 */
	public static final String COMPONENT_TOKEN = "siwoedutoken00";
	
	/**
	 * 小程序模板Id
	 */
	public static final String template_id = "4";
	
	/**
	 * 	请求成功之后返回的token
	 */
	public static String ACCESS_TOKEN;
	
	/**
	 * 预授权码
	 */
	public static String pre_auth_code="";
	
	/**
	 * 第三方ticket
	 */
	public static String TICKET = "";
	
	/**
	 * 	令牌
	 */
	public static String authorizer_access_token;
	
	/**
	 * 刷新令牌时使用
	 */
	public static String authorizer_refresh_token;
	
	/**
	 * 小程序的页面列表
	 */
	public static JSONArray item_list;
	
	/**
	 * 所有设置的类目
	 */
	public static JSONArray categories;
}
