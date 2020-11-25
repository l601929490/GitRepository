package com.siwo.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class HttpClientUntil {

	//	根据code值获取sessionkey和openId方法
	public static String doGet(String url, Map<String, String> param) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		CloseableHttpResponse respons = null;

		// 	创建url
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					// 	给url中添加参数
					builder.addParameter(key, param.get(key));
				}
			}

			URI uri = builder.build();

			// 创建httpGet请求
			HttpGet get = new HttpGet(uri);

			// 执行请求
			respons = httpClient.execute(get);

			// 判断请求是否成功,状态码是否为200
			if (respons.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(respons.getEntity(), "UTF-8");
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (respons != null) {
					respons.close();
				}
				httpClient.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

		return result;
	}

	public static String doGetQr(String url, Map<String, String> param) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		CloseableHttpResponse respons = null;

		// 	创建url
		try {
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					// 	给url中添加参数
					builder.addParameter(key, param.get(key));
				}
			}

			URI uri = builder.build();

			// 创建httpGet请求
			HttpGet get = new HttpGet(uri);

			// 执行请求
			respons = httpClient.execute(get);

			HttpEntity entity = respons.getEntity();
			
			InputStream in = entity.getContent();
			File file = new File("C:\\myFile\\upload\\qr.jpg");
			OutputStream out = new FileOutputStream(file);
			
			int row = 0;
			byte[] bytes = new byte[1024];
			
			while ((row = in.read(bytes))!= -1) {
				out.write(bytes, 0, row);
			}
			in.close();
			out.close();
		
			result = "https://edu.siwonet.com/qr.jpg";
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (respons != null) {
					respons.close();
				}
				httpClient.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

		return result;
	}
	
	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}

	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}
}
