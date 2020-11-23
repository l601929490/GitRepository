package com.siwo.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.siwo.model.Brief;
import com.siwo.model.Course;
import com.siwo.service.CourseService;

@RestController
public class CourseController {

	@Autowired
	private CourseService service;

	@PostMapping("/addCourse")
	public Map<String, Object> addCourse(HttpServletRequest request){
		
		InputStreamReader insr;
		try {
			insr = new InputStreamReader(request.getInputStream(),"utf-8");
			String result = "";
			int respInt = insr.read();
			while(respInt != -1){
			result += (char)respInt;
			respInt = insr.read();
			}
			
			//	解析接送数据
			JSONObject json = JSONObject.parseObject(result);
			
			Course course = new Course();
			course.setCourseImg(json.getString("courseImg"));
			course.setCourseContent(json.getString("courseContent"));
			
			Brief brief = new Brief();
			brief.setBriefContent(json.getString("briefContent"));
			brief.setBriefWhether(json.getString("briefWhether"));
			brief.setBriefImg(json.getString("briefImg"));
			brief.setClassId(json.getInteger("classId"));
			brief.setSchoolId(json.getInteger("schoolId"));
			
//			CourseViderAddress video = new CourseViderAddress();
//			video.setVideoSum(0);
//			video.setVideoTitle(json.getString("videoTitle"));
//			video.setVideoTime(40d);
//			video.setVideoImg(json.getString("videoImg"));
//			video.setVideoAddress(json.getString("videoAddress"));
			
			
			return service.addCourse(course, brief);
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		}

}
