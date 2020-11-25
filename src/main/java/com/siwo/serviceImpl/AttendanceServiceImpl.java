package com.siwo.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.commons.AttendanceUntil;
import com.siwo.commons.TaskUntil;
import com.siwo.dao.AttendanceCommentDao;
import com.siwo.dao.AttendanceDao;
import com.siwo.dao.ClientDao;
import com.siwo.dao.CompanyDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TaskDao;
import com.siwo.dao.TeacherDao;
import com.siwo.dao.UserIntegralDao;
import com.siwo.model.Attendance;
import com.siwo.model.AttendanceComment;
import com.siwo.model.Client;
import com.siwo.model.Company;
import com.siwo.model.Guardian;
import com.siwo.model.MyClass;
import com.siwo.model.School;
import com.siwo.model.Student;
import com.siwo.model.Task;
import com.siwo.model.Teacher;
import com.siwo.service.AttendanceService;
import com.siwo.service.ScoreGetScoreService;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Resource
	private AttendanceDao attendanceDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private UserIntegralDao alDao;

	@Autowired
	private MyClassDao classDao;

	@Autowired
	private ClientDao userDao;

	@Autowired
	private StudentDao stuDao;

	@Autowired
	private GuardianDao guarDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private TeacherDao teaDao;

	@Autowired
	private AttendanceCommentDao commentDao;

	@Autowired
	private ScoreGetScoreService scoregetService;

	@Autowired
	private AttendanceUntil until;
	
	@Autowired
	private TaskUntil taskUntil;

	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public synchronized Map<String, Object> addAttendance(Attendance attendance, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		// 实际打卡学生id
		Integer studentId = attendance.getStudentId();
		// 作业id
		Integer taskId = attendance.getTaskId();

		Task task = taskDao.queryTaskByTaskId(taskId);

		// 短期任务判断是否打过卡
		if (task.getLongTermUse() == 0) {

			// 按实际学生id和任务id查询打卡记录，判断该学生是否打过卡
			Attendance attendance2 = attendanceDao.queryAttendanceByStudentIdAndTaskId(taskId, studentId,
					attendance.getClassId());
			if (attendance2 != null) {
				map.put("code", 1);
				map.put("msg", "已打过卡");
				return map;
			}

			// 判断该做是否可以补交，是否还能打卡
			if (task != null) {
				Date stop = task.getTaskStopTime();
				// 是否允许补交
				Integer allowJob = task.getAllowJob();
				if (allowJob == 0) {
					if (stop.getTime() < System.currentTimeMillis()) {
						map.put("code", 1);
						map.put("msg", "打卡已结束");
						return map;
					}
				} else {
					if (stop.getTime() < System.currentTimeMillis()) {
						attendance.setMakeUp(1);
					}
				}
			}
		}
		if (task.getLongTermUse() == 1) {

			// 任务是否已超过截止时间
			Date stop = task.getTaskStopTime();
			if (stop.getTime() < System.currentTimeMillis()) {
				map.put("code", 2);
				map.put("msg", "打卡已截止");
				return map;
			}

			String now = format2.format(new Date());
			String startTime = now + " 00:00:00";
			String endTime = now + " 23:59:59";
			Attendance timingTttendance = attendanceDao.queryAttendanceByTimeAndStudentId(startTime, endTime, studentId,
					taskId, attendance.getClassId());
			if (timingTttendance != null) {
				map.put("code", 2);
				map.put("msg", "今日已打卡");
				return map;
			}

		}

		// 添加作业
		Integer row = until.addAttendanceFile(attendance, 0);
		if (row > 0) {

			// 获取已打卡人数
			Integer okNum = taskDao.selectTaskOkNum(taskId, attendance.getClassId());
			okNum = okNum + 1;
			// 修改已打卡人数
			taskDao.updateokNum(okNum, taskId, attendance.getClassId());

			try {

				// 1补交 2 正常
				scoregetService.addStudentUserIntegral(attendance.getAttendanceId(), "y", attendance.getMakeUp());

			} catch (Exception e) {
				e.printStackTrace();
			}

			map.put("code", 0);
			map.put("msg", "打卡完成");
		} else {
			map.put("code", 1);
			map.put("msg", "打卡失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> updateAttendance(Attendance attendance) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer attendanceId = attendance.getAttendanceId();
		if (attendanceId == null) {
			map.put("code", 1);
			map.put("msg", "更新失败");
			return map;
		}
		attendanceDao.deleteAttendanceImg(attendanceId);
		attendanceDao.deleteAttendanceRecording(attendanceId);
		attendanceDao.deleteAttendanceVideo(attendanceId);

		Integer row_1 = attendanceDao.updateAttendance(attendance);
		if (row_1 > 0) {
			Integer row_2 = until.addAttendanceFile(attendance, 1);
			if (row_2 > 0) {
				attendanceDao.updateCorrect(attendanceId, 2);
				map.put("code", 0);
				map.put("msg", "更新成功");
			} else {
				map.put("code", 1);
				map.put("msg", "更新失败");
			}
		} else {
			map.put("code", 1);
			map.put("msg", "更新失败");
		}

		return map;
	}

	public void updateLikeName(Attendance attendance) {

		List<String> phones = attendanceDao.queryLikeAttendancePhoneByAttendanceId(attendance.getAttendanceId());

		List<String> names = new ArrayList<String>();

		Integer schoolId = classDao.querySchoolIdByClassId(attendance.getClassId());

		for (String phone : phones) {

			Student student = stuDao.getStudentByPhoneAndSchoolId(phone, schoolId);
			if (student != null) {
				names.add(student.getStudentName());
				continue;
			}

			Guardian guardian = guarDao.getGuardianByPhone(phone, schoolId);
			if (guardian != null) {
				names.add(guardian.getGuardianName());
				continue;
			}

			Teacher teacher = teaDao.getTeacherByPhoneAndSchoolId(phone, schoolId);
			if (teacher != null) {
				names.add(teacher.getTeacherName());
				continue;
			}
		}
		attendance.setLikeAttendanceName(names);

	}

	private Map<String, Object> queryLongTermUseAttendance(Integer taskId, Integer studentId, Integer idNum,
			Integer classId, Integer visible) {
		Map<String, Object> map = new HashMap<String, Object>();

		String now = format2.format(new Date());
		String startTime = now + " 00:00:00";
		String endTime = now + " 23:59:59";

		// 按可见性和身份取学生完成的作业-已提交的
		List<Attendance> submitted = until.queryAttendancesByVisible(visible, 4, taskId, idNum, classId);

		// 未提交学生信息
		List<Student> notSubmitted = stuDao.queryNotSubmittedStudentByClassIdAndLongTask(classId, taskId, startTime,
				endTime);
		for (Student student : notSubmitted) {
			List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
			if (guardians.size() > 0) {
				student.setStudentPhone(guardians.get(0).getGuardianPhone());
			}
		}

		for (int i = 0; i < submitted.size(); i++) {
			Attendance attendance = submitted.get(i);

			// 老师可以看见所有留言
			if (idNum == 2) {
				List<AttendanceComment> comments = commentDao.queryCommentsByAttendanceId(attendance.getAttendanceId());
				attendance.setComments(comments);
			}
			// 学生/家长只能看见公开留言
			if (idNum != 2) {
				List<AttendanceComment> comments = commentDao
						.queryCommentsByAttendanceIdAndIsSecret(attendance.getAttendanceId(), 0);
				attendance.setComments(comments);
			}

			// 学生自己的任务信息
			if (attendance.getStudentId() == studentId) {
				attendance.setComments(commentDao.queryCommentsByAttendanceId(attendance.getAttendanceId()));

				List<AttendanceComment> comments = commentDao
						.queryCommentsByAttendanceIdAndIsSecret(attendance.getAttendanceId(), 0);

				List<AttendanceComment> comments_2 = commentDao
						.queryCommentsByAttendanceIdAndIsSecret(attendance.getAttendanceId(), 1);
				if (comments_2 != null && comments_2.size() > 0) {
					comments.addAll(comments_2);
				}
				attendance.setComments(comments);

				until.queryAttendanceFile(attendance);
			}
			// 点赞人
			updateLikeName(attendance);

			// 查询作业文件
			until.queryAttendanceFile(attendance);

		}

		// 排序
		Collections.sort(submitted, new Comparator<Attendance>() {

			@Override
			public int compare(Attendance o1, Attendance o2) {
//				if (o1.getIsAffirm() != o2.getIsAffirm()) {
//					if (idNum == 2) {
//						return o1.getIsAffirm() - o2.getIsAffirm();
//					} else {
//						Date date = o1.getAttendanceTime();
//						Date date2 = o2.getAttendanceTime();
//						return (int) (date2.getTime() - date.getTime());
//					}
//				} else {
					Date date = o1.getAttendanceTime();
					Date date2 = o2.getAttendanceTime();
					return (int) (date2.getTime() - date.getTime());
//				}

			}
		});

		Integer okNum = attendanceDao.queryAttendanceByTime(startTime, endTime, classId, taskId).size();
		map.put("code", 0);
		map.put("submitted", submitted);
		map.put("notSubmitted", notSubmitted);
		map.put("okNum", okNum);
		return map;
	}

	@Override
	public Map<String, Object> queryAttendanceByTaskId(Integer taskId, Integer studentId, Integer idNum,
			Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 老师发布的课后作业
		Task task = taskDao.queryTaskByTaskId(taskId);

		// 此作业的可见性
		Integer visible = task.getVisible();

		if (task.getLongTermUse() == 1) {
			return queryLongTermUseAttendance(taskId, studentId, idNum, classId, visible);
		}

		// 当前学生的打卡记录
		Attendance studentAttendance = attendanceDao.queryAttendanceByStudentIdAndTaskId(taskId, studentId, classId);
		// 4还未提交
		Integer isAffirm = 4;
		if (studentAttendance != null) {
			studentAttendance.setComments(commentDao.queryCommentsByAttendanceId(studentAttendance.getAttendanceId()));
			isAffirm = studentAttendance.getIsAffirm();

			List<AttendanceComment> comments = commentDao
					.queryCommentsByAttendanceIdAndIsSecret(studentAttendance.getAttendanceId(), 0);

			List<AttendanceComment> comments_2 = commentDao
					.queryCommentsByAttendanceIdAndIsSecret(studentAttendance.getAttendanceId(), 1);
			if (comments_2 != null && comments_2.size() > 0) {
				comments.addAll(comments_2);
			}
			studentAttendance.setComments(comments);

			until.queryAttendanceFile(studentAttendance);
		}

		// 按可见性和身份取学生完成的作业-已提交的
		List<Attendance> submitted = until.queryAttendancesByVisible(visible, isAffirm, taskId, idNum, classId);
		// 未提交学生信息
		List<Student> notSubmitted = stuDao.queryNotSubmittedStudentByClassId(classId, taskId);

		for (int i = 0; i < submitted.size(); i++) {
			Attendance attendance = submitted.get(i);

			// 将自己的打卡记录置顶
			if (submitted.get(i).equals(studentAttendance)) {
				submitted.remove(i);
				i--;
				continue;
			}

			// 老师可以看见所有留言
			if (idNum == 2) {
				List<AttendanceComment> comments = commentDao.queryCommentsByAttendanceId(attendance.getAttendanceId());
				attendance.setComments(comments);
			}
			// 学生/家长只能看见公开留言
			if (idNum != 2) {
				List<AttendanceComment> comments = commentDao
						.queryCommentsByAttendanceIdAndIsSecret(attendance.getAttendanceId(), 0);
				attendance.setComments(comments);
			}

			// 点赞人
			updateLikeName(attendance);

			// 查询作业文件
			until.queryAttendanceFile(attendance);

		}

		// 排序
		Collections.sort(submitted, new Comparator<Attendance>() {

			@Override
			public int compare(Attendance o1, Attendance o2) {
				if (o1.getIsAffirm() != o2.getIsAffirm()) {
					if (idNum == 2) {
						return o1.getIsAffirm() - o2.getIsAffirm();
					} else {
						Date date = o1.getAttendanceTime();
						Date date2 = o2.getAttendanceTime();
						return (int) (date2.getTime() - date.getTime());
					}
				} else {
					Date date = o1.getAttendanceTime();
					Date date2 = o2.getAttendanceTime();
					return (int) (date2.getTime() - date.getTime());
				}

			}
		});

		if (submitted.size() > 0) {
			if (studentAttendance != null) {
				submitted.add(0, studentAttendance);
			}
		}

		if (submitted.size() == 0 && studentAttendance != null) {
			submitted.add(studentAttendance);
		}

		Integer okNum = taskDao.selectTaskOkNum(taskId, classId);
		map.put("code", 0);
		map.put("submitted", submitted);
		map.put("notSubmitted", notSubmitted);
		map.put("okNum", okNum);
		return map;
	}

	// 删除打卡
	@Override
	public Map<String, Object> deleteAttendanceById(Integer attendanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (attendanceId != null) {

			Attendance attendance = attendanceDao.queryById(attendanceId);
			if (attendance != null) {

				if (attendance.getIsAffirm() != 0) {
					map.put("code", 2);
					map.put("msg", "作业已通过无法删除");
					return map;
				}

				Integer okNum = taskDao.selectTaskOkNum(attendance.getTaskId(), attendance.getClassId());
				okNum = okNum - 1;
				// 修改已打卡人数
				taskDao.updateokNum(okNum, attendance.getTaskId(), attendance.getClassId());
			}

			// 删除积分
			try {
				scoregetService.addStudentUserIntegral(attendance.getAttendanceId(), "n", attendance.getMakeUp());
			} catch (Exception e) {
				e.printStackTrace();
			}

			commentDao.deleteAttendanceCommentByAttendanceId(attendanceId);
			Integer row = until.deleteAttendanceAndFile(attendanceId);
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
	public Map<String, Object> shareTheWork(Integer attendanceId, String phone, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (attendanceId != null && phone != null) {

				Attendance attendance = attendanceDao.queryAttendanceByAttendanceId(attendanceId);

				if (attendance == null) {
					map.put("code", 0);
					map.put("data", attendance);
					return map;
				}

				until.queryAttendanceFile(attendance);
				
				List<String> rawData = userDao.queryUserInfoByPhone(phone);

				if (rawData == null || rawData.size() > 0) {
					map.put("rawData", rawData.get(0));
					attendance.setRawData(rawData.get(0));
				}

				//	任务
				Task task = taskDao.queryTaskByTaskId(attendance.getTaskId());
				taskUntil.queryTaskFile(task);
				
				Teacher teacher = teaDao.queryTeacherById(task.getTeacherId());
				
				//	班级
				MyClass myClass = classDao.queryClassById(attendance.getClassId());
				myClass.setTeacher(teacher);
				
				//	学生
				Student student = stuDao.queryStudentById(attendance.getStudentId());
				student.setMyClass(myClass);
				
				//	机构
				Company company = companyDao.queryCompanyBySchoolId(schoolId);
				attendance.setCompany(company);
				
				attendance.setStudent(student);
				map.put("code", 0);
				map.put("data", attendance);
				map.put("task", task);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}

	@Override
	public Map<String, Object> queryPhoneByClassId(Integer classId, String phone, Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classId == null) {
			map.put("code", 1);
			map.put("msg", "classId不能为空");
		}

		Map<String, Object> self = new HashMap<String, Object>();

		Integer schoolId = classDao.querySchoolIdByClassId(classId);

		Integer sum = alDao.queryUserIntegralSum(phone);
		if (phone != null) {
			Student student = stuDao.getStudentByPhoneAndSchoolId(phone, schoolId);
			if (student != null) {
				student.setIntegral(sum);
				self.put("student", student);
			} else if (student == null) {
				Guardian guardians = guarDao.getGuardianByPhone(phone, schoolId);
				if (guardians != null) {
					Student student3 = stuDao.queryStudentById(studentId);
					if (student3 != null) {
						student3.setIntegral(sum);
						self.put("student", student3);
					}
				}
			}

			List<String> rawDatas = userDao.queryUserInfoByPhone(phone);
			if (rawDatas.size() > 0) {
				String rawData = rawDatas.get(0);
				self.put("rawData", rawData);
			}
		}

		List<Student> students = stuDao.queryStudentByClassIdAndSchoolId(classId, schoolId);

		List<Map<String, Object>> studentInfo = new ArrayList<Map<String, Object>>();
		try {

			for (Student student : students) {

				String userPhone = student.getStudentPhone();

				if (student.getStudentPhone() == null) {
					List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
					userPhone = guardians.get(0).getGuardianPhone();
				}
				Map<String, Object> temp = new HashMap<String, Object>();
				Integer userSum = alDao.queryUserIntegralSum(userPhone);
				if (userSum == null) {
					userSum = 0;
				}
				student.setIntegral(userSum);
				List<String> rawData = userDao.queryUserInfoByPhone(userPhone);
				temp.put("student", student);
				temp.put("rawData", rawData);
				studentInfo.add(temp);
			}

		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常，请稍后再试");
			e.printStackTrace();
		}

		Collections.sort(studentInfo, new Comparator<Map<String, Object>>() {

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Student o1sum = (Student) o1.get("student");
				Student o2sum = (Student) o2.get("student");
				return o2sum.getCount() - o1sum.getCount();
			}

		});

		map.put("code", 0);
		map.put("studentInfo", studentInfo);
		map.put("self", self);
		return map;
	}

	@Override
	public synchronized Map<String, Object> addLikeAttendance(Integer attendanceId, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (attendanceId == null) {
			map.put("code", 0);
			map.put("msg", "attendanceId不能为空");
			return map;
		}
		if (phone == null) {
			map.put("code", 0);
			map.put("msg", "phone不能为空");
			return map;
		}

		Integer row = attendanceDao.addLikeAttendance(attendanceId, phone);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "点赞完成");
		} else {
			map.put("code", 1);
			map.put("msg", "点赞失败");
		}

		return map;
	}

	@Override
	public synchronized Map<String, Object> deleteLikeAttendance(Integer attendanceId, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer row = attendanceDao.deleteLikeAttendance(attendanceId, phone);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除完成");
		} else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}

		return map;
	}

	public void updateStudentFateAndCount(Attendance attendance, boolean isAdd) {

		Student student = stuDao.queryStudentById(attendance.getStudentId());

		
		Integer count = student.getCount();
		Integer studentId = student.getStudentId();
		
		String now = format2.format(attendance.getAttendanceTime());
		String startTime = now + " 00:00:00";
		String endTime = now + " 23:59:59";

		List<Attendance> attendances = attendanceDao.queryManyAttendanceByTimeAndStudentId(startTime, endTime,studentId);
		if (isAdd) {
			// 增加打卡次数
			stuDao.updateCount(count + 1, studentId);

			// 坚持天数
			int affirm = 0;
			for (Attendance attendance2 : attendances) {
				if (attendance2.getIsAffirm() > 0) {
					affirm = attendance2.getIsAffirm();
				}
			}
			if (affirm == 0) {
				stuDao.updateStudentFate(studentId, student.getFate() + 1);
			}
			
		} else if (!isAdd) {
			// 减少打卡次数
			stuDao.updateCount(count - 1, studentId);

			//	坚持天数
			int affirm = 0;
			for (Attendance attendance2 : attendances) {
				if (attendance2.getIsAffirm() > 0) {
					if (attendance2.getIsAffirm() > 0) {
						affirm++;
					}
				}
			}
			if (affirm == 1 ) {
				stuDao.updateStudentFate(studentId, student.getFate() - 1);
			}
		}

	}

	@Override
	public Map<String, Object> queryAttendanceByStudentIdAndTaskId(Integer taskId, Integer studentId, Integer classId,String phone,Integer longTermUse) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (longTermUse == null) {
				longTermUse = 3;
			}
			Attendance attendance = null;
			if (longTermUse == 1) {
				String now = format2.format(new Date());
				String startTime = now + " 00:00:00";
				String endTime = now + " 23:59:59";
				attendance = attendanceDao.queryAttendanceByTimeAndStudentId(startTime, endTime, studentId,
						taskId, classId);
			}else if (longTermUse == 0) {
				attendance = attendanceDao.queryAttendanceByStudentIdAndTaskId(taskId, studentId, classId);
			}
			
			List<String> rawData = userDao.queryUserInfoByPhone(phone);
			if (attendance != null) {
				until.queryAttendanceFile(attendance);
				attendance.setComments(commentDao.queryCommentsByAttendanceId(attendance.getAttendanceId()));
				Integer schoolId = classDao.querySchoolIdByClassId(classId);
				Company company = companyDao.queryCompanyBySchoolId(schoolId);
				attendance.setCompany(company);
				if (rawData != null && rawData.size() > 0) {
					attendance.setRawData(rawData.get(0));
				}
			}
			map.put("code", 0);
			map.put("data", attendance);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryAttendanceDetails(Integer schoolId, Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();

		attendanceDao.queryAttendanceByTaskId(1, classId);

		return null;
	}

	@Override
	public Map<String, Object> FindYourOwnHomework(Integer taskId, Integer classId, Integer studentId ) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<Attendance> attendances = attendanceDao.queryAttendanceByStudentIdAndLongTimeTaskId(taskId, studentId, classId);
			for (Attendance attendance : attendances) {
				List<AttendanceComment> comments = commentDao
						.queryCommentsByAttendanceIdAndIsSecret(attendance.getAttendanceId(), 0);

				List<AttendanceComment> comments_2 = commentDao
						.queryCommentsByAttendanceIdAndIsSecret(attendance.getAttendanceId(), 1);
				if (comments_2 != null && comments_2.size() > 0) {
					comments.addAll(comments_2);
				}
				attendance.setComments(comments);
				
				// 点赞人
				updateLikeName(attendance);

				// 查询作业文件
				until.queryAttendanceFile(attendance);
			}
			map.put("code", 0);
			map.put("data", attendances);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
