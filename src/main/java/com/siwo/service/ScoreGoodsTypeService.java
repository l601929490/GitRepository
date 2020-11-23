package com.siwo.service;

import java.util.Map;

import com.fasterxml.jackson.core.sym.Name;
import com.siwo.model.ScoreGoodsType;

public interface ScoreGoodsTypeService {
	public Map<String, Object> queryGoodsTypeByStudentId(Integer studentId);

	public Map<String, Object> queryGoodsTypeBySchoolId(Integer schoolId);

	public Map<String, Object> addGoodsType(ScoreGoodsType type);

	public Map<String, Object> updateGoodsType(ScoreGoodsType type);

	public Map<String, Object> deleteGoodsType(String [] typeId);
}
