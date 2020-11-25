package com.siwo.serviceImpl;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siwo.dao.StudentDao;
import com.siwo.dao.XqAllocationDao;
import com.siwo.model.Student;
import com.siwo.model.XqClassDay;
import com.siwo.model.XqMonthly;
import com.siwo.model.XqType;
import com.siwo.service.XqAllocationService;

@Transactional
@Service
public class XqAllocationServiceImpl implements XqAllocationService {

	@Autowired
	private XqAllocationDao dao;

	@Autowired
	private StudentDao stuDao;

	// 课时
	@Override
	public Map<String, Object> addXqClassDay(List<XqClassDay> classDays) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (classDays == null || classDays.size() == 0) {

			map.put("code", 0);
			map.put("msg", "参数不能为空");

			return map;
		}

		for (int i = 0; i < classDays.size(); i++) {
			dao.deleteXqClassDayByClassIds(classDays.get(i).getClassId());
			if (classDays.get(i).getClassDate() == null) {
				classDays.remove(i);
			}
		}

		try {
			if (classDays.size() > 0) {
				dao.addXqClassDay(classDays);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "操作成功");
		return map;
	}

	@Override
	public Map<String, Object> addXqClassWeek(Date begin,Date end) {
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar start = new GregorianCalendar();
		Calendar stop = new GregorianCalendar();
		start.setTime(begin);
		stop.setTime(end);
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] weeks = dfs.getWeekdays();
		int count = 1;
		while (begin.before(end)) {
//			System.out.println("第" + count + "周       日期：" + new Date(start.getTimeInMillis()) + "       " + weeks[start.get(Calendar.DAY_OF_WEEK)]);

			if (start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				System.out.println("第" + count + "周");
				count++;
			}
			start.add(Calendar.DAY_OF_YEAR, 1);
		}
		return null;
	}
	
	@Override
	public Map<String, Object> deleteManyXqClassDay(String[] xqClassdayIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.deleteManyXqClassDay(xqClassdayIds);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "删除失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "删除成功");
		return map;
	}

	@Override
	public Map<String, Object> updateXqClassDay(XqClassDay xqClassDay) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.updateXqClassDay(xqClassDay);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "更新失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "更新成功");
		return map;
	}

	@Override
	public Map<String, Object> queryXqClassDaysBySchoolId(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<XqClassDay> xqClassDays = dao.queryXqClassDaysBySchoolId(schoolId);
			map.put("code", 0);
			map.put("data", xqClassDays);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> queryXqClassDaysByClassId(Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<XqClassDay> xqClassDays = dao.queryXqClassDaysByClassId(classId);
			Collections.sort(xqClassDays, new Comparator<XqClassDay>() {

				@Override
				public int compare(XqClassDay o1, XqClassDay o2) {
					Long o1Time = o1.getClassDate().getTime();
					Long o2Time = o2.getClassDate().getTime();
					return (int) (o1Time - o2Time);
				}
			});
			map.put("code", 0);
			map.put("data", xqClassDays);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		return map;
	}

	// 学情类别
	@Override
	public Map<String, Object> addXqType(XqType xqType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.addXqType(xqType);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "添加失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "添加成功");
		return map;
	}

	@Override
	public Map<String, Object> deleteManyXqType(String[] xqTypeIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.deleteManyXqType(xqTypeIds);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "删除失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "删除成功");
		return map;
	}

	@Override
	public Map<String, Object> updateXqType(XqType xqType) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.updateXqType(xqType);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "更新失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "更新成功");
		return map;
	}

	@Override
	public Map<String, Object> queryXqTypes(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<XqType> xqTypes = dao.queryXqTypes(schoolId);
			map.put("code", 0);
			map.put("data", xqTypes);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		return map;
	}

	//	月度点评
	@Override
	public Map<String, Object> addXqMonthly(XqMonthly xqMonthly) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isBlank(xqMonthly.getRemark())) {
				map.put("code", 1);
				map.put("msg", "评语不能为空");
				return map;
			}
			XqMonthly xqMonthly2 = dao.queryXqMonthlyByStudentIdAndMonth(xqMonthly.getStudentId(), xqMonthly.getMonth(), xqMonthly.getClassId());
			if (xqMonthly2 == null) {
				dao.addXqMonthly(xqMonthly);
			}
			map.put("code", 0);
			map.put("msg", "点评成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> updateXqMonthly(XqMonthly xqMonthly) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.updateXqMonthly(xqMonthly);
			map.put("code", 0);
			map.put("msg", "更新成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteXqMonthly(Integer xqMonthlyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.deleteXqMonthly(xqMonthlyId);
			map.put("code", 0);
			map.put("msg", "删除成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> queryStudentAndXqMonthly(Integer classId, Integer schoolId, String month) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			Date date = format.parse(month);
			List<Student> students = stuDao.queryStudentByClassId(classId);
			for (Student student : students) {
				XqMonthly xqMonthly = dao.queryXqMonthlyByStudentIdAndMonth(student.getStudentId(), date,classId);
				if (xqMonthly == null) {
					student.setIsMark(0);
				}else if (xqMonthly != null) {
					student.setIsMark(1);
				}
				student.setXqMonthly(xqMonthly);
			}
			map.put("code", 0);
			map.put("data", students);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("data", "服务器异常");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> queryXqMonthly(Integer studentId, Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<XqMonthly> xqMonthlies = dao.queryXqMonthlyByStudentId(studentId,classId);
			XqMonthly xqMonthly_1 = null;
			Long difference = 0l;
			if (xqMonthlies.size() != 0) {
				xqMonthly_1 = xqMonthlies.get(0);
				difference = System.currentTimeMillis() - xqMonthlies.get(0).getMonth().getTime();
			}
			
			for (XqMonthly xqMonthly : xqMonthlies) {
				Long difference_2 = System.currentTimeMillis() - xqMonthly.getMonth().getTime();
				if (difference_2 < 0) {
					continue;
				}
				if (difference_2 < difference) {
					xqMonthly_1 = xqMonthly;
					difference = difference_2;
				}
			}
			map.put("code", 0);
			map.put("data", xqMonthly_1);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("data", "服务器异常");
			e.printStackTrace();
			return map;
		}
		return map;
	}
	
}
