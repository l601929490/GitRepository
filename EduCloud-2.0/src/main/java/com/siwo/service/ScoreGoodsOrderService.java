package com.siwo.service;

import java.util.Map;

import com.siwo.model.ScoreOrder;

public interface ScoreGoodsOrderService {
	public Map<String, Object> addOrder(ScoreOrder order);

	public Map<String, Object> queryOrderByStudentId(Integer studentId,Integer flag);

	public Map<String, Object> queryOrderByOrderNum(String orderNum);

	public Map<String, Object> queryOrderBySchoolId(Integer schoolId,String name,Integer flag);

	public Map<String, Object> confirmReceipt(String orderNum, String flag, Integer teacherId);

	public Map<String, Object> getOrderNum(Integer schoolId);

	public Map<String, Object> queryOrderYwcAndDshNum(Integer schoolId, Integer flag);
}
