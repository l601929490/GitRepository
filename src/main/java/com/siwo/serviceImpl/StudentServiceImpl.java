package com.siwo.serviceImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.ScoreSumDao;
import com.siwo.dao.StudentDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siwo.commons.StudentUtil;
import com.siwo.dao.ClientDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.model.Guardian;
import com.siwo.model.MyClass;
import com.siwo.model.ScoreSum;
import com.siwo.model.Student;
import com.siwo.service.GuardianService;
import com.siwo.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao dao;
	@Autowired
	private ClientDao userDao;
	@Autowired
	private ScoreSumDao sumDao;
	@Autowired
	private MyClassDao classDao;
	@Autowired
	private GuardianService guarService;
	@Autowired
	private GuardianDao guarDao;

	@Override
	public Map<String, Object> addStudent(Student student, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		Student student2 = null;
		if (!StringUtils.isEmpty(student.getStudentPhone())) {
			student2 = dao.getStudentByPhoneAndSchoolId(student.getStudentPhone(), student.getSchoolId());
		}
		if (student2 == null) {
			student.setStudentCreationTime(new Date());
			student.setCount(0);

			Integer row = dao.addStudent(student);
			if (row > 0) {
				List<Guardian> guardians = student.getGuardians();
				if (guardians != null) {
					for (Guardian guardian : guardians) {
						guardian.setGuardianCreator(student.getStudentCreator());
						guardian.setGuardianCreationTime(new Date());
						guardian.setSchoolId(student.getSchoolId());
						Integer guardianId = guarService.addGuardian_2(guardian);
						if (guardianId != null) {
							guarDao.addGuardianStudent(guardianId, student.getStudentId(), guardian.getRelationship());
						}
					}
				}
				map.put("code", 0);
				map.put("msg", "添加成功");
			} else {
				map.put("code", 1);
				map.put("msg", "添加失败");
			}
		} else {
			map.put("code", 1);
			map.put("msg", "该学生已经存在");
		}

		return map;
	}

	@Override
	public Map<String, Object> getAllStudent(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Student> students = dao.getStudentBySchoolId(schoolId);
		if (students != null) {
			map.put("code", 0);
			map.put("data", students);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryAllClassStudentByClassId(Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<Student> students = dao.queryAllClassStudentByClassId(classId);
		for (Student student : students) {
			List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
			student.setGuardians(guardians);
			StudentUtil.queryStudentState(student);
		}
		if (students != null) {
			map.put("code", 0);
			map.put("data", students);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> limitStudent(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		PageHelper.startPage(page, limit);
		List<Student> students = dao.limitStudent(schoolId);
		
		PageInfo<Student> studentInfo = new PageInfo<Student>(students);
		List<Student> students2 = studentInfo.getList();
		
		for (Student student : students2) {
			
			//	积分
			ScoreSum scoreSum = sumDao.queryScoreSumBystudentId(student.getStudentId());
			if (scoreSum == null) {
				student.setIntegral(0);
			}else {
				student.setIntegral(scoreSum.getRemain());
			}
			
			//	监护人电话
			List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
			student.setGuardians(guardians);
			
			List<MyClass> myClasses = classDao.queryMyClassByStudentId(student.getStudentId(),schoolId);
			student.setMyClasses(myClasses);
			//	状态
			Integer state = StudentUtil.queryStudentState(student);
			student.setState(state);
		}
		
		Collections.sort(students, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				
				Integer state = StudentUtil.queryStudentState(o1);
				Integer state_2 = StudentUtil.queryStudentState(o2);
				if (state != state_2) {
					return state - state_2;
				}
				
				return (int) (o2.getStudentCreationTime().getTime() - o1.getStudentCreationTime().getTime());
			}
		});

		if (students2 != null) {
			map.put("code", 0);
			map.put("data", students2);
			map.put("pageCount", studentInfo.getTotal());
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> update(Student student) {
		Map<String, Object> map = new HashMap<String, Object>();

		student.setStudentEditSession(new Date());

		Integer row = dao.update(student);
		List<Guardian> guardians = student.getGuardians();
		for (Guardian guardian : guardians) {
			if (guardian.getGuardianId() == null) {
				guarService.addGuardian_2(guardian);
			}else {
				guarDao.updateGuardian(guardian);
			}
			
		}
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "更新成功");
		} else {
			map.put("code", 1);
			map.put("msg", "更新失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteStudent(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Student student = dao.queryById(studentId);

		if (student != null) {
			userDao.deleteUserByPhone(student.getStudentPhone());

			Integer row = dao.delete(studentId);
			dao.deleteClass(studentId);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			} else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}

		return map;
	}

	@Override
	public Map<String, Object> queryStudentByName(String studentName, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Student> students = dao.queryStudentByName(studentName, schoolId);
		for (Student student : students) {
			List<MyClass> myClasses = classDao.queryMyClassByStudentId(student.getStudentId(),schoolId);
			student.setMyClasses(myClasses);
			//	状态
			Integer state = StudentUtil.queryStudentState(student);
			student.setState(state);
		}
		if (students != null) {
			map.put("code", 0);
			map.put("data", students);
		} else {
			map.put("code", 1);
			map.put("data", "查询失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteManyStudent(String[] studentIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (studentIds != null) {

			for (String id : studentIds) {
				Student student = dao.queryById(Integer.valueOf(id));

				List<Integer> classIds = dao.queryStudentBindingClass(Integer.valueOf(id));
				if (classIds != null && classIds.size() != 0) {
					map.put("code", 1);
					map.put("msg", "请先解除"+student.getStudentName()+"绑定的班级");
					return map;
				}
				guarDao.deleteStudentGuardian(Integer.valueOf(id));
			}
			
			Integer row = dao.deleteManyStudent(studentIds);
			dao.deleteManyStudentClass(studentIds);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			} else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		} else {
			map.put("code", 1);
			map.put("msg", "学生id不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryStudentByPhoneAndSchoolId(String phone, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		phone = "%"+phone+"%";
		List<Student> students = dao.queryStudentByPhoneAndSchoolId(phone, schoolId);
		
		for (Student student : students) {
			List<MyClass> myClasses = classDao.queryMyClassByStudentId(student.getStudentId(),schoolId);
			student.setMyClasses(myClasses);
			//	状态
			Integer state = StudentUtil.queryStudentState(student);
			student.setState(state);
		}
		
		if (students != null) {
			map.put("code", 0);
			map.put("data", students);
		}

		
		return map;
	}

	@Override
	public Map<String, Object> addStudentClass(String[] studentClassIds, String[] studentIds,Integer oneToMany) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (studentClassIds == null) {
			for (String string : studentIds) {
				dao.deleteClass(Integer.valueOf(string));
			}
			map.put("code", 0);
			map.put("msg", "操作成功");
			return map;
		}

		if (studentIds == null) {
			for (String string : studentClassIds) {
				dao.deleteStudent(Integer.valueOf(string));
			}
			map.put("code", 0);
			map.put("msg", "操作成功");
			return map;
		}

		if (studentIds.length > 1) {
			dao.deleteStudent(Integer.valueOf(studentClassIds[0]));
			for (String id : studentIds) {
				Integer row = dao.addStudentClass(Integer.valueOf(studentClassIds[0]), Integer.valueOf(id));
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				} else {
					map.put("code", 1);
					map.put("msg", "绑定失败");
				}

			}

		} 
		if (studentClassIds.length >1)  {
			dao.deleteClass(Integer.valueOf(studentIds[0]));
			for (String id_2 : studentClassIds) {

				Integer row = dao.addStudentClass(Integer.valueOf(id_2), Integer.valueOf(studentIds[0]));
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				} else {
					map.put("code", 1);
					map.put("msg", "绑定失败");
				}

			}
		}
		
		if (studentClassIds.length == 1 && studentIds.length == 1) {
			if (oneToMany == 1) {
				//	1 学生绑定班级
				dao.deleteClass(Integer.valueOf(studentIds[0]));
			}else {
				//	2 班级绑定学生
				dao.deleteStudent(Integer.valueOf(studentClassIds[0]));
			}
			Integer row = dao.addStudentClass(Integer.valueOf(Integer.valueOf(studentClassIds[0])), Integer.valueOf(studentIds[0]));
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
	public Map<String, Object> queryStudentByClassId(Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classId == null) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}

		List<Student> students = dao.queryStudentByClassId(classId);

		map.put("code", 0);
		map.put("data", students);

		return map;
	}

	@Override
	public Map<String, Object> deleteStudentAndClass(Integer classId, Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classId == null || studentId == null) {
			map.put("code", 1);
			map.put("msg", "解绑失败");
			return map;
		}
		Integer row = dao.deleteStudentAndClass(classId, studentId);
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
	public Map<String, Object> queryStudentByStatus(Integer schoolId, Integer status) {
		Map<String, Object>map = new HashMap<String, Object>();
		
		//	status   1未分配       2已分配
		
		if(schoolId == null) {
			map.put("code", 1);
			map.put("msg", "schoolid不能为空");
			return map;
		}
		if (status == null) {
			map.put("code", 1);
			map.put("msg", "status不能为空");
			return map;
		}
		
		if (status == 1) {
			List<Student> students = dao.queryAllocatedStudent(schoolId);
			map.put("code", 0);
			map.put("data", students);
		}else if (status == 2) {
			List<Student> students = dao.queryUndistributedStudent(schoolId);
			map.put("code", 0);
			map.put("data", students);
		}
		return map;
	}

}
