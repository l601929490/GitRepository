package com.siwo.service;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.siwo.model.ScoreShoppingCar;

public interface ScoreShoppingCarService {
	public Map<String, Object> addShoppingCar(Integer studentId,Integer goodsId);

	public Map<String, Object> queryShoppingCar(Integer studentId);

	public Map<String, Object> setShoppingCarNum(Integer studentId, Integer goodsId, String flag);

	public Map<String, Object> changeIsSelected(Integer shoppingCarId, Integer isSelected);

	public Map<String, Object> changeIsSelectedAll(Integer studentId, Integer isSelected);

	public Map<String, Object> queryGoodsByStudentIdAndIsSelected0(Integer studentId);
}
