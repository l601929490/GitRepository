package com.siwo.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ClientDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.Guardian;
import com.siwo.model.MyClass;
import com.siwo.model.School;
import com.siwo.model.Student;
import com.siwo.model.Teacher;

@Service
public class SchoolUntil {

	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private StudentDao stuDao;
	@Autowired
	private TeacherDao teaDao;
	@Autowired
	private GuardianDao guDao;
	@Autowired
	private MyClassDao classDao;
	@Autowired
	private ClientDao clientDao;

	public List<Map<String, Object>> selectId(Integer schoolId, String phone,String openId) {

		Student student = stuDao.queryStudentByPhone(phone, schoolId);

		Teacher teacher = teaDao.queryTeacherByPhone(phone, schoolId);
			
		Guardian guardian = guDao.getGuardianByPhone(phone, schoolId);

		//	身份详细信息
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		//	身份id
		List<Integer> identitys = new ArrayList<Integer>();
		if (teacher != null) {
			
			Map<String, Object> teacherMap = new HashMap<String, Object>();
			teacherMap.put("idNum", 2);
			teacherMap.put("identity", "2");
			teacherMap.put("data", teacher);
			teacherMap.put("phone", phone);
			
			if (teacher.getStatus().equals("1")) {
				identitys.add(2);
				list.add(teacherMap);
			}else if (teacher.getStatus().equals("2")) {
				teacher = null;
			}

		}
		if (guardian != null) {

			Map<String, Object> guardianMap = new HashMap<String, Object>();
			guardianMap.put("idNum", 3);
			guardianMap.put("identity", "3");
			guardianMap.put("data", guardian);
			guardianMap.put("phone", phone);
			
			List<Student> students = guardian.getStudents();
			if (students!=null && students.size() != 0) {
				for (Student student2 : students) {
					List<MyClass> myClasses = classDao.queryClassByStudentId(student2.getStudentId(),schoolId);
					student2.setMyClasses(myClasses);
				}
			}
			
			identitys.add(3);
			list.add(guardianMap);
		}

		if (student != null) {

			Map<String, Object> studentMap = new HashMap<String, Object>();
			studentMap.put("idNum", 1);
			studentMap.put("identity", "1");
			studentMap.put("data", student);
			studentMap.put("phone", phone);
			
			identitys.add(1);
			list.add(studentMap);

		}

		if (student == null && teacher == null && guardian == null) {
			Map<String, Object> touristMap = new HashMap<String, Object>();
			touristMap.put("code", "0");
			touristMap.put("idNum", 4);
			touristMap.put("data", "游客用户");
			touristMap.put("phone", phone);
			
			identitys.add(4);
			list.add(touristMap);
		}
		if (!StringUtils.isEmpty(openId)) {
			clientDao.deleteUserIdentity(openId);
			for (Integer integer : identitys) {
				clientDao.addUserIdentity(openId, integer);
			}
		}
		return list;
	}
	
	public List<School> selectSchool(Integer companyId, String phone){
		List<Student> students = stuDao.queryStudentsByPhone(phone);

		List<Teacher> teachers = teaDao.queryTeachersByPhone(phone);
			
		List<Guardian> guardians = guDao.queryGuardiansByPhone(phone);
		
		List<School> list = new ArrayList<School>();
		for(Student student : students) {
			School school = schoolDao.querySchoolById(student.getSchoolId());
			if (school.getCompanyId() == companyId) {
				list.add(school);
			}
		}
		for(Teacher teacher : teachers) {
			School school = schoolDao.querySchoolById(teacher.getSchoolId());
			if (school.getCompanyId() == companyId) {
				list.add(school);
			}
		}
		for(Guardian guardian : guardians) {
			School school = schoolDao.querySchoolById(guardian.getSchoolId());
			if (school.getCompanyId() == companyId) {
				list.add(school);
			}
		}
		
		return list;
	}
}
