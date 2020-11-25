package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreGoodsType;

@Mapper
public interface ScoreGoodsTypeDao {
	
	@Select("select * from s_goods_type where school_id=(select school_id from student where student_id=#{studentId})")
	public List<ScoreGoodsType> queryGoodsTypeByStudentId(Integer studentId);
	@Select(" select * from s_goods_type where school_id=#{schoolId}")
	public List<ScoreGoodsType> queryGoodsTypeBySchoolId(Integer schoolId);
	@Select("select * from s_goods_type " + 
			"where type_id=#{goodsType}")
	public ScoreGoodsType queryGoodsTypeByTypeId(Integer goodsType);
	/**
	 * 增加类别
	 * @param type
	 * @return
	 */
	@Insert("INSERT INTO `s_goods_type`(`type_id`, `type_name`, `school_id`) "
			+ "VALUES (null,#{typeName}, #{schoolId});" )
	public int addGoodsType(ScoreGoodsType type);
	/**
	 * 修改类别
	 * @param type
	 * @return
	 */
	@Update("UPDATE `s_goods_type` SET `type_name` = #{typeName}, `school_id` = #{schoolId} WHERE `type_id` = #{typeId};")
	public int updateGoodsType(ScoreGoodsType type);

	/**
	 * 批量删除
	 * @param typeId
	 * @return
	 */
	public int deleteGoodsType(String[] typeId);
	/**
	 * 查询类别下面的商品
	 * @param typeId
	 * @return
	 */
	@Select("select goods_id from s_goods " + 
			"where goods_type =#{typeId}")
	public List<String> queryGoodsByType(Integer typeId);
}
