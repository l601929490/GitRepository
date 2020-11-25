package com.siwo.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siwo.commons.TaskUntil;
import com.siwo.dao.AttendanceDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TaskDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.Attendance;
import com.siwo.model.MyClass;
import com.siwo.model.Task;
import com.siwo.model.TaskComment;
import com.siwo.model.TaskRecording;
import com.siwo.model.Teacher;
import com.siwo.service.AttendanceService;
import com.siwo.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao dao;

	@Autowired
	private StudentDao stuDao;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private AttendanceDao attDao;
	
	@Autowired
	private MyClassDao classDao;
	
	@Autowired
	private AttendanceService attService;

	@Autowired
	private TaskUntil until;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public Map<String, Object> addTask(Task task, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}

		Teacher teacher = teacherDao.queryTeacherById(task.getTeacherId());
		// 2离职
		if (teacher == null || teacher.getStatus().equals("2")) {
			map.put("code", 1);
			map.put("msg", "该老师已离职");
			return map;
		}

		Integer row = until.addTask(task, 1);

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
	public Map<String, Object> getTaskByTaskId(Integer taskId, String phone, Integer idNum, Integer schoolId,
			Integer studentId,Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (taskId == null) {
			map.put("code", 1);
			map.put("msg", "taskId不能为空");
			return map;
		}

		Task task = dao.queryTaskByTaskId(taskId);

		until.queryTaskFile(task);

		MyClass myClass = classDao.queryClassById(classId);
		task.setMyClass(myClass);;
		
		Attendance attendance = attDao.queryAttendanceByStudentIdAndTaskId(task.getTaskId(), studentId, classId);

		if (attendance == null) {
			Integer row = task.getTaskStopTime().compareTo(new Date());
			if (row > 0 || task.getAllowJob() == 1) {
				task.setPastDue(0);
			} else if (row <= 0 && task.getAllowJob() == 0) {
				task.setPastDue(1);
			}
		} else {
			task.setPastDue(0);
		}
		
		map.put("code", 0);
		map.put("data", task);
		return map;
	}

	public void queryLongTimeTask(Task task,Integer classId,Integer studentId,Integer sum){
		// 判断打卡时间是否过了
		Date stop = task.getTaskStopTime();

		Date date = new Date();
		if (stop.getTime() < date.getTime()) {
			task.setTiming(2);
		}

		// 已打卡人数
		String now = format.format(new Date());
		String startTime = now + " 00:00:00";
		String endTime = now + " 23:59:59";
		Integer taskNum = attDao.queryAttendanceByTime(startTime, endTime, classId, task.getTaskId()).size();
		task.setOkNum(taskNum);

		// 未打卡人数
		Integer notNum = sum - task.getOkNum();
		task.setNotNum(notNum);

		until.queryTaskFile(task);
		task.setClassId(classId);

		Attendance attendance = attDao.queryAttendanceByTimeAndStudentId(startTime, endTime, studentId, task.getTaskId(),classId);
		
		if (attendance == null) {
			Integer row = task.getTaskStopTime().compareTo(new Date());
			if (row > 0 || task.getAllowJob() == 1) {
				task.setPastDue(0);
			} else if (row <= 0 && task.getAllowJob() == 0) {
				task.setPastDue(1);
			}
		} else {
			task.setPastDue(0);
		}
		task.setAttendance(attendance);
	}
	
	@Override
	public Map<String, Object> queryTaskByClassId(Integer classId, Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 获取班级所有任务
		List<Task> tasks = dao.queryTaskByClassId(classId);

		// 获取全班人数
		Integer sum = stuDao.queryAllClassStudentCountByClassId(classId);

		for (Task task : tasks) {
			if (task.getLongTermUse() == 0) {
				// 判断打卡时间是否过了
				Date stop = task.getTaskStopTime();

				Date date = new Date();
				if (stop.getTime() < date.getTime()) {
					task.setTiming(2);
				}

				// 已打卡人数
				Integer taskNum = dao.selectTaskOkNum(task.getTaskId(), classId);
				task.setOkNum(taskNum);

				// 未打卡人数
				Integer notNum = sum - task.getOkNum();
				task.setNotNum(notNum);

				until.queryTaskFile(task);
				task.setClassId(classId);

				Attendance attendance = attDao.queryAttendanceByStudentIdAndTaskId(task.getTaskId(), studentId, classId);

				if (attendance == null) {
					Integer row = task.getTaskStopTime().compareTo(new Date());
					if (row > 0 || task.getAllowJob() == 1) {
						task.setPastDue(0);
					} else if (row <= 0 && task.getAllowJob() == 0) {
						task.setPastDue(1);
					}
				} else {
					task.setPastDue(0);
				}

				task.setAttendance(attendance);
			}else if (task.getLongTermUse() == 1) {
				//	长期任务
				queryLongTimeTask(task,classId,studentId,sum);
			}
		}

		//	排序
		Collections.sort(tasks, new Comparator<Task>() {

			@Override
			public int compare(Task o1, Task o2) {

				if (o1.getPastDue() == o2.getPastDue()) {
					if (o1.getAttendance() == null && o2.getAttendance() != null) {
						return -1;
					} else if (o2.getAttendance() == null && o1.getAttendance() != null) {
						return 1;
					} else if (o1.getAttendance() == null && o2.getAttendance() == null) {
						if (o1.getTiming() != o2.getTiming()) {
							return o1.getTiming() - o2.getTiming();
						} else {
							Date date = o1.getCreateTime();
							Date date2 = o2.getCreateTime();
							return (int) (date2.getTime() - date.getTime());
						}
					}
				}
				return o1.getPastDue() - o2.getPastDue();

			}
		});
		map.put("code", 0);
		map.put("data", tasks);
		return map;
	}

	@Override
	public Map<String, Object> updateTask(Task task) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer row = dao.updateTask(task);
		Integer taskId = task.getTaskId();

		until.deleteTaskFile(taskId);

		if (row > 0) {
			until.addTask(task, 2);
		}

		map.put("code", 0);
		map.put("msg", "更新成功");

		return map;
	}

	@Override
	public Map<String, Object> addTaskComment(TaskComment taskComment) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.addTaskComment(taskComment);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "添加失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "添加成功");
		return map;
	}

	@Override
	public Map<String, Object> updateTaskComment(TaskComment taskComment) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.updateTaskComment(taskComment);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "更新失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "更新成功");
		return map;
	}

	@Override
	public Map<String, Object> queryTaskCommentById(Integer taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (taskId == null) {
			map.put("code", 1);
			map.put("msg", "taskid不能为空");
			return map;
		}

		TaskComment comment = dao.queryTaskCommentById(taskId);
		map.put("code", 0);
		map.put("data", comment);
		return map;
	}

	@Override
	public Map<String, Object> limitTask(Integer classId, Integer schoolId, Integer page, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer sum = stuDao.queryAllClassStudentCountByClassId(classId);
		
		if (classId == null && schoolId != null) {
			PageHelper.startPage(page, limit);
			List<Task> tasks = dao.limitTask(schoolId);
			for (Task task : tasks) {
				Teacher teacher = teacherDao.queryTeacherById(task.getTeacherId());
				task.setTeacher(teacher);
				until.queryTaskFile(task);
				List<MyClass> classes = dao.queryTaskClassByTaskId(task.getTaskId());
				task.setMyClasses(classes);
			}
			PageInfo<Task> info = new PageInfo<Task>(tasks);
			map.put("code", 0);
			map.put("data", tasks);
			map.put("pageCount", info.getTotal());
			return map;
		}

		List<Task> tasks = dao.queryTaskByClassId(classId);
		for (Task task : tasks) {
			Teacher teacher = teacherDao.queryTeacherById(task.getTeacherId());
			task.setTeacher(teacher);
			if (task.getLongTermUse() == 0) {
				// 已打卡人数
				Integer taskNum = dao.selectTaskOkNum(task.getTaskId(), classId);
				task.setOkNum(taskNum);
				// 未打卡人数
				Integer notNum = sum - task.getOkNum();
				task.setNotNum(notNum);
			}else if (task.getLongTermUse() == 1) {
				
				// 已打卡人数
				String now = format.format(new Date());
				String startTime = now + " 00:00:00";
				String endTime = now + " 23:59:59";
				Integer taskNum = attDao.queryAttendanceByTime(startTime, endTime, classId, task.getTaskId()).size();
				task.setOkNum(taskNum);

				// 未打卡人数
				Integer notNum = sum - task.getOkNum();
				task.setNotNum(notNum);

				until.queryTaskFile(task);
				task.setClassId(classId);
				
			}
			

			until.queryTaskFile(task);
		}
		map.put("code", 0);
		map.put("data", tasks);
		return map;
	}

	@Override
	public Map<String, Object> deleteManyTask(String[] ids, Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			System.out.println(classId);
			for (String string : ids) {
				Integer id = Integer.valueOf(string);
				if (classId == null) {
					List<Attendance> attendances = attDao.queryAttendanceByTaskId_2(id);
					for (Attendance attendance : attendances) {
						attService.deleteAttendanceById(attendance.getAttendanceId());
					}
					dao.deleteTaskBangdingClass(id);
					dao.deleteTask(id);
					until.deleteTaskFile(id);
				}else {
					until.deleteTask(id, classId);
					List<Attendance> attendances = attDao.queryAttendanceByTaskId(id, classId);
					for (Attendance attendance : attendances) {
						attService.deleteAttendanceById(attendance.getAttendanceId());
					}
				}
			}
			map.put("code", 0);
			map.put("msg", "删除成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "删除失败");
			return map;
		}

		return map;
	}

	@Override
	public Map<String, Object> deleteTaskByTaskId(Integer taskId, Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer row = until.deleteTask(taskId, classId);
		List<Attendance> attendances = attDao.queryAttendanceByTaskId(taskId, classId);
		for (Attendance attendance : attendances) {
			attService.deleteAttendanceById(attendance.getAttendanceId());
		}
		if (row == 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		} else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

}
