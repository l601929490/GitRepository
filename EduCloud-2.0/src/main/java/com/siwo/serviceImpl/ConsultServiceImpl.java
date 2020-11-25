package com.siwo.serviceImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ConsultDao;
import com.siwo.model.Consult;
import com.siwo.service.ConsultService;

@Service
public class ConsultServiceImpl implements ConsultService {

	@Autowired
	private ConsultDao dao;

	@Override
	public Map<String, Object> addConsult(Consult consult) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		consult.setCreationTime(new Date());
		
		dao.addConsult(consult);
		map.put("code", 0);
		map.put("msg", "添加完成");
		return map;
	}

	@Override
	public Map<String, Object> queryAllConsult(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Consult> consults = dao.queryAllConsult(schoolId);

		Collections.sort(consults, new Comparator<Consult>() {

			@Override
			public int compare(Consult o1, Consult o2) {

				Date date = o1.getTime();
				Date date2 = o2.getTime();
				return (int) (date2.getTime() - date.getTime());

			}
		});

		if (consults != null) {
			map.put("code", 0);
			map.put("data", consults);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateConsult(Consult consult) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		consult.setEditSession(new Date());
		
		if (consult != null) {
			int row = dao.updateConsult(consult);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "更新成功");
			} else {
				map.put("code", 1);
				map.put("msg", "更新失败");
			}
		} 
		
		return map;
	}

	@Override
	public Map<String, Object> deleteConsult(String consultId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (consultId != null) {
			int row = dao.deleteConsult(consultId);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			} else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		} else {
			map.put("code", 1);
			map.put("msg", "咨询id不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteManyConsult(String[] consultIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (consultIds != null) {
			Integer row = dao.deleteManyConsult(consultIds);
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
	public Map<String, Object> queryConsultById(Integer consultId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (consultId != null) {
			Consult consult = dao.queryConsultById(consultId);
			if (consult != null) {
				Integer read = consult.getReadConsult();
				read = read + 1;
				// 当查询咨询详情页时，增加阅读量
				dao.updateReadConsult(read, consultId);
			}
			map.put("code", 0);
			map.put("data", consult);
		} else {
			map.put("code", 0);
			map.put("msg", "咨询id不能空");
		}
		return map;
	}

	@Override
	public Map<String, Object> limitConsults(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer start = (page - 1) * limit;
		List<Consult> consults = dao.limitConsults(start, limit, schoolId);
		Integer count = dao.getConsultCount(schoolId);
		if (consults != null) {
			map.put("code", 0);
			map.put("data", consults);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> fuzzyQueryConsults(String param, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (param == null && schoolId == null) {
			map.put("code", 1);
			map.put("msg", "搜索内容和学校Id不能为空");
		}

		param = "%" + param + "%";

		List<Consult> consults = dao.fuzzyQueryConsults(param, schoolId);
		map.put("code", 0);
		map.put("data", consults);

		return map;
	}

}
