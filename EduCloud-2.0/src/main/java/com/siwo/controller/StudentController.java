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

import com.siwo.model.Student;
import com.siwo.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService service;

	/**
	 * 添加学生
	 * 
	 * @param student
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/addStudent")
	public Map<String, Object> addStudent(@RequestBody Student student, BindingResult result) {
		return service.addStudent(student, result);
	}

	/**
	 * 获取所有学生
	 * 
	 * @return
	 */
	@GetMapping("/getAllStudent")
	public Map<String, Object> getAllStudent(Integer schoolId) {
		return service.getAllStudent(schoolId);
	}

	/**
	 * 按照班级id获取全班学生信息
	 * 
	 * @param classId
	 * @return
	 */
	@GetMapping("/getStudentByClassId")
	public Map<String, Object> queryAllClassStudentByClassId(Integer classId) {
		return service.queryAllClassStudentByClassId(classId);
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/limitStudent")
	public Map<String, Object> limitStudent(Integer page, Integer limit, String studentName, Integer schoolId,
			Integer studentClassId) {

		Map<String, Object> map = null;
		if (!StringUtils.isEmpty(studentName)) {
			try {
				Long.parseLong(studentName);
				map = service.queryStudentByPhoneAndSchoolId(studentName, schoolId);
			} catch (NumberFormatException e) {
				map = service.queryStudentByName(studentName, schoolId);
			}
		} else if (studentClassId != null) {
			map = queryAllClassStudentByClassId(studentClassId);
		} else {
			if (page != null && limit != null) {

				map = service.limitStudent(page, limit, schoolId);

			}
		}
		return map;
	}

	/**
	 * 更新学生信息
	 * 
	 * @param student
	 * @return
	 */
	@PostMapping("/updateStudent")
	public Map<String, Object> update(@Valid @RequestBody Student student, BindingResult result) {
		return service.update(student);
	}

	/**
	 * 按照ID删除学生
	 * 
	 * @param studentId
	 * @return
	 */
	@GetMapping("/deleteStudent")
	public Map<String, Object> deleteStudent(String studentIds) {
		String[] str = studentIds.split(",");
		return service.deleteManyStudent(str);
	}

	/**
	 * 按照姓名查学生
	 * 
	 * @param studentName
	 * @return
	 */
	@GetMapping("/queryStudentByName")
	public Map<String, Object> queryStudentByName(String studentName, Integer schoolId) {
		return service.queryStudentByName(studentName, schoolId);
	}

	/**
	 * 绑定学生班级
	 * 
	 * @param studentClassId
	 * @param studentId
	 * @return
	 */
	@PostMapping("/addStudentClass")
	public Map<String, Object> addStudentClass(@RequestBody Map<String, Object> map) {

		Map<String, Object> result = new HashMap<String, Object>();

		String classIds = (String) map.get("classIds");
		String studentIds = (String) map.get("studentIds");
		Integer oneToMany = (Integer) map.get("oneToMany");
		String[] ids = null;
		String[] ids_2 = null;

		if (StringUtils.isEmpty(studentIds) && StringUtils.isEmpty(classIds)) {
			result.put("code", 0);
			result.put("msg", "操作成功");
			return result;
		} else {
			if (studentIds != null) {
				ids = studentIds.split(",");
			}

			if (classIds != null) {
				ids_2 = classIds.split(",");
			}
		}

		return service.addStudentClass(ids_2, ids, oneToMany);
	}

	/**
	 * 查找班级所有学生
	 * 
	 * @param classId
	 * @return
	 */
	@GetMapping("/queryStudentByClassId")
	public Map<String, Object> queryStudentByClassId(Integer classId, Integer schoolId) {
		return service.queryStudentByClassId(classId);
	}

	/**
	 * 解除学生和班级绑定的信息
	 * 
	 * @param classId
	 * @param studentId
	 * @return
	 */
	@GetMapping("/deleteStudentAndClass")
	public Map<String, Object> deleteStudentAndClass(Integer classId, Integer studentId) {
		return service.deleteStudentAndClass(classId, studentId);
	}

	@GetMapping("/queryStudentByStatus")
	public Map<String, Object> queryStudentByStatus(Integer schoolId, Integer status) {
		return service.queryStudentByStatus(schoolId, status);
	}
}
