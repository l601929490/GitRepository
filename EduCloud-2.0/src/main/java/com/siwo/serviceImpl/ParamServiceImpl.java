package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ParamDao;
import com.siwo.model.Param;
import com.siwo.service.ParamService;

@Service
public class ParamServiceImpl implements ParamService{

	@Autowired
 	private ParamDao dao;
	
	@Override
	public Map<String, Object> addParam(Param param) {
		Map<String, Object>map = new HashMap<String, Object>();
		
		param.setCreationTime(new Date());
		
		Integer row = dao.addParam(param);
		
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
	public Map<String, Object> addRecordParam(List<Param> params) {
		
		Map<String, Object>map = new HashMap<String, Object>();
		
		try {
			for (Param param : params) {
				param.setCreationTime(new Date());
				dao.addParam(param);
			}
			map.put("code", 0);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "添加失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteParamByParamId(Integer paramId) {
		Map<String, Object>map = new HashMap<String, Object>();
		if (paramId != null) {
			Integer row = dao.deleteParamByParamId(paramId);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> updateParam(Param param) {
		Map<String, Object>map = new HashMap<String, Object>();
		
		Integer row = dao.updateParam(param);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "修改成功");
		}else {
			map.put("code", 1);
			map.put("msg", "修改失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryParamBySchoolId(Integer schoolId, String paramType) {
		Map<String, Object>map = new HashMap<String, Object>();
		
		if (schoolId == null || StringUtils.isEmpty(paramType)) {
			map.put("code", 1);
			map.put("msg", "查询失败，参数不能为空");
			return map;
		}
		
		List<Param> params = dao.queryParamBySchoolId(schoolId, paramType);
		
		map.put("code", 0);
		map.put("data", params);
		
		return map;
	}

	@Override
	public Map<String, Object> deleteManyParam(String[] paramIds) {
		Map<String, Object>map = new HashMap<String, Object>();
		
		Integer row = dao.deleteManyParam(paramIds);
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
	public Map<String, Object> limitParam(Integer page, Integer limit, Integer schoolId,String paramType) {
		Map<String, Object>map = new HashMap<String, Object>();
		
		Integer start = (page - 1) * limit;
		
		List<Param> params = dao.limitParam(start, limit, schoolId,paramType);
		Integer count = dao.getParamCount(schoolId,paramType);
		if (params != null) {
			map.put("code", 0);
			map.put("data", params);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryParamByParamName(String paramName, Integer schoolId, String paramType) {
		Map<String, Object>map = new HashMap<String, Object>();
		
		if (schoolId != null && !StringUtils.isEmpty(paramType)) {
			paramName = "%" + paramName + "%";
			List<Param> params = dao.queryParamByParamName(paramName, schoolId, paramType);
			map.put("code",0);
			map.put("data", params);
		}
		return map;
	}

}
