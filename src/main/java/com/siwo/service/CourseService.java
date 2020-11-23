package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Brief;
import com.siwo.model.Course;
import com.siwo.model.Video;

public interface CourseService {

	public Map<String, Object> addCourse(Course course,Brief brief);
	
	
}
