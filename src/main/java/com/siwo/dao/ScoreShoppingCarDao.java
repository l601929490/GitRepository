package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.ScoreShoppingCar;


@Mapper
public interface ScoreShoppingCarDao {
	/**
	 *   商品加入购物车
	 * @param shoppingCar
	 * @return
	 */
	@Insert("INSERT INTO `s_shoppingcar`(`shopping_car_id`, `student_id`, `goods_id`, `shopping_num`)"
			+ " VALUES (null, #{studentId}, #{goodsId}, 1);" )
	public int addShoppingCar(Integer studentId,Integer goodsId);
	/**
	 * 查询每个学生的购物车商品
	 * @param studentId
	 * @return
	 */
	@ResultMap("getShoppingCar")
	@Select("select * from s_shoppingcar  ss " + 
			"left join s_goods sg on ss.goods_id=sg.goods_id " + 
			"inner join s_goods_num sgn on ss.goods_id=sgn.goods_id " + 
			"where ss.student_id=#{studentId} ")
	public List<ScoreShoppingCar> queryShoppingCar(Integer studentId);
//	/**暂时不用
//	 * 查询每个学生的购物车商品  只查询选中的商品信息
//	 * @param studentId
//	 * @return
//	 */
//	@ResultMap("getShoppingCar")
//	@Select("select * from s_shoppingcar  ss " + 
//			"left join s_goods sg on ss.goods_id=sg.goods_id " + 
//			"where ss.student_id=#{studentId} and isSelected=0 and   ")
//	public List<ScoreShoppingCar> queryIsSelected0ShoppingCar(Integer studentId);
	/**
	 *根据学生和商品查询购物车商品 
	 */
	@ResultMap("getShoppingCar")
	@Select("select * from s_shoppingcar  ss " + 
			"left join s_goods sg on ss.goods_id=sg.goods_id " + 
			"where ss.student_id=#{studentId} and ss.goods_id=#{goodsId}")
	public ScoreShoppingCar queryShoppingCarAndGoodsId(Integer studentId,Integer goodsId); 
	/**
	 * 购物车数量加减
	 */
	@Update("update s_shoppingcar " + 
			"set shopping_num=#{shoppingNum} " + 
			"where shopping_car_id=#{shoppingCarId}")
	public int changeShoppingNum(int shoppingCarId,int shoppingNum);
	/**
	 * 购物车商品数量==1 再-数量 则 删除商品
	 * @param shoppingCarId
	 * @return
	 */
	@Delete("delete from s_shoppingcar where shopping_car_id=#{shoppingCarId}")
	public int deleteShoppingCarByShoppingCar(Integer shoppingCarId);
	/**
	 * 改变单个商品选中状态 
	 */ 
	@Update("update s_shoppingcar " + 
			"set isSelected=#{isSelected} " + 
			"where shopping_car_id=#{shoppingCarId} ")
	public int changeIsSelected(Integer shoppingCarId,Integer isSelected);
	/**
	  *  查询购物车商品状态 是否选中？？
	 * @param shoppingCarId
	 * @param studentId
	 * @return
	 */
	@Select("select isSelected from s_shoppingcar " + 
			"where shopping_car_id=#{shoppingCarId}  and  student_id=#{studentId}")
	public int selectIsSelectByShoppingCarIdAndStudentId(Integer shoppingCarId, Integer studentId);
	/**
	 * 改变全部商品选中状态
	 */
	@Update("update s_shoppingcar " + 
			"set isSelected=#{isSelected} " + 
			"where student_id=#{studentId}")
	public int changeIsSelectedAll(Integer studentId, Integer isSelected);
	/**
	 * 查看用户所有的选中商品
	 * @param studentId
	 * @return
	 */
	@ResultMap("getShoppingCar")
	@Select("select * from s_shoppingcar ss  " + 
			"left join s_goods sg on ss.goods_id=sg.goods_id  " + 
			"left join s_goods_num sgn on ss.goods_id=sgn.goods_id "+
			"where ss.student_id=#{studentId} and isSelected=0 and sgn.goods_num_remain>0")
	public List<ScoreShoppingCar> queryGoodsByStudentIdAndIsSelected0(Integer studentId);
	/**
	 * 兑换完成商品清空购物车 选中商品
	 */
	@Delete("delete from  s_shoppingcar " + 
			"where student_id=#{studentId} and isSelected=0")
	public int deleteShoppingCarByStudentId(Integer studentId);
	/**
	 * 修改购物车数量
	 * @param shoppingCarId
	 */
	@Update("update s_shoppingcar " + 
			"set shopping_num=#{shoppingNum} " + 
			"where shopping_car_id=#{shoppingCarId} ")
	public void updateShoppingNumByShoppingCarId(Integer shoppingCarId,Integer shoppingNum);
}
