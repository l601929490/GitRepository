package com.siwo.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.AttendanceDao;
import com.siwo.dao.CommonsDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.ScoreSumDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.ClientDao;
import com.siwo.dao.UserIntegralDao;
import com.siwo.model.Attendance;
import com.siwo.model.Guardian;
import com.siwo.model.ScoreSum;
import com.siwo.model.Student;
import com.siwo.service.CommonsService;

@Service
public class CommonsServiceImpl implements CommonsService {

	@Autowired
	private CommonsDao dao;

	@Autowired
	private UserIntegralDao alDao;

	@Autowired
	private StudentDao stuDao;

	@Autowired
	private ClientDao userDao;

	@Autowired
	private GuardianDao guarDao;

	@Autowired
	private AttendanceDao attDao;

	@Autowired
	private MyClassDao classDao;
	
	@Autowired
	private ScoreSumDao ssDao;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String, Object> addColorImg(String homeImg, Integer schoolId, String colour, Integer WhetherNeed,
			String creator, Date createTime) {

		Map<String, Object> map = new HashMap<String, Object>();

		Integer row = dao.addColorImg(homeImg, schoolId, colour, WhetherNeed, creator, createTime);

		if (row > 0) {
			map.put("code", "0");
			map.put("msg", "添加成功");
		} else {
			map.put("code", "1");
			map.put("msg", "添加失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> updateColorImg(String homeImg, Integer schoolId, String colour, Integer imgColorId,
			Integer WhetherNeed, String editor, Date editSession) {

		Map<String, Object> map = new HashMap<String, Object>();

		Integer row = dao.updateColorImg(homeImg, schoolId, colour, imgColorId, WhetherNeed, editor, editSession);

		if (row > 0) {
			map.put("code", "0");
			map.put("msg", "更新成功");
		} else {
			map.put("code", "1");
			map.put("msg", "更新失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> limitColorImg(Integer page, Integer limit, Integer schoolId) {

		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<Map<String, String>> maps = dao.limitColorImg(start, limit, schoolId);

		int count = dao.getColorImgCount(schoolId);

		if (maps != null) {
			map.put("code", 0);
			map.put("data", maps);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> deleteColorImgById(Integer imgColorId) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (imgColorId == null) {
			map.put("code", 1);
			map.put("data", "imgColorId不能为空");
			return map;
		}

		Integer row = dao.deleteColorImgById(imgColorId);

		if (row > 0) {
			map.put("code", "0");
			map.put("msg", "删除成功");
		} else {
			map.put("code", "1");
			map.put("msg", "删除失败");
		}

		return map;
	}

	@Override
	public Map<String, Object> deleteManyColorImg(String[] imgColorIds) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (imgColorIds != null) {
			Integer row = dao.deleteManyColorImg(imgColorIds);
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
	public Map<String, Object> queryColorImgBySchoolId(Integer schoolId) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId == null) {
			map.put("code", 1);
			map.put("data", "imgColorId不能为空");
		} else {
			List<Map<String, String>> maps = dao.queryColorImgBySchoolId(schoolId);

			if (maps != null) {
				map.put("code", 0);
				map.put("data", maps);
			} else {
				map.put("code", 1);
				map.put("data", "暂无数据");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> learningCondition(Long startTime, Long endTime, Integer classId) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 校区id
		Integer schoolId = classDao.querySchoolIdByClassId(classId);

		// 积分
		List<Student> userIntegral = new ArrayList<>();
		// 打卡人
		List<Student> clockList = new ArrayList<>();
		// 未打卡人
		List<String> notClockInStudent = new ArrayList<String>();

		// 开始时间
		String begin = format2.format(startTime);
		begin+=" 00:00:00";
		// 截止时间
		String over = format2.format(endTime);
		over+=" 00:00:00";
		
		// 全班学生
		List<Student> students = stuDao.queryStudentByClassId(classId);
		int recordCount = 0;
		for (Student student : students) {
			
			//	头像
			List<String> rawDatas = userDao.queryUserInfoByPhone(student.getStudentPhone());
			if (rawDatas.size() == 0) {
				List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
				for (Guardian guardian : guardians) {
					rawDatas = userDao.queryUserInfoByPhone(guardian.getGuardianPhone());
					if (rawDatas.size() > 0) {
						student.setRawData(rawDatas.get(0));
						break;
					}
				}
			}else {
				student.setRawData(rawDatas.get(0));
			}
			
			//	积分
			ScoreSum scoreSum = ssDao.queryScoreSumBystudentId(student.getStudentId());
			if (scoreSum != null) {
				student.setIntegral(scoreSum.getRemain());
			}else if (scoreSum == null) {
				student.setIntegral(0);
			}
			
			//	打卡
			List<Attendance> attendances = attDao.queryManyAttendanceByTimeAndStudentId(begin, over, student.getStudentId());
			if (attendances !=null && attendances.size() != 0 ) {
				clockList.add(student);
				recordCount+=attendances.size();
			}else {
				notClockInStudent.add(student.getStudentName());
			}
			
			userIntegral.add(student);
		}

		Collections.sort(clockList, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				return o2.getFate() - o1.getFate();
			}
		});
		Collections.sort(userIntegral, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				return o2.getIntegral() - o1.getIntegral();
			}
		});
		
		// 应打卡人数
		map.put("ShouldClockIn", students.size());
		// 实际打卡人数
		map.put("HasClock", clockList.size());
		// 打卡排行
		map.put("clockList", clockList);
		// 积分排行
		map.put("userIntegral", userIntegral);
		// 未打卡人信息
		map.put("notClockInStudent", notClockInStudent);
		// 总记录数
		map.put("recordCount", recordCount);

		return map;
	}



	@Override
	public Map<String, Object> queryStudentMessage(Long startTime, Long endTime, Integer classId, String studentName) {

		Map<String, Object> map = learningCondition(startTime, endTime, classId);

		// 积分
		List<Map<String, Object>> IntegralPerson = new ArrayList<Map<String, Object>>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> UserIntegral = (List<Map<String, Object>>) map.get("userIntegral");

		for (Map<String, Object> map2 : UserIntegral) {
			Student student = (Student) map2.get("student");
			if (student.getStudentName().contains(studentName)) {
				IntegralPerson.add(map2);
			}
		}

		// 未打卡人
		List<String> notClockPerson = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<String> notClockInStudent = (List<String>) map.get("notClockInStudent");

		for (String name : notClockInStudent) {
			if (name.contains(studentName)) {
				notClockPerson.add(name);
			}
		}

		// 打卡人
		List<Map<String, Object>> clockListPerson = new ArrayList<Map<String, Object>>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> clockList = (List<Map<String, Object>>) map.get("clockList");
		for (Map<String, Object> map2 : clockList) {
			Student student = (Student) map2.get("student");
			if (student.getStudentName().contains(studentName)) {
				clockListPerson.add(map2);
			}
		}

		map.put("IntegralPerson", IntegralPerson);
		map.put("notClockPerson", notClockPerson);
		map.put("clockListPerson", clockListPerson);

		return map;
	}

//	// 积分
//	public List<Map<String, Object>> getUserIntegral(Student student, String begin, String over,
//			List<Map<String, Object>> userIntegral, String phone) {
//		// 积分
//		Map<String, Object> reco = new HashMap<String, Object>();
//
//		List<String> list = userDao.queryUserInfoByPhone(phone);
//		String rawData = null;
//		if (list.size() > 0) {
//			rawData = list.get(0);
//		}
//		Integer integral = 0;
//		// 学生积分
//		Integer sum = alDao.queryUserIntegralByTime(begin, over, phone);
//		if (sum != null) {
//			integral += sum;
//		}
//		// 家长积分
//		List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
//		for (Guardian guardian : guardians) {
//			Integer guarSum = alDao.queryUserIntegralByTime(begin, over, guardian.getGuardianPhone());
//			if (guarSum != null) {
//				integral += guarSum;
//			}
//		}
//
//		if (integral != 0) {
//			reco.put("rawData", rawData);
//			reco.put("sum", integral);
//			reco.put("student", student);
//			userIntegral.add(reco);
//		}
//		return userIntegral;
//	}

//	// 未打卡人
//	public List<String> notClockInStudent(Student student, String begin, String over, String phone, Integer classId,
//			List<String> notClockInStudent) {
//
//		// 查询未打卡人信息
//		List<Attendance> attendances = attDao.queryAttendanceByTimeAndPhoneAndClassId(begin, over, phone, classId);
//
//		if (attendances == null || attendances.size() == 0) {
//
//			List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
//
//			if (guardians.size() == 0) {
//				notClockInStudent.add(student.getStudentName());
//			} else {
//				for (Guardian guardian : guardians) {
//
//					List<Attendance> attendances_2 = attDao.queryAttendanceByTimeAndPhoneAndClassId(begin, over,
//							guardian.getGuardianPhone(), classId);
//
//					if (attendances_2 == null || attendances_2.size() == 0) {
//						notClockInStudent.add(student.getStudentName());
//					}
//				}
//			}
//		}
//		return notClockInStudent;
//	}
}
