package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.AttendanceComment;
import com.siwo.service.AttendanceCommentService;

@RestController
public class AttendanceCommentController {

	@Autowired
	private AttendanceCommentService service;
	
	//	新增评论
	@PostMapping("/applet/addAttendanceComment")
	public Map<String, Object> addAttendanceComment(@Valid AttendanceComment comment,BindingResult result){
		comment.setCommentTime(new Date());
		return service.addAttendanceComment(comment, result);
	}
	
	//	删除评论
	@PostMapping("/applet/deleteAttendanceComment")
	public Map<String, Object> deleteAttendanceComment(Integer commentAttendanceId){
		return service.deleteAttendanceComment(commentAttendanceId);
	}
}
