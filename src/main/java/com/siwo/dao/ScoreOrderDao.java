package com.siwo.dao;

 
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.ScoreOrder;

@Mapper
public interface ScoreOrderDao {
	/**
	 * 增加订单信息
	 * @param order
	 * @return
	 */
	@Insert("INSERT INTO `s_order`(`order_id`, `studentId`, `order_num`, `goods_num`, `order_flag`, `order_score`, `order_time`, `order_address`, `order_phone`, `order_customer`, `address_time`,`school_id`,`teacher_id` ) "
			+ "VALUES (null,#{studentId} , #{orderNum}, #{goodsNum}, 1, #{orderScore}, #{orderTime}, #{orderAddress}, #{orderPhone}, #{orderCustomer}, #{addressTime},#{schoolId},#{teacherId});")
	@Options(useGeneratedKeys = true,keyColumn = "order_id",keyProperty = "orderId")
	public int addOrder(ScoreOrder order);
	/**
	 * 根据订单号查询订单信息
	 * @param orderNum
	 * @return
	 */
	@Select("select * from s_order " + 
			"where order_num=#{orderNum}")
	public ScoreOrder queryOrderByorderNum(String orderNum);
	
	/** 
	 * 查询出用户订单信息
	 */ 
	@Select("select * from s_order " + 
			"where  studentId=#{studentId} order by order_time desc")
	public List<ScoreOrder> queryOrderByStudentId(Integer studentId);
	
	
	/**
	 * 收货成功修改 状态 ，收货时间
	 * @param orderNum
	 * @return
	 */
	@Update("update s_order " + 
			"set order_flag=0,address_time=#{addressTime},teacher_id=#{teacherId} " + 
			"where order_num =#{orderNum}  ")
	public int confirmReceipt(String orderNum,Integer teacherId,Date addressTime);
	/**
	 * 查询订单已完成和待收货数量
	 * @param studetnId
	 * @return
	 */
	@Select("select * from s_order " + 
			"where  school_Id=#{schoolId} and  order_flag=#{flag} ")
	public List<ScoreOrder> getOrderNum(Integer schoolId,Integer flag);
	/**
	  *  查询已收货的商品
	 * @param studentId
	 * @return
	 */
	@Select("select * from s_order " + 
			"where  studentId=#{studentId} and order_flag=0 order by order_time desc")
	public List<ScoreOrder> queryOrderByStudentIdYSH(Integer studentId);
	/**
	  * 查询待收货的商品
	 * @param studentId
	 * @return
	 */
	@Select("select * from s_order " + 
			"where  studentId=#{studentId} and order_flag=1 order by order_time desc")
	public List<ScoreOrder> queryOrderByStudentIdDSH(Integer studentId);
	/**
	 * 查询每个机构下面校区的订单信息
	 * @param companyId
	 * @return
	 */
	@Select("select * from s_order " + 
			"where school_id =#{schoolId}  ")
	public List<ScoreOrder> queryOrderBySchoolId(Integer schoolId);
	/**
	 * 查询每个机构下面校区的订单信息  状态
	 * @param companyId
	 * @return
	 */
	@Select("select * from s_order " + 
			"where school_id =#{schoolId} and order_flag = #{flag} ")
	public List<ScoreOrder> queryGoodsByFlags(Integer schoolId, Integer flag);
	@Select("select * from s_order " + 
			"where school_id =#{schoolId}  and order_phone = #{name}")
	public List<ScoreOrder> queryGoodsByNames(Integer schoolId, String name);
	@Select("select * from s_order " + 
			"where school_id =#{schoolId} and order_flag = #{flag}  and order_phone = #{name}")
	public List<ScoreOrder> queryGoodsByNamesAndFlags(Integer schoolId, Integer flag, String name);
	 
}
