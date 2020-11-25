package com.siwo.service;

import java.util.List;
import java.util.Map;

import com.siwo.model.ScoreGoodsDetail;

public interface ScoreGoodsDetailService {
	public Map<String, Object> queryGoodsDetail(Integer studentId,Integer falg);

	public Map<String, Object> queryGoodsDetailBySchoolId(Integer companyId);

	public Map<String, Object> queryGoodsDetailByStudentId(Integer studentId);

	public Map<String, Object> addscoreDetail(ScoreGoodsDetail detail);
}
