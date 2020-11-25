package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Student;

public interface StudentService {

	/**
	 * 添加学生
	 * @param student
	 * @param result
	 * @return
	 */
	public Map<String, Object> addStudent(Student student,BindingResult result);
	
	/**
	 * 获取所有学生
	 * @return
	 */
	public Map<String, Object> getAllStudent(Integer schoolId);
	
	/**
	 * 按照班级id查找全班学生
	 * @param classId
	 * @return
	 */
	public Map<String, Object> queryAllClassStudentByClassId(Integer classId);
	
	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
	public Map<String, Object> limitStudent(Integer page,Integer limit,Integer schoolId);
	
	/**
	 * 	更新学生信息
	 * @param student
	 * @return
	 */
	public Map<String, Object> update(Student student);
	
	/**
	 * 	按照ID删除学生
	 * @param studentId
	 * @return
	 */
	public Map<String, Object> deleteStudent(Integer studentId);
	
	/**
	 * 	删除多个学生
	 * @param studentIds
	 * @return
	 */
	public Map<String, Object> deleteManyStudent(String[] studentIds);
	
	/**
	 * 	按照姓名查学生
	 * @return
	 */
	public Map<String, Object> queryStudentByName(String studentName,Integer schoolId);
	
	/**
	 * 	按照手机号和学校id查学生
	 * @param phone
	 * @param schoolId
	 * @return
	 */
	public Map<String, Object> queryStudentByPhoneAndSchoolId(String phone,Integer schoolId);
	
	/**
	 * 	绑定学生班级
	 * @param studentClassId
	 * @param studentId
	 * @return
	 */
	public Map<String, Object> addStudentClass(String[] studentClassId,String[] studentIds,Integer oneToMany);
	
	/**
	 * 	查找班级所有学生
	 * @param classId
	 * @return
	 */
	public Map<String, Object> queryStudentByClassId(Integer classId);
	
	/**
	 * 	解除学生和班级绑定的信息
	 * @param classId
	 * @param studentId
	 * @return
	 */
	public Map<String, Object> deleteStudentAndClass(Integer classId,Integer studentId);
	
	/**
	 * 	查找已分配和未分配的学生
	 * @param student
	 * @return
	 */
	public Map<String, Object> queryStudentByStatus(Integer schoolId,Integer status);
}
