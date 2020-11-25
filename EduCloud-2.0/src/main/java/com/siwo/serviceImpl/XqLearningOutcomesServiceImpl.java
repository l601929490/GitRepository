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
import com.siwo.dao.SchoolDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.XqAllocationDao;
import com.siwo.dao.XqLearningOutcomesDao;
import com.siwo.model.Student;
import com.siwo.model.XqClassDay;
import com.siwo.model.XqLearningOutcomes;
import com.siwo.model.XqType;
import com.siwo.service.XqLearningOutcomesService;

@Service
public class XqLearningOutcomesServiceImpl implements XqLearningOutcomesService {

	@Autowired
	private XqLearningOutcomesDao dao;
	@Autowired
	private StudentDao stuDao;
	@Autowired
	private XqAllocationDao allDao;
	@Autowired
	private MyClassDao classDao;

	@Override
	public Map<String, Object> addXqLearningOutcomes(List<XqLearningOutcomes> outcomes) {

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			for (XqLearningOutcomes outcome : outcomes) {
				XqLearningOutcomes xqLearningOutcomes = dao.queryXqLearningOutcomesByStudentId(outcome.getStudentId(),outcome.getClassDayId(), outcome.getXqTypeId(), outcome.getClassId());

				if (xqLearningOutcomes == null) {
					dao.addXqLearningOutcomes(outcome);
				} else {
					dao.updateXqLearningOutcomes(outcome);
				}
			}

		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "打分失败");
		}

		map.put("code", 0);
		map.put("msg", "打分成功");
		return map;

	}

	@Override
	public synchronized Map<String, Object> queryXqLearningOutcomesByStudentId(Integer studentId, Integer schoolId,
			Integer classId) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId == null || classId == null) {

			map.put("code", 0);
			map.put("msg", "参数不能为空");

			return map;
		}
		try {
			Student student = stuDao.queryStudentById(studentId);
			List<XqType> xqTypes = allDao.queryXqTypes(schoolId);
			List<XqClassDay> xqClassDays2 = allDao.queryXqClassDaysByClassId(classId);

			List<XqClassDay> xqClassDays = new ArrayList<XqClassDay>();
			for (XqClassDay xqClassDay : xqClassDays2) {
				List<XqLearningOutcomes> list = dao.getXqLearningOutcomesByStudentId(studentId, xqClassDay.getClassDayId(),classId);
				if (list!= null && list.size() > 0 ) {
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
				return map;
			}
			for (XqType xqType : xqTypes) {

				// 学生打分
				XqLearningOutcomes xqLearningOutcomes = dao.queryXqLearningOutcomesByStudentId(student.getStudentId(),
						xqClassDay.getClassDayId(), xqType.getXqTypeId(), classId);
				if (xqLearningOutcomes != null) {
					xqType.setStar(xqLearningOutcomes.getStar());
				}
			}

			student.setXqTypes(xqTypes);
			map.put("code", 0);
			map.put("data", student);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}

		return map;
	}

	@Override
	public Map<String, Object> queryXqLearningOutcomesByClassId(Integer classId, Integer classDayId, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (classId == null || classDayId == null) {

			map.put("code", 0);
			map.put("msg", "参数不能为空");

			return map;
		}

		try {
			List<Student> students = stuDao.queryStudentByClassId(classId);

			for (Student student : students) {
				List<XqType> xqTypes = allDao.queryXqTypes(schoolId);
				int temp = 0;
				for (XqType xqType : xqTypes) {
					XqLearningOutcomes xqLearningOutcomes = dao.queryXqLearningOutcomesByStudentId(
							student.getStudentId(), classDayId, xqType.getXqTypeId(), classId);
					if (xqLearningOutcomes != null) {
						xqType.setStar(xqLearningOutcomes.getStar());
					} else {
						temp++;
					}
				}
				if (xqTypes.size() == temp) {
					// 未点评
					student.setIsMark(0);
				} else {
					// 已点评
					student.setIsMark(1);
				}
				student.setXqTypes(xqTypes);
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
				XqLearningOutcomes xqLearningOutcomes2 = dao.queryXqLearningOutcomesByStudentId(studentId,
						xqClassDay2.getClassDayId(), xqType.getXqTypeId(), classId);
				//	老师未打分取默认分
				if (xqLearningOutcomes2 == null) {
					stars.add(xqType.getDefaultStar());
				}else {
					stars.add(xqLearningOutcomes2.getStar());
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
