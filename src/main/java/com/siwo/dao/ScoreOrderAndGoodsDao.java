package com.siwo.dao;

import java.util.List;
import java.util.function.IntToDoubleFunction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreOrderAndGoods;

@Mapper
public interface ScoreOrderAndGoodsDao {
	public int addScoreOrderAndGoods(List<ScoreOrderAndGoods> params);
	
	@Select("select * from s_order_and_goods soag " + 
			"left join s_goods sg  on soag.shopping_goods_id  =sg.goods_id " + 
			"where soag.shopping_order_id=#{orderNum}")
	public List<ScoreGoods> queryByOrderNum(String orderNum);
	
	
	@Select("select * from s_order_and_goods soag " + 
			"left join s_goods sg  on soag.shopping_goods_id  =sg.goods_id " + 
			"where soag.shopping_order_id=#{orderNum}")
	public List<ScoreOrderAndGoods> queryByOrderNums(String orderNum);
}
