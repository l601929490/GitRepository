package com.siwo.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.commons.MyClassUtil;
import com.siwo.dao.AttendanceDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TaskDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.Attendance;
import com.siwo.model.Guardian;
import com.siwo.model.MyClass;
import com.siwo.model.Student;
import com.siwo.model.Task;
import com.siwo.model.Teacher;
import com.siwo.service.MyClassService;

@Service
public class MyClassServiceImpl implements MyClassService {

	@Autowired
	private MyClassDao dao;

	@Autowired
	private StudentDao stuDao;

	@Autowired
	private TeacherDao teaDao;

	@Autowired
	private GuardianDao guarDao;
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired 
	private AttendanceDao attDao;

	@Override
	public Map<String, Object> addClass(MyClass myClass, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}

		Date start = myClass.getPromotionTime();
		Date stop = myClass.getGraduationTime();
		Long timeDifference = TimeUnit.MILLISECONDS.toDays(stop.getTime() - start.getTime());
		if (timeDifference < 1) {
			map.put("code", 1);
			map.put("msg", "开班时间和结束时间相距不能少于1天");
			return map;
		}

		myClass.setClassCreateTime(new Date());

		Integer row = dao.addClass(myClass);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		} else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> queryById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		MyClass myClass = dao.queryById(id);
		map.put("code", 0);
		map.put("data", myClass);
		return map;
	}

	@Override
	public Map<String, Object> queryAllClassName() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> namas = dao.queryAllClassName();
		map.put("code", 0);
		map.put("data", namas);
		return map;
	}

	@Override
	public Map<String, Object> queryByPhoneAndIder(String phone, Integer idNum, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (idNum == 1) {
			Student student = stuDao.queryStudentByPhone(phone, schoolId);
			MyClass myClass = dao.queryById(student.getStudentClassId());
			map.put("code", 0);
			map.put("data", myClass);
		} else if (idNum == 2) {
			Teacher teacher = teaDao.queryTeacherByPhone(phone, schoolId);
			List<MyClass> myClass = dao.queryByTeacherId(teacher.getTeacherId());
			map.put("code", 0);
			map.put("data", myClass);
		} else if (idNum == 3) {
			Guardian guardian = guarDao.getGuardianByPhone(phone, schoolId);
			List<Integer> ids = guardian.getGuardianStudentIds();
			for (Integer id : ids) {
				Student student = stuDao.queryStudentById(id);
				MyClass myClass = dao.queryById(student.getStudentClassId());
				map.put("data", myClass);
			}

			map.put("code", 0);
		} else {
			map.put("code", 0);
			map.put("data", "游客用户");
		}
		return map;
	}

	@Override
	public Map<String, Object> getAllClass(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<MyClass> classes = dao.getAllMyClass(schoolId);

		Integer size = classes.size();
		List<MyClass> list = new ArrayList<MyClass>();
		for (int i = 0; i < size; i++) {

			Integer state = MyClassUtil.queryClassStatus(classes.get(i));
			
			if (state != 3) {
				list.add(classes.get(i));
			}

		}
		map.put("code", 0);
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> queryClassByPhone(String phone, Integer idNum, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (idNum == null) {
			map.put("code", 1);
			map.put("msg", "idNum不能为空");
			return map;
		} else if (phone == null) {
			map.put("code", 1);
			map.put("msg", "phone不能为空");
			return map;
		} else if (schoolId == null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
			return map;
		}

		try {
			if (idNum == 1) {

				Student student = stuDao.queryStudentByPhone(phone, schoolId);
				if (student != null) {
					map.put("code", 0);
					List<MyClass> classes = dao.queryClassByStudentId(student.getStudentId(), schoolId);
					map.put("data", classes);
				}
			} else if (idNum == 2) {
				Teacher teacher = teaDao.queryTeacherByPhone(phone, schoolId);
				if (teacher != null) {
					List<MyClass> myClasses = dao.queryByTeacherId(teacher.getTeacherId());
					map.put("code", 0);
					map.put("data", myClasses);
				}
			} else if (idNum == 3) {
				Guardian guardian = guarDao.getGuardianByPhone(phone, schoolId);

				List<MyClass> list = new ArrayList<MyClass>();
				List<Integer> ids = guardian.getGuardianStudentIds();
				for (Integer id : ids) {

					Student student = stuDao.queryByIdAndSchoolId(id, schoolId);
					if (student != null) {
						List<MyClass> myClass = dao.queryClassByStudentId(student.getStudentId(), schoolId);
						for (MyClass myClass2 : myClass) {
							myClass2.setStudent(student);
							list.add(myClass2);
						}
					}
				}
				map.put("code", 0);
				map.put("data", list);
			}
		} catch (NullPointerException e) {
			map.put("code", 0);
			map.put("msg", "暂无数据");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> limitClass(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer start = (page - 1) * limit;
		List<MyClass> myClasses = dao.limitClass(start, limit, schoolId);

		for (MyClass myClass : myClasses) {
			Integer state = MyClassUtil.queryClassStatus(myClass);
			myClass.setState(state);
			
		}

		Collections.sort(myClasses, new Comparator<MyClass>() {

			@Override
			public int compare(MyClass o1, MyClass o2) {
				Integer status = MyClassUtil.queryClassStatus(o1);
				Integer status_2 = MyClassUtil.queryClassStatus(o2);
				if (status == status_2) {
					if (o1.getPromotionTime().getTime() > o2.getPromotionTime().getTime()) {
						return -1;
					} else {
						return 0;
					}
				} else {
					return status - status_2;
				}
			}
		});

		Integer count = dao.getMyClassCount(schoolId);
		if (myClasses != null) {
			map.put("code", 0);
			map.put("data", myClasses);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteclassId(Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (classId == null) {
			map.put("code", 1);
			map.put("msg", "classId不能为空");
		} else {
			Integer row = dao.deleteclassId(classId);
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
	public Map<String, Object> deleteManyClass(String[] classIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String classId : classIds) {
			Integer id  = Integer.valueOf(classId);
			List<Teacher> teachers = teaDao.queryTeacherByClassId(id);
			if (teachers.size() != 0) {
				MyClass myClass = dao.queryClassById(id);
				map.put("code", 1);
				map.put("msg", "请先解除"+myClass.getClassName()+"绑定的老师");
				return map;
			}
			
			List<Student> students = stuDao.queryStudentByClassId(id);
			if (students.size() != 0) {
				MyClass myClass = dao.queryClassById(id);
				map.put("code", 1);
				map.put("msg", "请先解除"+myClass.getClassName()+"绑定的学生");
				return map;
			}
		}
		if (classIds != null) {
			Integer row = dao.deleteManyClass(classIds);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			} else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		} else {
			map.put("code", 1);
			map.put("msg", "posterId不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateClass(MyClass myClass) {
		Map<String, Object> map = new HashMap<String, Object>();
		myClass.setClassEditSession(new Date());
		Integer row = dao.updateClass(myClass);
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
	public Map<String, Object> queryClassByStudentId(Integer studentId, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (studentId == null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
			return map;
		}

		List<MyClass> myClasses = dao.queryClassByStudentId(studentId,schoolId);

		map.put("code", "0");
		map.put("data", myClasses);

		return map;
	}

	@Override
	public Map<String, Object> queryClassByName(String className, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		className = "%" + className + "%";

		List<MyClass> classes = dao.queryClassByName(className, schoolId);
		for (MyClass myClass : classes) {
			Integer state = MyClassUtil.queryClassStatus(myClass);
			myClass.setState(state);
		}
		if (classes != null) {
			map.put("code", 0);
			map.put("data", classes);
		}

		return map;
	}

	@Override
	public Map<String, Object> queryByTeacherId(Integer teacherId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (teacherId == null) {
			map.put("code", 1);
			map.put("msg", "id不能为空");
			return map;
		}
		
		List<MyClass> myClasses = dao.queryByTeacherId(teacherId);
		map.put("code", 0);
		map.put("data", myClasses);
		
		return map;
	}

	@Override
	public Map<String, Object> wxQueryClassByStudentId(Integer studentId, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (studentId == null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
			return map;
		}

		List<MyClass> myClasses = dao.queryMyClassByStudentId(studentId,schoolId);
		
		for (MyClass myClass : myClasses) {
			
			List<Task> tasks = new ArrayList<Task>();
			List<Task> tasks2 = taskDao.queryTaskByClassId(myClass.getClassId());
			for (Task task : tasks2) {
				int comparTo = task.getTaskStopTime().compareTo(new Date());
				if (comparTo == 1 || task.getAllowJob() == 1) {
					tasks.add(task);
				}
			}
			
			// 未完成作业数
			Integer count = 0;
			for (Task task : tasks) {
				if (task.getLongTermUse() == 0) {
					Attendance attendance = attDao.queryAttendanceByStudentIdAndTaskIdAndClassId(task.getTaskId(), studentId, myClass.getClassId());
					if (attendance == null) {
						count++;
					}
				}else if (task.getLongTermUse() == 1) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String now = format.format(new Date());
					String startTime = now + " 00:00:00";
					String endTime = now + " 23:59:59";
					Attendance attendance = attDao.queryAttendanceByTimeAndStudentId(startTime, endTime, studentId,
							task.getTaskId(),myClass.getClassId());
					if (attendance == null) {
						count++;
					}
				}
			}
			myClass.setUnfinished(count);
		}

		map.put("code", "0");
		map.put("data", myClasses);

		return map;
	}

	@Override
	public Map<String, Object> querySumUnfinished(Integer studentId,Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (studentId == null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
			return map;
		}

		try {
			List<MyClass> myClasses = dao.queryMyClassByStudentId(studentId,schoolId);
			Integer sumUnfinished = 0;
			for (MyClass myClass : myClasses) {
				
				List<Task> tasks = new ArrayList<Task>();
				List<Task> tasks2 = taskDao.queryTaskByClassId(myClass.getClassId());
				for (Task task : tasks2) {
					int comparTo = task.getTaskStopTime().compareTo(new Date());
					if (comparTo == 1 || task.getAllowJob() == 1) {
						tasks.add(task);
					}
				}
				
				// 未完成作业数
				Integer count = 0;
				
				for (Task task : tasks) {
					if (task.getLongTermUse() == 0) {
						Attendance attendance = attDao.queryAttendanceByStudentIdAndTaskIdAndClassId(task.getTaskId(), studentId, myClass.getClassId());
						if (attendance == null) {
							count++;
						}
					}else if (task.getLongTermUse() == 1) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String now = format.format(new Date());
						String startTime = now + " 00:00:00";
						String endTime = now + " 23:59:59";
						Attendance attendance = attDao.queryAttendanceByTimeAndStudentId(startTime, endTime, studentId,
								task.getTaskId(),myClass.getClassId());
						if (attendance == null) {
							count++;
						}
					}
				}
				sumUnfinished+=count;
			}
			map.put("code", 0);
			map.put("data", sumUnfinished);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "服务器异常");
			return map;
		}
		return map;
	}

}
