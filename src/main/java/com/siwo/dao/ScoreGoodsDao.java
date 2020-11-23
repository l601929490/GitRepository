package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.ScoreGoods;

@Mapper
public interface ScoreGoodsDao {
	/**
	 * 根据机构查询出积分商品
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id " + 
			" left join s_goods_type sgt on sg.goods_type=sgt.type_id  "+
			"where sg.goods_school=#{schoolId}  	  group by sgt.type_id")
	public List<ScoreGoods> queryGoodsBySchoolId(Integer schoolId);
	/**
	 * 根据学生id查询出所在机构的积分商品
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id  " + 
			"left join s_goods_type sgt on sg.goods_type=sgt.type_id " + 
			"where sg.goods_school=(select school_id from student  where student_id =#{studentId} group by sgt.type_id) and  goods_display=0")
	public List<ScoreGoods> queryGoodsByStudentId(Integer studentId);
	/**
	 * 猜你喜欢商品
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg   " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id " + 
			"left join  s_goods_type sgt on sg.goods_type =sgt.type_id " + 
			"where  sg.goods_school=(select school_id from student  where student_id =#{studentId}) and    goods_display=0  " + 
			"group by sgt.type_id	" + 
			"order by sgn.goods_num_sale  desc " + 
			"limit 0,8")
	public List<ScoreGoods> queryGoodsLikeByStudentId(Integer studentId);
	/**
	 * 根据商品id查询商品详情
	 * @param goodsId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join  school sgs on sg.goods_school =sgs.school_id " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id  " + 
			"where sg.goods_id =#{goodsId} and  goods_display=0")
	public ScoreGoods queryGoodsDetailByGoodsId(Integer goodsId);
	/**
	 * 根据类型id查询商品
	 * @param typeId
	 * @return
	 */
	@ResultMap("scoreGoods")
		@Select("select * from s_goods sg " + 
				"left join s_goods_num sgn  on sg.goods_id=sgn.goods_id " + 
				"where goods_type=#{typeId} and  goods_display=0")
	public List<ScoreGoods> queryGoodsByTypeId(Integer typeId);
	/**
	 * 后台上架商品
	 * @param sGoods
	 * @return
	 */
	@Insert(" INSERT INTO `s_goods`(`goods_id`, `goods_name`, `goods_type`, `goods_score`, `goods_img`, `goods_createtime`, `goods_school`, `goods_detail`, `goods_rule`,`goods_display`) "
			+ " VALUES (null,#{goodsName}, #{goodsType}, #{goodsScore}, #{goodsImg}, #{goodsCreateTime}, #{goodsSchoolId}, #{goodsDetail}, #{goodsRule},#{goodsDisplay});" ) 
	@Options(keyColumn = "goods_id",keyProperty = "goodsId",useGeneratedKeys = true)
	public int addScoreGoods(ScoreGoods sGoods);
	/**
	 * 搜索商品 全部商品
	 * @param name
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn  on sg.goods_id=sgn.goods_id  " + 
			"where  goods_name like concat ('%',#{name},'%')  and  goods_school =(select school_id from student where student_id=#{studentId}) and  goods_display=0")
	public List<ScoreGoods> searchGoodsByName(String name,Integer studentId);
	/**
	 * 搜索商品 可兑换
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn  on sg.goods_id=sgn.goods_id  " + 
			"where goods_score <=(select remain from s_scsum where student_id=#{studentId}) and goods_school=(select school_id from student where student_id=#{studentId}) and goods_name like concat ('%',#{name},'%') and  goods_display=0")
	public List<ScoreGoods> searchGoodsOKexchange(String name,Integer studentId);
	/**
	 * 查询可兑换商品
	 * @param studentId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn  on sg.goods_id=sgn.goods_id  " + 
			"where goods_score <=(select remain from s_scsum where student_id=#{studentId})  and   goods_display=0 and goods_school=(select school_id from student where student_id=#{studentId})")
	public List<ScoreGoods> queryGoodsOKexchange(Integer studentId);
	/**
	 * 查询全部商品
	 * @param studentId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn  on sg.goods_id=sgn.goods_id  " + 
			"where goods_school=(select school_id from student where student_id=#{studentId})  and  goods_display=0")

	public List<ScoreGoods> queryGoodsAll(Integer studentId);
	/**
	 * 搜索商品 根据类型
	 * @param type
	 * @param name
	 * @param studentId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg  " + 
			"left join s_goods_num sgn  on sg.goods_id=sgn.goods_id  " + 
			"where goods_score <=(select remain from s_scsum where student_id=#{studentId}) and  goods_display=0 and goods_name  like concat('%',#{name},'%') and  goods_type =#{type}   and  goods_school in (select school_id from student where student_id=#{studentId} )")
	public List<ScoreGoods> searchGoodsByOtherTypeId(Integer type, String name, Integer studentId);
	/**
	 * 修改商品状态为下架状态
	 * @param goodsId
	 * @return
	 */
	@Update("update s_goods " + 
			"set goods_display=1 " + 
			"where goods_id =#{goodsId}  ")
	public int offShelfGoodsByGoodsId(Integer goodsId);
	/**
	 * 批量删除商品
	 */
 
	public int deleteGoodsByGoodsId(String [] goodsId);
	/**
	 * 修改商品
	 * @param sGoods
	 * @return
	 */
	public int updateScoreGoods(ScoreGoods sGoods);
	/**
	 * 查询机构下面的所有商品  
	 * @param schoolId
	 * @return
	 */
	@Select("select * from s_goods sg   " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id  " + 
			"left join s_goods_type sgt on sg.goods_type=sgt.type_id  " + 
			"where sg.goods_school in (select school_id from school where company_id=#{schoolId} ) and  goods_display=0   " + 
			"order by goods_createtime desc")
	public List<ScoreGoods> queryGoodsByCompanyId(Integer schoolId);
	/**
	 * 根据校区查询商品
	 * @param schoolId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id " + 
			"left join s_goods_type sgt on sg.goods_type=sgt.type_id  "+
			"where sg.goods_school=#{schoolId} and  goods_display=0 order by goods_createtime desc")
	public List<ScoreGoods> queryGoodsBySchoolIds(Integer schoolId);
	
	/**
	 *  条件查询 根据类别
	 * @param typeId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id " + 
			"left join s_goods_type sgt on sg.goods_type=sgt.type_id  "+
			"where sg.goods_school=#{schoolId} and   goods_type=#{typeId} and   goods_display=0 order by goods_createtime desc")
	public List<ScoreGoods> queryGoodsByTypeIds(Integer schoolId,Integer typeId);
	/**
	 *  条件查询 根据名称
	 * @param typeId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id " + 
			"left join s_goods_type sgt on sg.goods_type=sgt.type_id  "+
			"where sg.goods_school=#{schoolId} and  goods_name like concat ('%',#{name},'%') and    goods_display=0 order by goods_createtime desc")
	public List<ScoreGoods> queryGoodsByNames(Integer schoolId, String name);
	/**
	 *  条件查询 根据名称和类别查
	 * @param typeId
	 * @return
	 */
	@ResultMap("scoreGoods")
	@Select("select * from s_goods sg " + 
			"left join s_goods_num sgn on sg.goods_id=sgn.goods_id " + 
			"left join s_goods_type sgt on sg.goods_type=sgt.type_id  "+
			"where sg.goods_school=#{schoolId} and   goods_type=#{typeId} and  goods_name like concat ('%',#{name},'%') and    goods_display=0 order by goods_createtime desc")
	public List<ScoreGoods> queryGoodsByNamesAndTypes(Integer schoolId,Integer typeId, String name);
	
} 
