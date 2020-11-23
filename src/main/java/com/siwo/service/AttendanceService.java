package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Attendance;

public interface AttendanceService {
	
	public Map<String, Object> addAttendance(Attendance attendance,BindingResult result);
	
	public Map<String, Object> queryAttendanceByTaskId(Integer taskId,Integer studentId,Integer idNum,Integer classId);
	
	public Map<String, Object> updateAttendance(Attendance attendance);

	public Map<String, Object> deleteAttendanceById(Integer attendanceId);
	
	public Map<String, Object> shareTheWork(Integer attendanceId,String phone,Integer schoolId);
	
	public Map<String, Object> queryPhoneByClassId(Integer classId,String phone,Integer studentId);
	
	public Map<String, Object> addLikeAttendance(Integer attendanceId,String phone);
	
	public Map<String, Object> deleteLikeAttendance(Integer attendanceId,String phone);
	
	public Map<String, Object> queryAttendanceByStudentIdAndTaskId(Integer taskId,Integer studentId,Integer classId,String phone,Integer longTermUse);
	
	public void updateStudentFateAndCount(Attendance attendance, boolean isAdd);
	
	public Map<String, Object> queryAttendanceDetails(Integer schoolId,Integer classId);
	
	public Map<String, Object> FindYourOwnHomework(Integer taskId,Integer classId,Integer studentId);
}
