package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.SchoolDao;
import com.siwo.dao.ScoreSumDao;
import com.siwo.dao.StudentDao;
import com.siwo.model.School;
import com.siwo.model.ScoreSum;
import com.siwo.model.Student;
import com.siwo.service.ScoreGoodsSumService;
@Service
public class ScoreGoodsSumServiceImpl implements ScoreGoodsSumService {
	@Autowired
	private ScoreSumDao dao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private SchoolDao schoolDao;
	
	@Override
	public Map<String, Object> queryGoodsDetail(Integer studentId) {
		return null;
	}

	@Override
	public Map<String, Object> queryScoreSumByStudentId(Integer studentId) {
		Map<String, Object> map=new HashMap<String, Object>();
		
		 
		if (studentId==null) {
			map.put("code", 1);
			map.put("msg","studentId不能为空" );
		}else {
			ScoreSum scoreSum= dao.queryScoreSumBystudentId(studentId);
			if (scoreSum!=null) {
				if (scoreSum.getSum()==null) {
					scoreSum.setSum(0);
				}
				if (scoreSum.getConsumption()==null) {
					scoreSum.setConsumption(0);;
				}
				if (scoreSum.getRemain()==null||scoreSum.getRemain()<=0) {
					scoreSum.setRemain(0);
				}
				map.put("code", 0);
				map.put("data", scoreSum);
			}
			//如果没有没有孩子的积分总数,增加
			else {
				ScoreSum scoreSum2=new ScoreSum();
				scoreSum2.setSum(0);
				scoreSum2.setRemain(0);
				scoreSum2.setStudentId(studentId);
				scoreSum2.setConsumption(0);
				int res= dao.addScoreSumByStudentId(scoreSum2);
				if (res>0) {
					//查询出刚增加孩子的信息
					ScoreSum scoreSum3= dao.queryScoreSumBystudentId(studentId);
					map.put("code", 0);
					map.put("data", scoreSum3);
				}
				
			}
		} 
 
		return map;
	}

	@Override
	public Map<String, Object> queryScoreSumBySchoolId(Integer schoolId,String studentName) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (schoolId==null) {
			map.put("code", 1);
			map.put("msg","schoolId不能为空" );
		}else {
			List<ScoreSum> scoreSums=new ArrayList<ScoreSum>();
			if (studentName.isEmpty()) {
				//查询校区的商品
				  scoreSums= dao.queryScoreSumBySchoolId(schoolId);
			}else {
				//
				scoreSums= dao.queryScoreSumBySchoolIdsAndStudentName(schoolId,studentName);
			}
			
			
			for (ScoreSum scoreSum : scoreSums) {
				Student student=studentDao.queryById(scoreSum.getStudentId());
				scoreSum.setStudentName(student.getStudentName());
				//根据学生查询出id
				School school=schoolDao.querySchoolById(student.getSchoolId());
				scoreSum.setSchoolName(school.getSchoolName());
			}
			if (scoreSums.size()!=0) {
				map.put("code", 0);
				map.put("data",scoreSums );
			}else {
				map.put("code", 1);
				map.put("msg","暂无数据" );
			}
		}
		return map;
	}
	
}
