package com.siwo.commons;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.WxSubscribeApi;
import com.siwo.model.MyClass;
import com.siwo.model.Student;

@Service
public class SendSubscribe {

	@Autowired
	private WxSubscribeApi subApi;
	
	public void aKeyToRemind(String access_token,Student student,MyClass myClass,String openId,String pagePath,String subjectName) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> name = new HashMap<String, Object>();
		name.put("value", student.getStudentName());
		map.put("name1", name);
		
		Map<String, Object> className = new HashMap<String, Object>();
		className.put("value", myClass.getClassName());
		map.put("thing11",className);
		
		Map<String, Object> taskTitle = new HashMap<String, Object>();
		taskTitle.put("value", subjectName+"作业");
		map.put("thing9", taskTitle);
		
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("value", "作业还未完成，尽快完成作业");
		map.put("thing5", msg);
		
		Map<String, Object> time = new HashMap<String, Object>();
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		String nowtime = format.format(new Date());
		time.put("value", nowtime);
		map.put("time8", time);
		
		
		Map<String, Object>params = new HashMap<String, Object>();
		params.put("access_token", access_token);
		params.put("touser", openId);
		params.put("template_id", "dzwZhaoxdGNYPSg_E9ZkN43Va9nJLMeN9E8RgWAjh2I");
		params.put("data", map);
		params.put("page", pagePath);	
		params.put("miniprogram_state", "developer");
		System.out.println("ACCESSTOKEN:"+access_token);
		Map<String, Object> result = subApi.sendMessage(access_token,params);
		System.out.println("RESULT:"+result);
	}
}
