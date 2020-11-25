package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.SuggestDao;
import com.siwo.model.Suggest;
import com.siwo.service.SuggestService;

@Service
public class SuggestServiceImpl implements SuggestService{

	@Autowired
	private SuggestDao dao;
	
	@Override
	public Map<String, Object> addSuggest(Suggest suggest,BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		suggest.setCreationTime(new Date());
		
		//	0  未处理
		suggest.setStatus(0);
		
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		
		Integer row = dao.addSuggest(suggest);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		}else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> deleteSuggest(Integer suggestId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Integer row = dao.deleteSuggest(suggestId);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		}else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> deleteManySuggest(String[] suggestIds) {
		Map<String, Object> map  = new HashMap<String, Object>();
		Integer row = dao.deleteManySuggest(suggestIds);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		}else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateSuggest(Suggest suggest) {
		Map<String, Object> map  = new HashMap<String, Object>();
		
		suggest.setEditSession(new Date());
		
		Integer row = dao.updateSuggest(suggest);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "更新成功");
		}else {
			map.put("code", 1);
			map.put("msg", "更新失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> limitSuggest(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<Suggest> suggests = dao.limitSuggest(start, limit, schoolId);

		int count = dao.getSuggestCount(schoolId);

		if (suggests != null) {
			map.put("code", 0);
			map.put("data", suggests);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> updateSuggestStatus(Integer status, String[] suggestId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (status == null || suggestId == null) {
			map.put("code", 1);
			map.put("msg", "参数不能为空");
			return map;
		}
		
		try {
			for (String id : suggestId) {
				dao.updateSuggestStatus(status, Integer.valueOf(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "服务器异常");
		}
		map.put("code", 0);
		map.put("msg", "修改成功");
		return map;
	}

}
