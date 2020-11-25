package com.siwo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Attendance;
import com.siwo.service.AttendanceService;

@RestController
@RequestMapping("/applet")
public class AttendanceController {

	@Autowired
	private AttendanceService service;
	
	/**
	 * 	交作业
	 * @param attendance
	 * @param result
	 * @return
	 */
	@PostMapping("/addAttendance")
	public Map<String, Object> addAttendance(@RequestBody Attendance attendance,BindingResult result){
		attendance.setAttendanceTime(new Date());
		attendance.setIsAffirm(0);
		attendance.setCorrect(0);
		attendance.setMakeUp(0);
		return service.addAttendance(attendance,result);
	}
	
	/**
	 * 	修改提交作业
	 * @param attendance
	 * @return
	 */
	@PostMapping("/updateAttendance")
	public Map<String, Object> updateAttendance(@RequestBody Attendance attendance){
		return service.updateAttendance(attendance);
	}
	
	/**
	 * 	按照任务id查找评论
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/queryAttendanceByTaskId")
	public Map<String, Object> queryAttendanceByTaskId(Integer taskId,Integer studentId,Integer idNum,Integer classId){
		return service.queryAttendanceByTaskId(taskId,studentId,idNum,classId);
	}
	
	/**
	 * 	按学生id和taskId查找作业
	 */
	@GetMapping("/queryAttendanceByStudentIdAndTaskId")
	public Map<String, Object> queryAttendanceByStudentIdAndTaskId(Integer taskId, Integer studentId,Integer classId,String phone,Integer longTermUse) {
		return service.queryAttendanceByStudentIdAndTaskId(taskId, studentId,classId,phone,longTermUse);
	}
	
	/**
	 * 	删除打卡记录
	 * @param attendanceId
	 * @return
	 */
	@GetMapping("/deleteAttendanceById")
	public Map<String, Object> deleteAttendanceById(Integer attendanceId){
		return service.deleteAttendanceById(attendanceId);
	}
	
	/**
	 * 	分享作业
	 * @param phone
	 * @return
	 */
	@GetMapping("/shareTheWork")
	public Map<String, Object> shareTheWork(Integer attendanceId,String phone,Integer schoolId){
		return service.shareTheWork(attendanceId, phone, schoolId);
	}
	
	/**
	 * 	排行榜
	 * @param classId
	 * @return
	 */
	@GetMapping("/queryPhoneByClassId")
	public Map<String, Object> queryPhoneByClassId(Integer classId,String phone,Integer schoolId,Integer studentId) {
		return service.queryPhoneByClassId(classId,phone,studentId);
	}
	
	//	点赞
	@GetMapping("/addLikeAttendance")
	public Map<String, Object> addLikeAttendance(Integer attendanceId, String phone,Integer isLikeColck,Integer id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (attendanceId == null) {
			map.put("code", 1);
			map.put("msg", "attendanceId不能为空");
			return map;
		}
		
		if (phone == null) {
			map.put("code", 1);
			map.put("msg", "phone不能为空");
			return map;
		}
		
		if (isLikeColck == null) {
			map.put("code", 1);
			map.put("msg", "code不能为空");
			return map;
		}
		
		if (isLikeColck == 1) {
			return service.addLikeAttendance(attendanceId, phone);
		}else if (isLikeColck == 2) {
			return service.deleteLikeAttendance(attendanceId, phone);
		}else {
			map.put("code", 1);
			map.put("msg", "code不正确");
			return map;
		}
	}
	
	@GetMapping("/FindYourOwnHomework")
	public Map<String, Object> FindYourOwnHomework(Integer taskId,Integer classId,Integer studentId){
		return service.FindYourOwnHomework(taskId, classId, studentId);
	}
}
