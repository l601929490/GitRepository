
package com.siwo.controller;

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

import com.siwo.model.MyClass;
import com.siwo.service.MyClassService;

@RestController
public class MyClassController {

	@Autowired
	private MyClassService service;
	
	@PostMapping("/addClass")
	public Map<String, Object> addClass(@Valid @RequestBody MyClass myClass,BindingResult result){
		return service.addClass(myClass,result);
	}
	
	@GetMapping("/queryById")
	public Map<String, Object> queryById(Integer id){
		return service.queryById(id);
	}
	
	@PostMapping("/updateClass")
	public Map<String, Object> updateClass(@RequestBody MyClass myClass) {
		return service.updateClass(myClass);
	}
	
//	@GetMapping("/queryClassByPhone")
//	public Map<String, Object> queryAllClass(String phone,Integer idNum){
//		return service.queryClassByPhone(phone, idNum);
//	}
	
	@GetMapping("/queryAllClassName")
	public Map<String, Object> queryAllClassName(){
		return service.queryAllClassName();
	}
	
	
	@GetMapping("/applet/queryClassByPhoneAndIdNum")
	public Map<String, Object> queryByPhoneAndIdNum(String phone,Integer idNum,Integer schoolId){
		return service.queryClassByPhone(phone, idNum,schoolId);
	}
	
	@GetMapping("/applet/getAllClass")
	public Map<String, Object> getAllClassx(Integer schoolId){
		return service.getAllClass(schoolId);
	}
	
	@GetMapping("/getAllClass")
	public Map<String, Object> getAllClass(Integer schoolId){
		return service.getAllClass(schoolId);
	}
	
	@GetMapping("/limitClass")
	public Map<String, Object> limitClass(Integer page, Integer limit,Integer schoolId,String className){
		
		if (StringUtils.isEmpty(className)) {
			return service.limitClass(page, limit, schoolId);
		}else {
			return service.queryClassByName(className, schoolId);
		}
	}
	
	@GetMapping("deleteClass")
	public Map<String, Object> deleteClass(String classIds){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(classIds)) {
			map.put("code", 1);
			map.put("msg", "classId不能为空");
			return map;
		}
		String[] ids = classIds.split(",");
		
		if (ids.length == 1) {
			Integer classId = Integer.valueOf(classIds);
			map = service.deleteclassId(classId);
		}else if (ids.length > 1) {
			map = service.deleteManyClass(ids);
		}
		return map;
	}
	
	@GetMapping("/queryClassByStudentId")
	public Map<String, Object> queryClassByStudentId(Integer studentId,Integer schoolId) {
		return service.queryClassByStudentId(studentId, schoolId);
	}
	
	@GetMapping("/applet/queryClassByStudentId")
	public Map<String, Object> wxQueryClassByStudentId(Integer studentId,Integer schoolId) {
		return service.wxQueryClassByStudentId(studentId, schoolId);
	}
	
	@GetMapping("/applet/querySumUnfinished")
	public Map<String, Object> querySumUnfinished(Integer studentId,Integer schoolId) {
		return service.querySumUnfinished(studentId, schoolId);
	}
	
	@GetMapping("/applet/queryMyClassByTeacherId")
	public Map<String, Object> queryByTeacherId(Integer teacherId) {
		return service.queryByTeacherId(teacherId);
	}
}
