package com.siwo.serviceImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

import com.github.pagehelper.PageHelper;
import com.siwo.commons.SendSubscribe;
import com.siwo.dao.AdminDao;
import com.siwo.dao.AttendanceDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.ScoreGetScoreDao;
import com.siwo.dao.ScoreRecipientDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TaskDao;
import com.siwo.dao.TeacherDao;
import com.siwo.dao.ClientDao;
import com.siwo.dao.CompanyDao;
import com.siwo.dao.DynamicConditionDao;
import com.siwo.dao.GuardianDao;
import com.siwo.model.Admin;
import com.siwo.model.Attendance;
import com.siwo.model.Company;
import com.siwo.model.DynamicCondition;
import com.siwo.model.Guardian;
import com.siwo.model.MyClass;
import com.siwo.model.ScoreGetScore;
import com.siwo.model.ScoreRecipient;
import com.siwo.model.Student;
import com.siwo.model.Task;
import com.siwo.model.Teacher;
import com.siwo.model.WxParam;
import com.siwo.service.AttendanceService;
import com.siwo.service.ScoreGetScoreService;
import com.siwo.service.TeacherService;

@Service
@Validated
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDao dao;

	@Autowired
	private StudentDao stuDao;
	
	@Autowired
	private GuardianDao guarDao;

	@Autowired
	private MyClassDao classDao;

	@Autowired
	private AttendanceDao attDao;

	@Autowired
	private ClientDao userDao;

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private AttendanceService attService;
	
	@Autowired
	private ScoreGetScoreService scoreService;
	
	@Autowired
	private SendSubscribe sendSubscribe;
	
	@Autowired
	private ScoreRecipientDao recipientDao;

	@Override
	public Map<String, Object> addTeacher(Teacher teacher, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}

		Teacher teacher2 = dao.getTeacherByPhoneAndSchoolId(teacher.getTeacherPhone(), teacher.getSchoolId());

		if (teacher2 != null) {
			map.put("code", "1");
			map.put("msg", "此人已经是此班级的老师");
			return map;
		}

		// 1在职
		teacher.setStatus("1");
		Integer row = dao.addTeacher(teacher);

		if (row > 0) {

			adminDao.queryAdminByAdminAccount(teacher.getTeacherPhone());

			Admin admin = new Admin(null, "teacher", teacher.getTeacherPhone(), "123456", "是", 0, teacher.getSchoolId(),
					null, teacher.getTeacherName(), null,teacher.getTeacherCreator(),new Date(),null,null);

			adminDao.addAdmin(admin);
			adminDao.addSchoolAdmin(admin.getAdminId(), teacher.getSchoolId());

			map.put("code", 0);
			map.put("msg", "添加成功");
		} else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> getAllTeacher(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Teacher> teachers = dao.getAllTeacher(schoolId);
		if (teachers == null) {
			map.put("code", 1);
			map.put("msg", "暂时没有数据");
		} else {
			map.put("code", 0);
			map.put("data", teachers);
		}

		return map;
	}

	@Override
	public Map<String, Object> deleteManyTeacher(String[] teacherIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (teacherIds != null) {

			for (String id : teacherIds) {

				List<Integer> classIds = dao.queryClassByTeacherId(Integer.valueOf(id));
				Teacher teacher = dao.queryTeacherById(Integer.valueOf(id));
				if (classIds.size() > 0) {
					map.put("code", 1);
					map.put("msg", teacher.getTeacherName() + "还未解除绑定的班级");
					return map;
				}
				if (teacher != null) {
					userDao.deleteUserByPhone(teacher.getTeacherPhone());
				}

				Teacher teacher2 = dao.queryTeacherById(Integer.valueOf(id));
				adminDao.deleteAdmin(teacher2.getTeacherPhone(),teacher2.getSchoolId());
			}

			Integer row = dao.deleteManyTeacher(teacherIds);
			dao.deleteManyTeacherClass(teacherIds);
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
	public Map<String, Object> limitTeacher(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageHelper.startPage(page, limit);
		List<Teacher> teachers = dao.limitTeacher(schoolId);

		for (Teacher teacher : teachers) {
			//查询是否为核销员
			ScoreRecipient recipient= recipientDao.queryRecipientByTeacherId(teacher.getTeacherId());
			if (recipient!=null) {
				teacher.setFlag("y");
			}else {
				teacher.setFlag("n");
			}
		}
		
		Collections.sort(teachers, new Comparator<Teacher>() {
			
			@Override
			public int compare(Teacher o1, Teacher o2) {

				if (!o1.getStatus().equals(o2.getStatus())) {
					return Integer.valueOf(o1.getStatus()) - Integer.valueOf(o2.getStatus());
				}

				if (o1.getStatus().equals(o2.getStatus())) {
					if ((o2.getTeacherCreationTime().getTime() - o1.getTeacherCreationTime().getTime()) > 0) {
						return 1;
					} else {
						return -1;
					}
				}
				return 0;
			}

		});
		Integer count = dao.getTeacherCount(schoolId);
		if (teachers != null) {
			map.put("code", 0);
			map.put("data", teachers);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteTeacher(Integer teacherId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Teacher teacher = dao.queryTeacherById(teacherId);
		if (teacher != null) {
			userDao.deleteUserByPhone(teacher.getTeacherPhone());
		}

		Integer row = dao.deleteTeacher(teacherId);
		dao.deleteTeacherClass(teacherId);
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
	public Map<String, Object> updateTeacher(Teacher teacher) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (teacher != null) {
			teacher.setTeacherEditSession(new Date());
		}

		Integer row = dao.updateTeacher(teacher);

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
	public Map<String, Object> updateTeacherShowByTeacherId(String[] teacherIds, String teacherShow, Integer schoolId) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (schoolId == null) {
			map.put("code", 1);
			map.put("msg", "修改失败");
			return map;
		}

		List<Teacher> teachers = dao.queryShowTeacher(schoolId, "1");

		if (teacherShow.equals("1")) {
			if (teachers.size() >= 3) {
				map.put("code", 1);
				map.put("msg", "已展示了3个老师");
				return map;
			}
			int n = 3 - teachers.size();
			if (teacherIds.length > n) {
				map.put("code", 1);
				map.put("msg", "最多展示3个老师");
				return map;
			}
		} else {
			if (teacherIds.length > 3) {
				map.put("code", 1);
				map.put("msg", "最多展示3个老师");
				return map;
			}
		}

		for (String id : teacherIds) {
			dao.updateTeacherShowByTeacherId(Integer.valueOf(id), teacherShow);
		}
		map.put("code", 0);
		map.put("msg", "修改成功");

		return map;
	}

	@Override
	public Map<String, Object> addTeacherClass(Integer classId, String[] teacherIds) {
		Map<String, Object> map = new HashMap<String, Object>();

		dao.deleteClassTeacher(classId);

		if (teacherIds != null && teacherIds.length != 0) {
			for (String id : teacherIds) {

				Integer row = dao.addTeacherClass(classId, Integer.valueOf(id));
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				}

			}
		} else {
			map.put("code", 0);
			map.put("msg", "操作成功");
		}

		return map;
	}

	@Override
	public Map<String, Object> queryTeacherByClassId(Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classId == null) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}

		List<Teacher> teachers = dao.queryTeacherByClassId(classId);

		map.put("code", 0);
		map.put("data", teachers);

		return map;
	}

	@Override
	public Map<String, Object> deleteTeacherAndClass(Integer classId, Integer teacherId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classId == null || teacherId == null) {
			map.put("code", 1);
			map.put("msg", "解绑失败");
			return map;
		}
		Integer row = dao.deleteTeacherAndClass(classId, teacherId);
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
	public Map<String, Object> queryTeacherByName(String teacherName, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		teacherName = "%" + teacherName + "%";
		List<Teacher> teachers = dao.queryTeacherByName(teacherName, schoolId);
		
		for (Teacher teacher : teachers) {
			//查询是否为核销员
			ScoreRecipient recipient= recipientDao.queryRecipientByTeacherId(teacher.getTeacherId());
			if (recipient!=null) {
				teacher.setFlag("y");
			}else {
				teacher.setFlag("n");
			}
		}
		
		if (teachers != null) {
			map.put("code", 0);
			map.put("data", teachers);
		}
		return map;
	}

	@Override
	public Map<String, Object> updateTeacherStatus(String[] teacherIds, String status) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (teacherIds == null || teacherIds.length == 0 || StringUtils.isEmpty(status)) {
			map.put("code", 1);
			map.put("msg", "更新失败");
			return map;
		}
		
		for (String teacherId : teacherIds) {
			dao.updateTeacherStatus(Integer.valueOf(teacherId), status);
		}
		map.put("code", 0);
		map.put("msg", "更新成功");
		return map;
	}

	@Override
	public Map<String, Object> queryTeacherById(Integer teacherId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (teacherId == null) {
			map.put("code", 1);
			map.put("msg", "teacherId不能为空");
		}
		Teacher teacher = dao.queryTeacherById(teacherId);
		List<String> rawdata = clientDao.queryUserInfoByPhone(teacher.getTeacherPhone());
		map.put("code", 0);
		map.put("data", teacher);
		map.put("rawData", rawdata);
		return map;
	}

	@Override
	public Map<String, Object> affirm(String phone, Integer attId, Integer taskId, Integer affirm) {
		Map<String, Object> map = new HashMap<String, Object>();

		Attendance attendance = attDao.queryAttendanceByAttendanceId(attId);
		
		if (affirm > 0) {
			// 增加学生打卡天数
			attService.updateStudentFateAndCount(attendance, true);
		}else if (affirm == 0) {
			// 减少学生打卡天数
			attService.updateStudentFateAndCount(attendance, false);
		}
		
		// 修改学生作业是否通过/优秀
		Integer row = attDao.updateIsAffirm(attId, affirm);
		
		if (row > 0) {
			scoreService.addGoodsReview(attendance.getAttendanceId(), affirm);
			map.put("code", 0);
			map.put("msg", "确认成功");
		} else {
			map.put("code", 2);
			map.put("msg", "确认失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateAttendanceCorrect(Integer attendanceId,Integer correct) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			attDao.updateCorrect(attendanceId, correct);
			map.put("code", 0);
			map.put("msg", "修改成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "修改失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateScore(Integer attId, Integer score) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			attDao.updateScore(attId, score);
			//	打分加积分
			scoreService.addGoodsReviewEvaluation(attId, score);
			map.put("code", 0);
			map.put("msg", "打分成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "打分失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> aKeyToRemind(Integer classId,Integer taskId,String pagePath) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (classId == null) {
			map.put("code", 1);
			map.put("msg", "classid不能为空");
			return map;
		}
		MyClass myClass = classDao.queryClassById(classId);
		Integer schoolId = classDao.querySchoolIdByClassId(classId);
		Company company = companyDao.queryCompanyBySchoolId(schoolId);
		//	未提交的学生
		List<Student> students = stuDao.queryNotSubmittedStudentByClassId(classId,taskId);
		
		Task task = taskDao.queryTaskByTaskId(taskId);
		
		for (Student student : students) {
			
			try {
				
				String stuOpenId = clientDao.queryOpenIdByPhone(student.getStudentPhone(), company.getCompanyId());
				sendSubscribe.aKeyToRemind(WxParam.access_token, student, myClass,stuOpenId,pagePath,task.getTaskSubject());
				
				List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
				for (Guardian guardian : guardians) {
					String guarOpenId = clientDao.queryOpenIdByPhone(guardian.getGuardianPhone(), company.getCompanyId());
					sendSubscribe.aKeyToRemind(WxParam.access_token, student, myClass, guarOpenId,pagePath,task.getTaskSubject());;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("code", 0);
		map.put("msg", "提醒成功");
		return map;
	}
	
}
