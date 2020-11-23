package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ApplyDao;
import com.siwo.dao.ClientDao;
import com.siwo.model.Apply;
import com.siwo.model.Client;
import com.siwo.service.ApplyService;

@Service
public class ApplyServiceImpl implements ApplyService{

	@Autowired
	private ApplyDao dao;
	@Autowired
	private ClientDao clientDao;
	
	@Override
	public Map<String, Object> addApply(Apply apply) {
		
		Map<String, Object>map = new HashMap<String, Object>();
		
		apply.setApplyCreationTime(new Date());
		
		dao.addApply(apply);
		dao.addApplySubject(apply.getApplyId(), apply.getSubject());
		
		map.put("code", 0);
		map.put("msg", "添加成功");
		return map;
	}

	@Override
	public Map<String, Object> limitApply(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<Apply> applies = dao.limitApply(start, limit,schoolId);
		
		int count = dao.getApplyCount(schoolId);

		if (applies != null) {
			map.put("code", 0);
			map.put("data", applies);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> queryApplyBySchoolIdAndApplyShow(Integer schoolId,String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Apply> apply = dao.queryApplyBySchoolIdAndApplyShow(schoolId);
		
		try {
			if (apply != null && apply.size() != 0) {
				map.put("code", 0);
				map.put("data", apply);
			}
			if (!StringUtils.isBlank(openId)) {
				Client client = clientDao.queryUserByOpenId(openId);
				map.put("rawData", client.getRawData());
			}
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常，请稍后再试");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> updateApply(Apply apply) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Integer row = dao.updateApply(apply);
			if (row>0) {
				dao.deleteApplySubject(apply.getApplyId());
				List<String> subList = apply.getSubjects();
				for (String string : subList) {
					dao.addApplySubject(apply.getApplyId(), string);
				}
			}
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "更新失败");
			e.printStackTrace();
			return map;
		}
		map.put("code", 0);
		map.put("msg", "更新成功");
		return map;
	}

	@Override
	public Map<String, Object> deleteApply(String[] applyIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			dao.deleteApply(applyIds);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "删除失败");
			e.printStackTrace();
			return map;
		}
		map.put("code", 0);
		map.put("msg", "删除成功");
		return map;
	}

	@Override
	public Map<String, Object> queryApplyByApplyTitle(String title, Integer schoolId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			title = "%" + title + "%";
			List<Apply> applies = dao.queryApplyByApplyTitle(title, schoolId);
			map.put("code", 0);
			map.put("data", applies);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			e.printStackTrace();
			return map;
		}
		return map;
	}

}
