package com.siwo.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.DynamicCondition;
import com.siwo.model.UserIntegral;

@Mapper
public interface UserIntegralDao {

	/**
	 * 	添加用户积分
	 * @param integral
	 * @return
	 */
	@Insert("insert into user_integral (integral_sum,user_phone,integral_time,attendance_id,is_share) "
			+ "values (#{IntegralSum},#{userPhone},#{integralTime},#{attendanceId},#{isShare})")
	@Options(useGeneratedKeys = true,keyColumn = "integral_id",keyProperty = "integralId")
	public Integer addUserIntegral(UserIntegral integral);
	
	@Delete("delete from user_integral where attendance_id = #{attendanceId} and user_phone = #{phone}")
	public Integer deleteUserIntegralByAttendanceIdAndPhone(Integer attendanceId,String phone);
	
	/**
	 * 	查询积分规则
	 * @return
	 */
	@Select("select * from dynamic_condition where school_id = #{schoolId}")
	public DynamicCondition queryDynamicCondition(Integer schoolId);
	
	/**
	 * 	修改用户积分
	 * @param integral
	 * @param userPhone
	 * @return
	 */
	@Update("update user_integral set integral_sum = #{integral} where user_phone = #{userPhone}")
	public Integer updateUserIntegral(Integer integral,String userPhone);

	/**
	 * 	查出用户的积分情况
	 * @param userIntegralSum
	 * @return
	 */
	@Select("select sum(integral_sum) from user_integral where user_phone = #{userPhone}")
	public Integer queryUserIntegralSum(String userPhone);
	
	@Select("select sum(integral_sum) from user_integral "
			+ "where user_phone = #{phone} "
			+ "and integral_time > #{startTime} "
			+ "and integral_time < #{endTime}")
	public Integer queryUserIntegralByTime(String startTime,String endTime,String phone);
}
