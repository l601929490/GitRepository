package com.siwo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Guardian;
import com.siwo.service.GuardianService;

@RestController
public class GuardianController {

	@Autowired
	private GuardianService service;
	
	/**
	 * 添加监护人
	 * @param guardian
	 * @param result
	 * @return
	 */
	@PostMapping("/addGuardian")
	public Map<String, Object> addGuardian(@Valid @RequestBody Guardian guardian,BindingResult result){
		guardian.setGuardianCreationTime(new Date());
		return service.addGuardian(guardian, result);
	}

	public Map<String, Object> queryGuardianBuPhone(String guardianPhone,Integer schoolId){
		return service.queryGuardianByPhone(guardianPhone,schoolId);
	}
	
	/**
	 * 获取所有监护人
	 * @return
	 */
	@GetMapping("/getAllGuardian")
	public Map<String, Object> getAllGuardian(Integer schoolId){
		return service.getAllGuardian(schoolId);
	}
	
	/**
	 * 获取监护人总数
	 * @return
	 */
	@GetMapping("/getGuardianCount")
	public Map<String, Object> getGuardianCount(Integer guardianSchoolId) {
		return service.getGuardianCount(guardianSchoolId);
	}
	
	/**
	 * 小程序获取被监护人监护的学生信息
	 * @param guardianId
	 * @return
	 */
	@GetMapping("/applet/getGuardianStudent")
	public Map<String, Object> getWxGuardianStudent(Integer guardianId,String phone){
		return service.getGuardianStudent(guardianId,phone);
	}
	
	/**
	 * 	获取监护人以及其孩子的信息
	 * @param guardianId
	 * @return
	 */
	@GetMapping("/applet/queryGuardianAndStudentByGuardianId")
	public Map<String, Object> queryGuardianAndStudentByGuardianId(Integer guardianId) {
		return service.queryGuardianAndStudentByGuardianId(guardianId);
	}
	
	/**
	 * 	分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/limitGuardian")
	public Map<String, Object> limitGuardian(Integer page,Integer limit,Integer schoolId,String guardianName){
		if (StringUtils.isEmpty(guardianName)) {
			return service.limitGuardian(page, limit,schoolId);
		}else {
			return service.queryGuardianByName(guardianName, schoolId);
		}
		
	}
	
	/**
	 *	 删除一个
	 * @param guardianId
	 * @return
	 */
	@GetMapping("/deleteGuardianById")
	public Map<String, Object> deleteGuardianById(Integer guardianId){
		return service.deleteGuardianById(guardianId);
	}
	
	/**
	 * 	删除多个
	 * @param guardianIds
	 * @return
	 */
	@GetMapping("/deleteGuardian")
	public Map<String, Object> deleteGuardianMany(String guardianIds){
		String[] ids = guardianIds.split(",");
		return service.deleteGuardianMany(ids);
	}
	
	@PostMapping("/updateGuardian")
	public Map<String, Object> updateGuardian(@RequestBody Guardian guardian) {
		guardian.setGuardianEditSession(new Date());
		return service.updateGuardian(guardian);
	}
	

	/**
	 * 	绑定监护人和学生
	 * @param guardian
	 * @return
	 */
	@PostMapping("/addGuardianStudent")
	public Map<String, Object> addGuardianStudent(@RequestBody Map<String, Object> map){
		Map<String, Object> result = new HashMap<String, Object>();
		
		String guardianIds =(String)map.get("guardianIds");
		String studentIds = (String)map.get("studentIds");
		String relationship = (String)map.get("relationship");
		Integer oneToMany = (Integer)map.get("oneToMany");
		
		String[] ids = null;
		String[] ids_2 = null;
		
		if (StringUtils.isEmpty(studentIds)&&StringUtils.isEmpty(guardianIds)) {
			result.put("code", 0);
			result.put("msg", "操作成功");
			return map;
		}else {
			if (studentIds != null) {
				ids = studentIds.split(",");
			}
			
			if (guardianIds != null) {
				ids_2 = guardianIds.split(",");
			}
		}
		return service.addGuardianStudent(ids_2, ids, relationship,oneToMany);
	}
	
	/**
	 * 按照监护人id查学生
	 * @param guardianId
	 * @return
	 */
	@GetMapping("/queryStudentByGuardianId")
	public Map<String, Object> queryStudentByGuardianId(Integer guardianId){
		return service.queryStudentByGuardianId(guardianId);
	}
	
	/**
	 * 	按照学生id查询监护人信息
	 * @param studentId
	 * @return
	 */
	@GetMapping("/queryGuardianByStudentId")
	public Map<String, Object> queryGuardianByStudentId(Integer studentId){
		return service.queryGuardianByStudentId(studentId);
	}
	
	/**
	 * 	解除绑定
	 * @param guardianId
	 * @param studentId
	 * @return
	 */
	@GetMapping("/deleteGuardianAndStudent")
	public Map<String, Object> deleteGuardianAndStudent(Integer guardianId,Integer studentId){
		return service.deleteGuardianAndStudent(guardianId, studentId);
	}
	
	/**
	 * 	一键绑定学生监护人
	 * @param schoolId
	 * @return
	 */
	@GetMapping("/bingdingStudentAndGuardian")
	public Map<String, Object> bingdingStudentAndGuardian(Integer schoolId) {
		return service.bingdingStudentAndGuardian(schoolId);
	}
}
