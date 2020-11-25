package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.ClientDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.UserIntegralDao;
import com.siwo.model.Guardian;
import com.siwo.model.MyClass;
import com.siwo.model.School;
import com.siwo.model.Student;
import com.siwo.model.StudentAndGuardian;
import com.siwo.service.GuardianService;

@Service
public class GuardianServiceImpl implements GuardianService {

	@Autowired
	private GuardianDao dao;

	@Autowired
	private StudentDao stuDao;

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private UserIntegralDao alDao;

	@Autowired
	private ClientDao userDao;

	@Autowired
	private MyClassDao classDao;

	@Override
	public Map<String, Object> addGuardian(Guardian guardian, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}

		Guardian guardian2 = dao.getGuardianByPhone(guardian.getGuardianPhone(), guardian.getSchoolId());

		if (guardian2 == null) {
			Integer row = dao.addGuardian(guardian);

			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "添加成功");
			} else {
				map.put("code", 1);
				map.put("msg", "添加失败");
			}

		} else {
			map.put("code", 1);
			map.put("msg", "该用户已存在");
		}

		return map;
	}

	@Override
	public Map<String, Object> queryGuardianByPhone(String guardianPhone, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Guardian guardian = dao.getGuardianByPhone(guardianPhone, schoolId);
		map.put("code", 0);
		map.put("data", guardian);
		return map;
	}

	@Override
	public Map<String, Object> getAllGuardian(Integer guardianSchoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Guardian> guardians = dao.getAllGuardian(guardianSchoolId);
		if (guardians != null) {
			map.put("code", 0);
			map.put("data", guardians);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> getGuardianCount(Integer guardianSchoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = dao.getGuardianCount(guardianSchoolId);
		map.put("code", 0);
		map.put("data", count);
		return map;
	}

	@Override
	public Map<String, Object> getGuardianStudent(Integer guardianId, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Student> students = stuDao.getGuardianStudent(guardianId);

		List<Student> students2 = new ArrayList<Student>();

		for (Student student : students) {

			if (student != null) {

				School school = schoolDao.querySchoolById(student.getSchoolId());
				List<MyClass> classes = classDao.queryClassByStudentId(student.getStudentId(), school.getSchoolId());
				Integer num = alDao.queryUserIntegralSum(student.getStudentPhone());
				Integer num2 = alDao.queryUserIntegralSum(phone);

				if (num == null) {
					num = 0;
				}
				if (num2 == null) {
					num2 = 0;
				}
				student.setIntegral(num + num2);

				student.setSchool(school);
//				for (MyClass myClass : classes) {
//					student.setMyClass(myClass);
//				}
				student.setMyClasses(classes);
				students2.add(student);
			}
		}

		if (students != null && students.size() != 0) {
//			students2.remove(0);
			map.put("code", 0);
			map.put("data", students2);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> limitGuardian(Integer page, Integer limit, Integer guardianSchoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer start = (page - 1) * limit;
		List<Guardian> guardians = dao.limitGuardian(start, limit, guardianSchoolId);
		Integer count = dao.getGuardianCount(guardianSchoolId);
		if (guardians != null) {
			map.put("code", 0);
			map.put("data", guardians);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteGuardianById(Integer guardianId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Guardian guardian = dao.queryGuardianById(guardianId);
		if (guardian != null) {
			userDao.deleteUserByPhone(guardian.getGuardianPhone());
		}

		Integer row = dao.deleteGuardianById(guardianId);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		} else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteGuardianMany(String[] guardianIds) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (String id : guardianIds) {
			Guardian guardian = dao.queryGuardianById(Integer.valueOf(id));
			List<Student> students = dao.queryStudentByGuardianId(Integer.valueOf(id));
			if (students != null && students.size() != 0) {
				map.put("code", 1);
				map.put("msg", "请先解除" + guardian.getGuardianName() + "绑定的学生信息");
				return map;
			}
		}

		Integer row = dao.deleteGuardianMany(guardianIds);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		} else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateGuardian(Guardian guardian) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer row = dao.updateGuardian(guardian);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "修改成功");
		} else {
			map.put("code", 1);
			map.put("msg", "修改失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> addGuardianStudent(String[] guardianId, String[] studentIds, String relationship,
			Integer oneToMany) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (guardianId == null) {
			dao.deleteStudentGuardian(Integer.valueOf(studentIds[0]));
			map.put("code", 0);
			map.put("msg", "操作成功");
			return map;
		}
		if (studentIds == null) {
			dao.deleteGuardianStudent(Integer.valueOf(guardianId[0]));
			map.put("code", 0);
			map.put("msg", "操作成功");
			return map;
		}

		if (guardianId.length > 1) {
			dao.deleteStudentGuardian(Integer.valueOf(studentIds[0]));
			for (String id : guardianId) {
				Integer row = dao.addGuardianStudent(Integer.valueOf(id), Integer.valueOf(studentIds[0]), null);
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				} else {
					map.put("code", 1);
					map.put("msg", "绑定失败");
				}
			}

		}
		if (studentIds.length > 1) {
			dao.deleteGuardianStudent(Integer.valueOf(guardianId[0]));
			for (String id_2 : studentIds) {
				Integer row = dao.addGuardianStudent(Integer.valueOf(guardianId[0]), Integer.valueOf(id_2), null);
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				} else {
					map.put("code", 1);
					map.put("msg", "绑定失败");
				}
			}
		}

		if (studentIds.length == 1 && guardianId.length == 1) {
			// 给学生加监护人
			if (oneToMany == 1) {
				dao.deleteStudentGuardian(Integer.valueOf(studentIds[0]));
			} else {
				dao.deleteGuardianStudent(Integer.valueOf(guardianId[0]));
			}
			Integer row = dao.addGuardianStudent(Integer.valueOf(guardianId[0]), Integer.valueOf(studentIds[0]), null);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "绑定成功");
			} else {
				map.put("code", 1);
				map.put("msg", "绑定失败");
			}
		}

		return map;
	}

	@Override
	public Map<String, Object> queryGuardianByStudentId(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (studentId == null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
			return map;
		}

		List<Guardian> guardians = dao.queryGuardianByStudentId(studentId);

		map.put("code", 0);
		map.put("data", guardians);
		return map;
	}

	@Override
	public Map<String, Object> deleteGuardianAndStudent(Integer guardianId, Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (guardianId == null || studentId == null) {
			map.put("code", 1);
			map.put("msg", "解绑失败");
			return map;
		}
		Integer row = dao.deleteGuardianAndStudent(guardianId, studentId);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "解绑成功");
		} else {
			map.put("code", 1);
			map.put("msg", "解绑失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryStudentByGuardianId(Integer guardianId) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (guardianId == null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
			return map;
		}

		List<Student> guardians = dao.queryStudentByGuardianId(guardianId);

		map.put("code", 0);
		map.put("data", guardians);
		return map;
	}

	@Override
	public Map<String, Object> queryGuardianByName(String guardianName, Integer schoolId) {

		Map<String, Object> map = new HashMap<String, Object>();

		guardianName = "%" + guardianName + "%";

		List<Guardian> guardians = dao.queryGuardianByName(guardianName, schoolId);

		if (guardians != null) {
			map.put("code", 0);
			map.put("data", guardians);
		}
		return map;
	}

	@Override
	public Map<String, Object> queryGuardianAndStudentByGuardianId(Integer guardianId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {

			Guardian guardian = dao.queryGuardianById(guardianId);
			List<Student> students = dao.queryStudentByGuardianId(guardianId);
			if (guardian != null) {
				guardian.setStudents(students);
			}
			map.put("code", 0);
			map.put("data", guardian);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败，服务器异常");
			return map;
		}

		return map;
	}

	@Override
	public Map<String, Object> bingdingStudentAndGuardian(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 全校学生
			List<Student> students = stuDao.getStudentBySchoolId(schoolId);
			// 全校监护人
			List<Guardian> guardians = dao.getAllGuardian(schoolId);

			int temp = 0;
			while (true) {

				if (temp >= guardians.size()) {
					break;
				}

				Guardian guardian = guardians.get(temp);

				for (Student student : students) {
					List<StudentAndGuardian> studentAndGuardians = stuDao
							.queryStudentAndGuardian(student.getStudentId());

					for (StudentAndGuardian studentAndGuardian : studentAndGuardians) {
						Integer count = dao.queryGuardianAndStudent(student.getStudentId(), guardian.getGuardianId());
						if (count == 0) {

							if (guardian.getGuardianPhone() != null && studentAndGuardian.getGuardianPhone() != null
									&& guardian.getGuardianPhone().equals(studentAndGuardian.getGuardianPhone())) {
								dao.addGuardianStudent(guardian.getGuardianId(), student.getStudentId(), null);
							}

						}

					}
				}

				temp++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "绑定失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "绑定成功");
		return map;
	}

	@Override
	public Integer addGuardian_2(Guardian guardian) {
		Guardian guardian2 = dao.getGuardianByPhone(guardian.getGuardianPhone(), guardian.getSchoolId());

		if (guardian2 == null) {
			Integer row = dao.addGuardian(guardian);

			if (row > 0) {
				return guardian.getGuardianId();
			} 

		}
		return guardian2.getGuardianId();
	}

}
