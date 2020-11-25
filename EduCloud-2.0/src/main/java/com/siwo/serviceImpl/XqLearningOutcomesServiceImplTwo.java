package com.siwo.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.siwo.commons.XqUntil;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.XqAllocationDao;
import com.siwo.dao.XqLearningOutcomesDao;
import com.siwo.model.Student;
import com.siwo.model.XqClassDay;
import com.siwo.model.XqLearningOutcomes;
import com.siwo.model.XqType;
import com.siwo.model.XqTypeStar;
import com.siwo.service.XqLearningOutcomesServiceTwo;

@Service
public class XqLearningOutcomesServiceImplTwo implements XqLearningOutcomesServiceTwo {

	@Autowired
	private XqLearningOutcomesDao dao;
	@Autowired
	private StudentDao stuDao;
	@Autowired
	private XqAllocationDao allDao;
	@Autowired
	private MyClassDao classDao;

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	//	老师评分
	@Override
	public Map<String, Object> addXqLearningOutcomes(XqLearningOutcomes outcome) {

		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println(outcome);
		
		try {
			String nowTime = format.format(new Date());
			Date date = format.parse(nowTime);

			//	查询有没有评分
			XqLearningOutcomes xqLearningOutcomes = dao.queryXqLearningOutcomesByStudentIdTwo(outcome.getStudentId(),
					outcome.getClassDayId(),outcome.getClassId());
			
			//	评分时间
			outcome.setCommentTime(date);
			
			if (xqLearningOutcomes == null) {
				dao.addXqLearningOutcomes(outcome);
				//	添加打星分数
				List<XqTypeStar> xqTypeStars = outcome.getXqTypeStars();
				for (XqTypeStar xqTypeStar : xqTypeStars) {
					xqTypeStar.setXqLearningOutcomesId(outcome.getXqLearningOutcomesId());
					dao.addXqTypeStar(xqTypeStar);
				}
			} else {
				dao.updateXqLearningOutcomes(outcome);
				//	修改打星分数
				List<XqTypeStar> xqTypeStars = outcome.getXqTypeStars();
				for (XqTypeStar xqTypeStar : xqTypeStars) {
					xqTypeStar.setXqLearningOutcomesId(outcome.getXqLearningOutcomesId());
					dao.updateXqTypeStar(xqTypeStar);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "打分失败");
		}

		map.put("code", 0);
		map.put("msg", "打分成功");
		return map;

	}

	//	学生学情详情
	@Override
	public Map<String, Object> queryXqLearningOutcomesByStudentId(Integer studentId, Integer schoolId,
			Integer classId) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId == null || classId == null) {

			map.put("code", 0);
			map.put("msg", "参数不能为空");

			return map;
		}
		System.out.println(studentId+"---------");
		try {
			Student student = stuDao.queryStudentById(studentId);
			List<XqType> xqTypes = allDao.queryXqTypes(schoolId);
			List<XqClassDay> xqClassDays2 = allDao.queryXqClassDaysByClassId(classId);

			List<XqClassDay> xqClassDays = new ArrayList<XqClassDay>();
			for (XqClassDay xqClassDay : xqClassDays2) {
				XqLearningOutcomes outcomes = dao.getXqLearningOutcomesByStudentIdTwo(studentId, xqClassDay.getClassDayId(),classId);
				if (outcomes != null) {
					xqClassDays.add(xqClassDay);
				}
			}
			Long firstTime = 0l; 
			if (xqClassDays.size() > 0) {
				firstTime = xqClassDays.get(0).getClassDate().getTime();
			}else {
				xqClassDays.addAll(xqClassDays2);
				firstTime = System.currentTimeMillis();
			}
					
			// 当前时间戳
			Long nowTime = System.currentTimeMillis();
			Long time = nowTime - firstTime;
			if (time < 0) {
				time = nowTime;
			}
			XqClassDay xqClassDay = null;

			//	取距离现在时间最近的过去时间
			for (int i = 0; i < xqClassDays.size(); i++) {
				Long twoTime = xqClassDays.get(i).getClassDate().getTime();
				Long num = nowTime - twoTime;
				if (num <= time && num >= 0) {
					xqClassDay = xqClassDays.get(i);
					time = num;
				}
			}
			if (xqClassDay == null) {
				student.setXqTypes(xqTypes);
				map.put("code", 0);
				map.put("data", student);
				map.put("Daytime", format.format(new Date()));
				map.put("comment", null);
				return map;
			}
			
			// 学生打分
			XqLearningOutcomes xqLearningOutcomes = dao.queryXqLearningOutcomesByStudentIdTwo(student.getStudentId(),
					xqClassDay.getClassDayId(), classId);
			for (XqType xqType : xqTypes) {

				if (xqLearningOutcomes != null) {
					XqTypeStar xqTypeStar = dao.queryXqTypeStar(xqLearningOutcomes.getXqLearningOutcomesId(), xqType.getXqTypeId());
					xqType.setStar(xqTypeStar.getStar());
				}
			}

			student.setXqTypes(xqTypes);
			map.put("code", 0);
			map.put("data", student);
			map.put("Daytime", format.format(xqClassDay.getClassDate()));
			map.put("comment", xqLearningOutcomes.getComment());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}

