package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Teacher;

public interface TeacherService {

	public Map<String, Object> addTeacher(Teacher teacher,BindingResult result);
	
	public Map<String, Object> getAllTeacher(Integer schoolId);
	
	/**
	 * 	按id查老师
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> queryTeacherById(Integer teacherId);
	
	/**
	 * 	按照老师Id删除老师
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> deleteTeacher(Integer teacherId);
	
	/**
	 *	 批量删除
	 */
	public Map<String, Object> deleteManyTeacher(String[] teacherIds);
	
	/**
	 *	 分页查询
	 */
	public Map<String, Object> limitTeacher(Integer page,Integer limit,Integer schoolId);
	
	/**
	 * 	老师确认打卡
	 * @return
	 */
	public Map<String, Object> affirm(String phone,Integer attId,Integer taskId,Integer affirm);
	
	/**
	 * 	修改老师信息
	 * @param teacher
	 * @return
	 */
	public Map<String, Object> updateTeacher(Teacher teacher);
	
	/**
	 * 	是否展示在小程序
	 * @param teacherId
	 * @param teacherShow
	 * @return
	 */
	public Map<String, Object> updateTeacherShowByTeacherId(String[] teacherIds,String teacherShow,Integer schoolId);
	
	/**
	 * 	绑定老师和班级
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> addTeacherClass(Integer classId,String[] teacherIds);
	
	/**
	 * 	查询一个班所有老师
	 * @param classId
	 * @return
	 */
	public Map<String, Object> queryTeacherByClassId(Integer classId);
	
	/**
	 * 	解除老师绑定班级
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> deleteTeacherAndClass(Integer classId,Integer teacherId);
	
	/**
	 * 	按名字模糊查询
	 * @param teacherName
	 * @param teacherId
	 * @return
	 */
	public Map<String, Object> queryTeacherByName(String teacherName,Integer schoolId);

	/**
	 * 	修改老师状态
	 * @param teacherId
	 * @param status
	 * @return
	 */
	public Map<String, Object> updateTeacherStatus(String[] teacherIds,String status);

	/**
	 * 	修改作业订正状态
	 * @return
	 */
	public Map<String, Object> updateAttendanceCorrect(Integer attendanceId,Integer correct);
	
	/**
	 * 	老师作业打分
	 * @param attId
	 * @param score
	 * @return
	 */
	public Map<String, Object> updateScore(Integer attId,Integer score);
	
	/**
	 * 	一键提醒交作业
	 * @param classId
	 * @return
	 */
	public Map<String, Object> aKeyToRemind(Integer classId,Integer taskId,String pagePath);
}
