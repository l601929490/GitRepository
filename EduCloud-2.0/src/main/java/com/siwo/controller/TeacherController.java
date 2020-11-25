package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Teacher;
import com.siwo.service.TeacherService;

@RestController
public class TeacherController {

	@Autowired
	private TeacherService service;
	/**
	 * 	添加老师
	 * @param teacher
	 * @param result
	 * @return
	 */
	@PostMapping("/addTeacher")
	public Map<String, Object> addTeacher(@RequestBody Teacher teacher,BindingResult result){
		teacher.setTeacherCreationTime(new Date());
		return service.addTeacher(teacher, result);
	}
	
	/**
	 * 	小程序，按id查老师
	 * @param teacherId
	 * @return
	 */
	@GetMapping("/applet/queryTeacherById")
	public Map<String, Object> queryTeacherById(Integer teacherId) {
		return service.queryTeacherById(teacherId);
	}
	
	/**
	 * 	获取所有老师
	 * @return
	 */
	@GetMapping("/getAllTeacher")
	public Map<String, Object> getAllStudent(Integer schoolId){
		return service.getAllTeacher(schoolId);
	}
	
	/**
	 * 	删除一个老师
	 * @return
	 */
	@GetMapping("/deleteTeacher")
	public Map<String, Object> deleteTeacher(String teacherIds){
		if (StringUtils.isEmpty(teacherIds)) {
			return null;
		}
		String[] str = teacherIds.split(",");
		return service.deleteManyTeacher(str);
	}
	
	
	/**
	 * 	分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/limitTeacher")
//	@RequiresRoles(value={"Company", "superAdmin"}, logical= Logical.OR)
	public Map<String, Object> limitTeacher(Integer page,Integer limit,Integer schoolId,String teacherName){
		if (StringUtils.isEmpty(teacherName)) {
			return service.limitTeacher(page, limit,schoolId);
		}else {
			return service.queryTeacherByName(teacherName, schoolId);
		}
		
	}
	
	/**
	 * 修改老师信息
	 * @param teacher
	 * @return
	 */
	@PostMapping("/updateTeacher")
	public Map<String, Object> updateTeacher(@RequestBody Teacher teacher){
		return service.updateTeacher(teacher);
	}
	
	/**
	 * 	老师批修改学生作业状态
	 * @param phone
	 * @param attendanceId
	 * @param taskId
	 * @return
	 */
	@GetMapping("/applet/affirm")
	public Map<String, Object> affirm(String phone, Integer attendanceId,Integer taskId,Integer isAffirm){
		return service.affirm(phone, attendanceId,taskId,isAffirm);
	}
	
	/**
	 * 	修改老师是否展示
	 * @param teacherId
	 * @param teacherShow
	 * @param schoolId
	 * @return
	 */
	@GetMapping("/updateTeacherShowByTeacherId")
	public Map<String, Object> updateTeacherShowByTeacherId(String teacherId, String teacherShow,Integer schoolId) {
		String[] ids = teacherId.split(",");
		return service.updateTeacherShowByTeacherId(ids, teacherShow,schoolId);
	}
	
	/**
	 * 	绑定老师和班级
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	@PostMapping("/addTeacherClass")
	public Map<String, Object> addTeacherClass(@RequestBody Map<String, Object> map){
		
		Integer classId =(Integer)map.get("classId");
		String teacherIds = (String)map.get("teacherIds");
		
		String[] ids = null;
		
		if (!StringUtils.isEmpty(teacherIds)) {
			ids = teacherIds.split(",");
		}
		return service.addTeacherClass(classId, ids);
	}
	
	/**
	 * 	查询一个班所有老师
	 * @param classId
	 * @return
	 */
	@GetMapping("/queryTeacherByClassId")
	public Map<String, Object> queryTeacherByClassId(Integer classId){
		return service.queryTeacherByClassId(classId);
	}
	
	/**
	 * 	解除老师绑定班级
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	@GetMapping("/deleteTeacherAndClass")
	public Map<String, Object> deleteTeacherAndClass(Integer classId,Integer teacherId){
		return service.deleteTeacherAndClass(classId, teacherId);
	}
	
	/**
	 * 	修改老师状态
	 * @param teacherIds
	 * @param status
	 * @return
	 */
	@GetMapping("/updateTeacherStatus")
	public Map<String, Object> updateTeacherStatus(String teacherIds, String status) {
		String[] ids = teacherIds.split(",");
		return service.updateTeacherStatus(ids, status);
	}
	
	/**
	 * 	修改学生作业订正状态
	 * @param attendanceId
	 * @param correct
	 * @return
	 */
	@GetMapping("/applet/updateAttendanceCorrect")
	public Map<String, Object> updateAttendanceCorrect(Integer attendanceId,Integer correct) {
		return service.updateAttendanceCorrect(attendanceId, correct);
	}
	
	/**
	 * 	老师作业打分
	 * @param attId
	 * @param score
	 * @return
	 */
	@PostMapping("/applet/updateScore")
	public Map<String, Object> updateScore(Integer attendanceId, Integer score) {
		return service.updateScore(attendanceId, score);
	}
	
	/**
	 * 	一键提醒交作业
	 * @param classId
	 * @return
	 */
	@GetMapping("/applet/aKeyToRemind")
	public Map<String, Object> aKeyToRemind(Integer classId,Integer taskId,String pagePath) {
		return service.aKeyToRemind(classId,taskId,pagePath);
	}
}
