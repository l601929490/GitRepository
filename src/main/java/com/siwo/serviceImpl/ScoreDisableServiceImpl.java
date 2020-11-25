package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ScoreDisableDao;
import com.siwo.model.ScoreDisable;
import com.siwo.service.ScoreDisableService;

@Service
public class ScoreDisableServiceImpl implements ScoreDisableService {
	@Autowired
	private ScoreDisableDao dao;
	
	@Override
	public Map<String, Object> insertScoreDisable(ScoreDisable scoreDisable) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (scoreDisable!=null) {
			ScoreDisable dis= dao.queryScoreDisable(scoreDisable.getDisableSchoolId());
			if (dis==null) {
				int res= dao.insertScoreDisable(scoreDisable);
				if (res>0) {
					map.put("code",0);
					map.put("msg", "添加成功");
				}else {
					map.put("code",1);
					map.put("msg", "添加失败");
				}
			}else {
				map.put("code",1);
				map.put("msg", "添加失败");
			}
		
		}
		// TODO Auto-generated method stub
		return map;
	}

	@Override
	public Map<String, Object> queryScoreDisable(Integer schoolId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (schoolId==null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
		}else {
		    ScoreDisable disable=dao.queryScoreDisable(schoolId);
		    if (disable!=null) {
		    	map.put("code", 0);
				map.put("data",disable );
			}else {
				map.put("code", 0);
				map.put("msg","暂无数据");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> updateScoreDisable(ScoreDisable scoreDisable) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		if (scoreDisable!=null) {
			ScoreDisable dis= dao.queryScoreDisable(scoreDisable.getDisableSchoolId());
			if (dis!=null) {
				int res= dao.updateScoreDisable(scoreDisable);
				if (res>0) {
					map.put("code",0);
					map.put("msg", "修改成功");
				}else {
					map.put("code",1);
					map.put("msg", "修改失败");
				}
			}else {
				map.put("code",1);
				map.put("msg", "修改失败");
			}
		
		}
		// TODO Auto-generated method stub
		return map;
	}
	
}
