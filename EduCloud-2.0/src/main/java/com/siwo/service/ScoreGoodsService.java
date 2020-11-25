package com.siwo.service;
 
import java.util.Map;

import com.siwo.model.ScoreGoods;

public interface ScoreGoodsService {
	public Map<String, Object> queryGoodsBySchoolId(Integer schoolId,Integer typeId,String name );
	public Map<String, Object> queryGoodsByStudentId(Integer studentId);
	public Map<String, Object> queryGoodsLikeByStudentId(Integer studentId);
	public Map<String, Object> queryGoodsDetailByGoodsId(Integer studentId,Integer goodsId);
	public Map<String, Object> queryGoodsByTypeId(Integer typeId,Integer studentId);
	public Map<String, Object> addScoreGoods(ScoreGoods sGoods);
	public Map<String, Object> searchGoodsByName(Integer type,String name,Integer studentId);
	public Map<String, Object> offShelfGoodsByGoodsId(Integer goodsId);
	public Map<String, Object> deleteGoodsByGoodsId(String [] goodsId);
	public Map<String, Object> updateScoreGoods(ScoreGoods sGoods);
}	
