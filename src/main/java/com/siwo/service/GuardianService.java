package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.validation.BindingResult;

import com.siwo.model.Guardian;

public interface GuardianService {

	/**
	 * 添加监护人
	 * @param guardian
	 * @return
	 */
	public Map<String, Object> addGuardian(Guardian guardian,BindingResult result);
	
	/**
	 * 添加监护人
	 * @param guardian
	 * @return
	 */
	public Integer addGuardian_2(Guardian guardian);

	/**
	 * 按手机号查询监护人
	 * @param guardianPhone
	 * @return
	 */
	public Map<String, Object> queryGuardianByPhone(String guardianPhone,Integer schoolId);
	
	/**
	 * 获取所有监护人信息
	 * @return
	 */
	public Map<String, Object> getAllGuardian(Integer guardianSchoolId);
	
	/**
	 * 获取监护人的总数
	 * @return
	 */
	public Map<String, Object> getGuardianCount(Integer guardianSchoolId);
	
	/**
	 * 	分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
	public Map<String, Object> limitGuardian(Integer page,Integer limit,Integer guardianSchoolId);
	
	/**
	 *	 删除一个
	 * @param guardianId
	 * @return
	 */
	public Map<String, Object> deleteGuardianById(Integer guardianId);
	
	/**
	 * 	删除多个
	 * @param guardianIds
	 * @return
	 */
	public Map<String, Object> deleteGuardianMany(String[] guardianIds);
	
	/**
	 * 	修改
	 * @param guardian
	 * @return
	 */
	public Map<String, Object> updateGuardian(Guardian guardian);
	
	/**
	 * 	绑定监护人和学生
	 * @param guardian
	 * @return
	 */
	public Map<String, Object> addGuardianStudent(String[] guardianId,String[] studentId,String relationship,Integer oneToMany);
	
	/**
	 * 小程序获取监护人监护的学生信息
	 * @param guardianId
	 * @return
	 */
	public Map<String, Object> getGuardianStudent(Integer guardianId,String phone);
	
	/**
	 * 	按照学生id查询监护人信息
	 * @param studentId
	 * @return
	 */
	public Map<String, Object> queryGuardianByStudentId(Integer studentId);
	
	/**
	 * 	解除绑定
	 * @param guardianId
	 * @param studentId
	 * @return
	 */
	public Map<String, Object> deleteGuardianAndStudent(Integer guardianId,Integer studentId);
	
	/**
	 * 	按监护人id查学生信息
	 * @param studentId
	 * @return
	 */
	public Map<String, Object> queryStudentByGuardianId(Integer guardianId);
	
	/**
	 * 	按名字模糊查询
	 * @param guardianName
	 * @param schoolId
	 * @return
	 */
	public Map<String, Object> queryGuardianByName(String guardianName,Integer schoolId);
	
	/**
	 * 	按id查询监护人以及他的孩子的信息
	 * @param guardianId
	 * @return
	 */
	public Map<String, Object> queryGuardianAndStudentByGuardianId(Integer guardianId);
	
	/**
	 * 	一键绑定监护人和学生
	 * @param schoolId
	 * @return
	 */
	public Map<String, Object> bingdingStudentAndGuardian(Integer schoolId);
}
