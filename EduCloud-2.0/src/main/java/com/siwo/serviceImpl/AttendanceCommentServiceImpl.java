package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.AttendanceCommentDao;
import com.siwo.model.AttendanceComment;
import com.siwo.service.AttendanceCommentService;

@Service
public class AttendanceCommentServiceImpl implements AttendanceCommentService{

	@Autowired
	private AttendanceCommentDao dao;
	
	@Override
	public Map<String, Object> addAttendanceComment(AttendanceComment comment,BindingResult result) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		
		Integer row = dao.addAttendanceComment(comment);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		}else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> deleteAttendanceComment(Integer commentAttendanceId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (commentAttendanceId == null) {
			map.put("code", 1);
			map.put("msg", "commentAttendanceId不能为空");
			return map;
		}
		
		Integer row = dao.deleteAttendanceComment(commentAttendanceId);

		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		}else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

}
