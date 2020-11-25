package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siwo.dao.TeachingMaterialDao;
import com.siwo.model.TeachingMaterial;
import com.siwo.model.TeachingMaterialContent;
import com.siwo.service.TeachingMaterialService;

@Service
public class TeachingMaterialServiceImpl implements TeachingMaterialService{

	@Autowired
	private TeachingMaterialDao dao;
	
	@Override
	public Map<String, Object> addTeachingMaterial(TeachingMaterial teachingMaterial) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (teachingMaterial.getSchoolId() == null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
			return map;
		}
		
		try {
			dao.addTeachingMaterial(teachingMaterial);
			List<TeachingMaterialContent> list = teachingMaterial.getContents();
			for (TeachingMaterialContent teachingMaterialContent : list) {
				teachingMaterialContent.setTeachingMaterialId(teachingMaterial.getTeachingMaterialId());
			}
			dao.addTeachingMaterialContent(list);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "添加成功");
		return map;
	}

	@Override
	public Map<String, Object> deleteManyTeachingMaterial(String[] materialIds) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			dao.deleteManyTeachingMaterial(materialIds);
			dao.deleteContent(materialIds);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "删除失败");
			return map;
		}
		map.put("code", 0);
		map.put("msg", "删除成功");
		return map;
	}

	@Override
	public Map<String, Object> updateTeachingMaterial(TeachingMaterial teachingMaterial) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			dao.updateTeachingMaterial(teachingMaterial);
			dao.deleteContentById(teachingMaterial.getTeachingMaterialId());
			List<TeachingMaterialContent> contents = teachingMaterial.getContents();
			for (TeachingMaterialContent teachingMaterialContent : contents) {
				teachingMaterialContent.setTeachingMaterialId(teachingMaterial.getTeachingMaterialId());
			}
			dao.addTeachingMaterialContent(contents);
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
	public Map<String, Object> limitTeachingMaterials(Integer page, Integer limit,Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PageHelper.startPage(page, limit);
			List<TeachingMaterial> teachingMaterials = dao.getAllTeachingMaterials(schoolId);
			PageInfo<TeachingMaterial> pageInfo = new PageInfo<TeachingMaterial>();
			map.put("code", 0);
			map.put("data", teachingMaterials);
			map.put("pageCount", pageInfo.getTotal());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		
		return  map;
	}

	@Override
	public Map<String, Object> queryTeachingMaterialsByGradeAndSubject(Integer grade, Integer subject,
			Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<TeachingMaterial> teachingMaterials = dao.queryTeachingMaterialsByGradeAndSubject(grade, subject, schoolId);
			map.put("code", 0);
			map.put("data", teachingMaterials);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> queryTeachingMaterialByTeachingMaterialId(Integer teachingMaterialId) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TeachingMaterial material = dao.queryTeachingMaterialByTeachingMaterialId(teachingMaterialId);
			map.put("code", 0);
			map.put("data", material);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		return map;
	}

}
