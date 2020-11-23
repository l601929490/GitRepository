package com.siwo.serviceImpl;

 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.DynamicConditionDao;
 
import com.siwo.model.DynamicCondition;
import com.siwo.service.DynamicConditionService;
 

@Service
public class DynamicConditionServiceImpl implements DynamicConditionService {

	@Autowired
	private DynamicConditionDao dao;
	
	@Override
	public Map<String, Object> queryDynamicConditionByCompanyId(Integer companyId) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<DynamicCondition> lists=dao.queryDynamicConditionByCompanyId(companyId);
		if (lists!=null) {
			map.put("code", 0);
			map.put("msg", "查询成功");
			map.put("data", lists);
		}else {
			map.put("code", 1);
			map.put("msg", "查询失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryDynamicConditionBySchoolId(Integer schoolId) {
		Map<String, Object> map=new HashMap<String, Object>();
		DynamicCondition lists=dao.queryDynamicConditionBySchoolId(schoolId);
		if (lists!=null) {
			map.put("code", 0);
			map.put("msg", "查询成功");
			map.put("data", lists);
		}else {
			map.put("code", 1);
			map.put("msg", "查询失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> addDynamicCondition(DynamicCondition dynamicCondition) {
		Map<String, Object> map=new HashMap<String, Object>();
		int res=dao.addDynamicCondition(dynamicCondition);
		if (res>0) {
			map.put("code", 0);
			map.put("msg","增加成功");
		}else {
			map.put("code", 1);
			map.put("msg","增加失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> changeDynamicCondition(DynamicCondition dynamicCondition) {
		Map<String, Object> map=new HashMap<String, Object>();
		int res=dao.changeDynamicCondition(dynamicCondition);
		if (res>0) {
			map.put("code", 0);
			map.put("msg","修改成功");
		}else {
			map.put("code", 1);
			map.put("msg","修改失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryDynamicConditionByStudentId(Integer studentId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (studentId==null) {
			map.put("code", 0);
			map.put("msg","studetnId不能为空");
		}else {
			DynamicCondition dynamicCondition=dao.queryDynamicConditionByStudentId(studentId);
			if (dynamicCondition!=null) {
				map.put("code", 0);
				map.put("data",dynamicCondition);
			}else {
				map.put("code", 1);
				map.put("msg","查询失败");
			}
		}

		
		return map;
	}


	 
}

