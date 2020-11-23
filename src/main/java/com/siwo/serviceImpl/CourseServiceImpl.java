package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.BriefDao;
import com.siwo.dao.CourseDao;
import com.siwo.model.Brief;
import com.siwo.model.Course;
import com.siwo.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseDao dao;
	@Autowired
	private BriefDao briefDao;
	
	@Override
	public Map<String, Object> addCourse(Course course, Brief brief) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		briefDao.addBrief(brief);
//		address.setBriefId(brief.getBriefId());
		course.setBriefId(brief.getBriefId());
		dao.addCourse(course);
//		videoDao.addVideo(address);
		map.put("code", 0);
		map.put("msg", "添加成功");
		
		return map;
	}

}
