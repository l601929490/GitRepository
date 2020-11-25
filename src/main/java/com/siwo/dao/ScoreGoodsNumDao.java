package com.siwo.dao;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.ScoreGoodsNum;

@Mapper
public interface ScoreGoodsNumDao {
	 
	/**
	 * 后台上架商品
	 * @param sGoods
	 * @return
	 */
	@Insert("INSERT INTO `s_goods_num`(`goods_num_id`, `goods_num_sum`, `goods_num_sale`,  `goods_num_remain`,`goods_num_basic_sale`, `goods_id`) "
			+ " VALUES (null, #{goodsNumSum}, 0, #{goodsNumRemain}, #{goodsNumBasicSale},#{goodsId});")
	public int addScoreGoodsNum(ScoreGoodsNum sGoodsNum);
	/**
	 *  修改商品数量
	 * @param shoppingGoodsId
	 * @param shoppingGoodsNum
	 */
	@Update("UPDATE  `s_goods_num` " + 
			"SET  `goods_num_sale` = goods_num_sale+#{shoppingGoodsNum} , `goods_num_remain` = goods_num_remain-#{shoppingGoodsNum} " + 
			"where `goods_id` = #{shoppingGoodsId}")
	public void updateGoodsNum(Integer shoppingGoodsId, Integer shoppingGoodsNum);
	/**
	 * 删除商品详情数据
	 * @param goodsId
	 * @return
	 */
 
	public int deleteGoodsNumByGoodsId(String[] goodsId);
	
	/**
	 * 查询商品数量
	 * 
	 */
	@Select("select * from s_goods_num where goods_id=#{goodsId} ")
	public ScoreGoodsNum queryGoodsNumByGoodsId(Integer goodsId);
} 
