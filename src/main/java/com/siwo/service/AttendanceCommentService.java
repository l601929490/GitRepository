package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.AttendanceComment;

public interface AttendanceCommentService {

	//	新增评论
	public Map<String, Object> addAttendanceComment(AttendanceComment comment,BindingResult result);
	
	//	删除评论
	public Map<String, Object> deleteAttendanceComment(Integer commentAttendanceId);
	
	//	查询评论
//	public Map<String, Object> queryCommentsByAttendanceId(Integer attendanceId);
}
