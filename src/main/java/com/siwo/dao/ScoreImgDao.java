package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.ScoreImg;

@Mapper
public interface ScoreImgDao {
	@Select("select * from s_img where goods_id=#{goodsId}")
	public List<ScoreImg> queryImgByGoodsId(Integer goodsId);
	@Insert("INSERT INTO `s_img`(`img_id`, `img_name`, `goods_id`, `img_type`)  "
			+ " VALUES (null, #{imgName}, #{goodsId}, #{imgType}); ") 
	public int addScoreGoodsImg(ScoreImg scoreImg);
	/**
	 * 删除商品图片信息
	 * @param goodsId
	 * @return
	 */
 
	public int deleteGoodsImgByGoodsId(String [] goodsId);
	
	@Delete("delete  from s_img where goods_id=#{goodsId}")
	public int deleteGoodsImgByGoodsIdOne(Integer goodsId);
	
	@Select("select * from s_img where goods_id=#{goodsId} and  img_type=#{type}")
	public List<ScoreImg> queryGoodsImgByGoodsId(Integer goodsId,Integer type);
}
