package com.siwo.serviceImpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.SchoolDao;
import com.siwo.dao.ScoreGoodsDetailDao;
import com.siwo.dao.StudentDao;
import com.siwo.model.School;
import com.siwo.model.ScoreGoodsDetail;
import com.siwo.model.Student;
import com.siwo.model.YearMonth;
import com.siwo.service.ScoreGoodsDetailService;

@Service
public class ScoreGoodsDetailServiceImpl implements ScoreGoodsDetailService {

	@Autowired
	private ScoreGoodsDetailDao dao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private SchoolDao schoolDao;

	@Override
	public Map<String, Object> queryGoodsDetail(Integer studentId,Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> deList = new ArrayList<Object>();
		List<ScoreGoodsDetail> scoreGoodsDetails =dao.queryDetailByStudentId(studentId);
		List<YearMonth> yearMonth=new ArrayList<YearMonth>();
		for (ScoreGoodsDetail scoreGoodsDetail : scoreGoodsDetails) {
			Date createTime=scoreGoodsDetail.getDetailCreateTime();
		    yearMonth = dao.queryYearMonth(studentId);	
		}
		
		
		List<ScoreGoodsDetail> details = null;
		for (YearMonth yearMonth2 : yearMonth) {
			
			if (flag==0) {
				//全部
				details = dao.queryScoreGoodsDetailByYM(yearMonth2.getYear(), yearMonth2.getMonth(), studentId);
			}else if (flag==1) {
				//获取
				details = dao.queryScoreGoodsDetailByHQ(yearMonth2.getYear(), yearMonth2.getMonth(), studentId);
			}else if (flag==2) {
				//消费
				details = dao.queryScoreGoodsDetailByXF(yearMonth2.getYear(), yearMonth2.getMonth(), studentId);
			}
			
			
			Integer sumScore= dao.queryScoreByStudentId(yearMonth2.getYear(), yearMonth2.getMonth(), studentId,0);//获取的积分
			Integer useScore= dao.queryScoreByStudentId(yearMonth2.getYear(), yearMonth2.getMonth(), studentId,1);//消耗的积分
				if (details.size()==0) {
					
				}else {
					 deList.add(details);
				}
			
			
		}
		if (deList.size() != 0) {
			map.put("code", 0);
			map.put("data", deList);
			
			map.put("msg", "查询成功");
		} else {
			map.put("code", 1);
		}
		return map;
	}

	@Override
	public Map<String, Object> queryGoodsDetailBySchoolId(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId==null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
		}else {
			List<ScoreGoodsDetail> scoreGoodsDetails=dao.queryGoodsDetailBySchoolId(schoolId);
			for (ScoreGoodsDetail scoreGoodsDetail : scoreGoodsDetails) {
				Student student=studentDao.queryStudentById(scoreGoodsDetail.getStudentId());
				School school=schoolDao.querySchoolById(student.getSchoolId());
				scoreGoodsDetail.setSchoolName(school.getSchoolName());
				scoreGoodsDetail.setSchoolId(student.getSchoolId());
			}
			if (scoreGoodsDetails!=null) {
				map.put("code", 0);
				map.put("data",scoreGoodsDetails );
			}else {
				map.put("code", 1);
				map.put("msg","暂无数据" );
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> queryGoodsDetailByStudentId(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId==null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
		}else {
			List<ScoreGoodsDetail> scoreGoodsDetails=null;
				//全部
				scoreGoodsDetails=dao.queryDetailByStudentId(studentId);
			
			for (ScoreGoodsDetail scoreGoodsDetail : scoreGoodsDetails) {
				Student student=studentDao.queryStudentById(scoreGoodsDetail.getStudentId());
				scoreGoodsDetail.setStudentName(student.getStudentName());
				School school=schoolDao.querySchoolById(student.getSchoolId());
				scoreGoodsDetail.setSchoolName(school.getSchoolName());
				
			}
			if (scoreGoodsDetails!=null) {
				map.put("code", 0);
				map.put("data",scoreGoodsDetails );
			}else {
				map.put("code", 1);
				map.put("msg","暂无数据" );
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> addscoreDetail(ScoreGoodsDetail detail) {
		detail.setDetailCreateTime(new Date());
		int res= dao.addScoreGoodsDetail(detail); 
		 
		return null;
	}

}
