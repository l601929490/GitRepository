package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.ApplyDefaultImgDao;
import com.siwo.dao.ApplyMessageDao;
import com.siwo.dao.GuardianDao;
import com.siwo.model.Apply;
import com.siwo.model.ApplyMessage;
import com.siwo.model.ApplyMessageStudent;
import com.siwo.model.Guardian;
import com.siwo.model.Student;
import com.siwo.service.ApplyMessageService;

@Service
public class ApplyMessageServiceImpl implements ApplyMessageService{

	@Autowired
	private ApplyMessageDao dao;
	@Autowired
	private GuardianDao guarDao;
	
	@Override
	public Map<String, Object> addApplyMessage(ApplyMessage message,BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		
		message.setApplyMessageTime(new Date());
		
		try {
			dao.addApplyMessage(message);
			
			dao.addApplyAndApplyMessage(message.getApplyId(), message.getApplyMessageId());
			
			List<ApplyMessageStudent> students = message.getStudents();
			for (ApplyMessageStudent applyMessageStudent : students) {
				applyMessageStudent.setApplyMessageId(message.getApplyMessageId());
			}
			
//			dao.addApplyMessageStudent(students);
			
			map.put("code", 0);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 0);
			map.put("msg", "添加失败");
		}
		
		
		return map;
	}

	@Override
	public Map<String, Object> limitApplyMessage(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Integer start = (page - 1) * limit;

			List<ApplyMessage> applies = dao.limitApplyMessage(start, limit, schoolId);
			
			for (ApplyMessage applyMessage : applies) {
				
				String guardianPhone = applyMessage.getApplyMessagePhone();
				Guardian guardian = guarDao.queryGuardianByPhone(guardianPhone, schoolId);
				
				List<ApplyMessageStudent> students = dao.queryApplyMessageStudentsByApplyMessageId(applyMessage.getApplyMessageId());
				
				for (ApplyMessageStudent messageStudent : students) {
					if (guardian != null) {
						Student student = guarDao.queryStudentByGuardianIdAndStudentName(guardian.getGuardianId(), messageStudent.getApplyMessageStudentName());
						if (student != null) {
							//	已报名
							messageStudent.setIsSignUp(1);
						}else {
							//	未报名
							messageStudent.setIsSignUp(0);
						}
					}else {
						messageStudent.setIsSignUp(0);
					}
				}
				
				Apply apply = dao.queryApplyByApplyMessageId(applyMessage.getApplyMessageId());
				applyMessage.setApply(apply);
				applyMessage.setStudents(students);
			}
			
			int count = dao.getApplyMessageCount(schoolId);

			map.put("code", 0);
			map.put("data", applies);
			map.put("pageCount", count);
		} catch (Exception e) {
			map.put("code", 0);
			map.put("msg", "服务器异常，请稍后再试");
			return map;
		}
		return map;
	}


}
