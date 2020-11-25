package com.siwo.service;

import java.util.List;
import java.util.Map;

import com.siwo.model.ScoreGoodsDetail;

public interface ScoreGoodsSumService {
	public Map<String, Object> queryGoodsDetail(Integer studentId);

	public Map<String, Object> queryScoreSumByStudentId(Integer studentId);

	public Map<String, Object> queryScoreSumBySchoolId(Integer schoolId,String studentName);
}