		System.out.println(map);
		return map;
	}

	//	老师页面返回数据
	@Override
	public Map<String, Object> queryXqLearningOutcomesByClassId(Integer classId, Integer classDayId, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classId == null) {

			map.put("code", 0);
			map.put("msg", "参数不能为空");

			return map;
		}
		
		try {
//			String nowTime = format.format(new Date());
			List<Student> students = stuDao.queryStudentByClassId(classId);

			List<XqType> xqTypes = allDao.queryXqTypes(schoolId);
			for (Student student : students) {
				XqLearningOutcomes xqLearningOutcomes = dao.queryXqLearningOutcomesByStudentIdTwo(
						student.getStudentId(),classDayId, classId);
				
				student.setXqTypes(xqTypes);
				
				if (xqLearningOutcomes == null) {
					xqLearningOutcomes = new XqLearningOutcomes();
					List<XqTypeStar> xqTypeStars = new ArrayList<XqTypeStar>();
					for (XqType xqType : xqTypes) {
						XqTypeStar xqTypeStar = new XqTypeStar();
						xqTypeStar.setXqType(xqType);
						xqTypeStars.add(xqTypeStar);
					}
					xqLearningOutcomes.setXqTypeStars(xqTypeStars);
					student.setXqLearningOutcomes(xqLearningOutcomes);
					// 未点评
					student.setIsMark(0);
				} else {
					List<XqTypeStar> xqTypeStars = dao.queryManyXqTypeStar(xqLearningOutcomes.getXqLearningOutcomesId());
					for (XqTypeStar xqTypeStar : xqTypeStars) {
						XqType xqType = allDao.queryXqTypeByXqTypeId(xqTypeStar.getXqTypeId());
						xqTypeStar.setXqType(xqType);
					}
					xqLearningOutcomes.setXqTypeStars(xqTypeStars);
					// 已点评
					student.setIsMark(1);
					student.setXqLearningOutcomes(xqLearningOutcomes);
				}
			}
			Collections.sort(students, new Comparator<Student>() {

				@Override
				public int compare(Student o1, Student o2) {
					return o1.getIsMark()-o2.getIsMark();
				}
				
			});
			map.put("code", 0);
			map.put("data", students);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}

		return map;
	}


	@Override
	public Map<String, Object> queryLineGraph(Integer classId, Integer studentId,Integer schoolId) {
		
		List<XqType> xqTypes = allDao.queryXqTypes(schoolId);
		List<XqClassDay> xqClassDays = allDao.queryXqClassDaysByClassId(classId);
		// 	获取所有课时
		List<String> dates = new ArrayList<String>();
		//	排序
		XqUntil.sortDate(xqClassDays, true);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 	折线图时间
		int temp = 0;
		for (int i = 0; i < xqClassDays.size(); i++) {
			if (i < 7) {
				temp = i;
			}else if (i >= 7) {
				break;
			}
			
			if (xqClassDays.get(i).getClassDate().getTime() > System.currentTimeMillis()) {
				temp = i;
			}
		}
		List<XqClassDay> xqClassDays2 = new ArrayList<XqClassDay>();
		for (int i = 0; i < xqClassDays.size(); i++) {
			xqClassDays2.add(xqClassDays.get(i));
			if (i  == temp) {
				break;
			}
		}
		for (int i = xqClassDays2.size()-1; i >= 0; i--) {
			String date = format.format(xqClassDays2.get(i).getClassDate());
			dates.add(date);
		}

		// 折线图数组
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (XqType xqType : xqTypes) {
			// 折线图数据
			Map<String, Object> map2 = new HashMap<String, Object>();

			// 折线图数据名
			map2.put("xqTypeName", xqType.getXqTypeName());
			
			// 折线图真实数据
			List<Integer> stars = new ArrayList<Integer>();
			for (XqClassDay xqClassDay2 : xqClassDays2) {
				XqLearningOutcomes xqLearningOutcomes2 = dao.queryXqLearningOutcomesByStudentIdTwo(studentId, xqClassDay2.getClassDayId(), classId);
				//	老师未打分取默认分
				if (xqLearningOutcomes2 == null) {
					stars.add(xqType.getDefaultStar());
				}else {
					XqTypeStar xqTypeStar = dao.queryXqTypeStar(xqLearningOutcomes2.getXqLearningOutcomesId(), xqType.getXqTypeId());
					stars.add(xqTypeStar.getStar());
				}
			}
			map2.put("star", stars);
			list.add(map2);
		}

		//	返回数据
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap.put("time", dates);
		newMap.put("xqData", list);

		return newMap;
	}

	//	每天凌晨1点执行一次
	@Scheduled(cron = "0 0 1 * * ?")
	@Override
	public void automaticGrading() {
		
		try {
			//	获取昨天的日期
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar calendar = new GregorianCalendar();

			calendar.setTime(new Date());

			calendar.add(Calendar.DATE,-1);

			String date= format.format(calendar.getTime());
			
			String start = date + " 00:00:00";
			String end = date + " 23:59:59";
			//	查出昨天所有课时
			List<XqClassDay> xqClassDays = allDao.queryXqClassDayByTime(start, end);
			
			for (XqClassDay xqClassDay : xqClassDays) {
				List<Student> students = stuDao.queryStudentByClassId(xqClassDay.getClassId());
				for(Student student : students) {
					List<XqLearningOutcomes> xqLearningOutcomes = dao.getXqLearningOutcomesByStudentId(student.getStudentId(), xqClassDay.getClassDayId(), xqClassDay.getClassId());
					System.out.println(xqLearningOutcomes.size());
					if (xqLearningOutcomes == null || xqLearningOutcomes.size() == 0) {
						//	如果未点评自动点评
						Integer schoolId = classDao.querySchoolIdByClassId(xqClassDay.getClassId());
						List<XqType> xqTypes = allDao.queryXqTypes(schoolId);
						for (XqType xqType : xqTypes) {
							XqLearningOutcomes xqLearningOutcome = new XqLearningOutcomes(null, xqClassDay.getClassId(), student.getStudentId(), xqType.getXqTypeId(), xqClassDay.getClassDayId(), 3,null,null,null);
							dao.addXqLearningOutcomes(xqLearningOutcome);
						}
					}
				}
			}
			System.out.println("-------------自动点评昨日学情------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
