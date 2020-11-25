package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.MyClass;

public interface MyClassService {

	public Map<String, Object> addClass(MyClass myClass,BindingResult result);
	
	public Map<String, Object> queryById(Integer id);
	
	public Map<String, Object> queryClassByPhone(String phone,Integer idNum,Integer schoolId);
	
	public Map<String, Object> queryAllClassName();
	
	public Map<String, Object> queryByPhoneAndIder(String phone,Integer idNum,Integer schoolId);
	
	public Map<String, Object> getAllClass(Integer schoolId);
	
	public Map<String, Object> limitClass(Integer page, Integer limit,Integer schoolId);
	
	public Map<String, Object> deleteclassId(Integer classId);
	
	public Map<String, Object> deleteManyClass(String[] classIds);
	
	public Map<String, Object> updateClass(MyClass myClass);
	
	public Map<String, Object> queryClassByStudentId(Integer studentId,Integer schoolId);
	
	public Map<String, Object> wxQueryClassByStudentId(Integer studentId,Integer schoolId);
	
	public Map<String, Object> queryClassByName(String className,Integer schoolId);
	
	public Map<String, Object> queryByTeacherId(Integer teacherId);
	
	public Map<String, Object> querySumUnfinished(Integer studentId,Integer schoolId);
}
