package com.siwo.serviceImpl;

import java.io.File;
/**
 * 印刷文字识别WebAPI接口调用示例接口文档(必看)：https://doc.xfyun.cn/rest_api/%E5%8D%B0%E5%88%B7%E6%96%87%E5%AD%97%E8%AF%86%E5%88%AB.html
 * 上传图片base64编码后进行urlencode要求base64编码和urlencode后大小不超过4M最短边至少15px，最长边最大4096px支持jpg/png/bmp格式
 * (Very Important)创建完webapi应用添加合成服务之后一定要设置ip白名单，找到控制台--我的应用--设置ip白名单，如何设置参考：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=41891
 * 错误码链接：https://www.xfyun.cn/document/error-code (code返回错误码时必看)
 * @author iflytek
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.siwo.commons.FileUtil;
import com.siwo.commons.HttpUtil;

@Service
public class WebOCR {
	// OCR webapi 接口地址
	private static final String WEBOCR_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/general";
	// 应用ID (必须为webapi类型应用，并印刷文字识别服务，参考帖子如何创建一个webapi应用：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=36481)
	private static final String APPID = "5f891b27";
	// 接口密钥(webapi类型应用开通印刷文字识别服务后，控制台--我的应用---印刷文字识别---服务的apikey)
	private static final String API_KEY = "0408b5c82285bd70a0f1df3d199aa109";
	// 是否返回位置信息
	private static final String LOCATION = "false";
	// 语种(可选值：en（英文），cn|en（中文或中英混合)
	private static final String LANGUAGE = "cn|en";
	// 图片地址,图片最短边至少15px，最长边最大4096px，格式jpg、png、bmp
//	private static final String PIC_PATH = "C:\\Users\\EDZ\\Desktop\\企业微信截图_16031022697616.png";

	/**
	 * OCR WebAPI 调用示例程序
	 * 
	 * @param args
	 * @throws IOException
	 */
	public Map<String, Object> getOCR(String filePath){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, String> header;
			header = buildHttpHeader();
			byte[] imageByteArray = FileUtil.read(filePath);
			String imageBase64 = new String(Base64.encodeBase64(imageByteArray),"UTF-8");
			String result = HttpUtil.doPost1(WEBOCR_URL, header, "image=" + URLEncoder.encode(imageBase64,"UTF-8"));

			JSONObject json = JSONObject.parseObject(result);
			JSONObject data = (JSONObject)json.get("data");
			String text = "";
			@SuppressWarnings("unchecked")
			List<JSONObject> block = (List<JSONObject>)data.get("block");
			for (JSONObject object : block) {
				@SuppressWarnings("unchecked")
				List<JSONObject> line = (List<JSONObject>) object.get("line");
				for (JSONObject jsonObject2 : line) {
					@SuppressWarnings("unchecked")
					List<JSONObject> word = (List<JSONObject>) jsonObject2.get("word");
					for (JSONObject jsonObject : word) {
						String content = (String) jsonObject.get("content");
						text += content;
					}
				}
			}
//			text = new String(text.getBytes("UTF-8"),"GBK");
//			System.out.println(text);
			map.put("code", 0);
			map.put("data", text);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "识别失败");
			e.printStackTrace();
		}
		
//		System.out.println("OCR WebAPI 接口调用结果：" + result);
		
	//  错误码链接：https://www.xfyun.cn/document/error-code (code返回错误码时必看)
		File file = new File(filePath);
		file.delete();
		return map;
	}

	/**
	 * 组装http请求头
	 */
	private Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
		String curTime = System.currentTimeMillis() / 1000L + "";
		String param = "{\"location\":\"" + LOCATION + "\",\"language\":\"" + LANGUAGE + "\"}";
		String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
		String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		header.put("X-Param", paramBase64);
		header.put("X-CurTime", curTime);
		header.put("X-CheckSum", checkSum);
		header.put("X-Appid", APPID);
		return header;
	}
}