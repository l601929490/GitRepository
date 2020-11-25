package com.siwo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Client;

@Mapper
public interface ClientDao {
	
	/**
	 * 	按照openId查用户信息
	 * @param openId
	 * @return
	 */
	@Select("select * from user where open_Id = #{openId}")
	public Client queryUserByOpenId(String openId);
	
	/**
	 * 	根据手机号查opneId
	 * @param phone
	 * @param companyId
	 * @return
	 */
	@Select("select open_id from user where user_phone = #{phone} and company_id = #{companyId}")
	public String queryOpenIdByPhone(String phone,Integer companyId);
	
	/**
	 * 	查找整个机构的用户
	 * @param phone
	 * @param companyId
	 * @return
	 */
	@Select("select * from user where company_id = #{companyId}")
	public List<Client> queryByCompanyId(Integer companyId);
	
	/**
	 * 	添加微信用户信息
	 * @param request
	 * @return
	 */
	@Insert("insert into user (encrypted_data,iv,open_id,raw_data,session_key,login_time) values "
			+ "(#{encryptedData},#{iv},#{openId},#{rawData},#{sessionKey},#{loginTime})")
	public Integer addUser(Client request);
	
	/**	
	 * 	按openid修改用户电话
	 * @param userPhone
	 * @param openId
	 * @return
	 */
	@Update("update user set user_phone = #{userPhone},company_id = #{companyId} where open_id = #{openId}")
	public Integer updateUserPhone(String userPhone,String openId,Integer companyId);
	
	/**
	 * 	修改最后一次登录时间
	 * @param loginTime
	 * @param openId
	 * @param companyId
	 * @return
	 */
	@Update("update user set login_time = #{loginTime} where open_id = #{openId}")
	public Integer updateLoginTime(Date loginTime,String openId);
	
	/**
	 * 	按openid修改用户身份
	 * @param identity
	 * @param openId
	 * @return
	 */
	@Update("update user set identity = #{identity} where open_id = #{openId}")
	public Integer updateUseridentity(String identity,String openId);
	
	/**
	 * 	根据openId修改数据信息
	 * @param encryptedData
	 * @param openId
	 * @return
	 */
	@Update("update user set raw_data = #{rawData} where open_id = #{openId}")
	public Integer updateRawData(String rawData,String openId);
	
	@Update("update user set session_key = #{sessionKey} where open_id = #{openId}")
	public Integer updateSessionKey(String sessionKey,String openId);
	
	/**
	 * 	根据手机号查询用户微信信息
	 * @param phone
	 * @return
	 */
	@Select("select raw_data from user where user_phone = #{phone}")
	public List<String> queryUserInfoByPhone(String phone);
	
	//	删除用户信息
	@Delete("delete from user where user_phone = #{phone}")
	public Integer deleteUserByPhone(String phone);
	
	//	查找成交用户
//	@ResultMap("getClient")
	@Select("select * from user where company_id = 14 and open_id in (SELECT open_id from user_id where user_identity in (1,3))")
	public List<Client> limitClientsAccomplish (Integer companyId);
	
//	@Select("select count(*) from user u left join user_id ui on u.open_id = ui.open_id where company_id = #{companyId} and ui.user_identity in (1,3)")
//	public Integer getClientCountAccomplish(Integer companyId);
	
	//	未成交用户
	@Select("select * from user where company_id = 14 and open_id in (SELECT open_id from user_id where user_identity = 4) limit #{page},#{limit}")
	public List<Client> limitClientsFail(Integer companyId,Integer page,Integer limit);
	
	@Select("select count(*) from user where company_id = 14 and open_id in (SELECT open_id from user_id where user_identity = 4)")
	public Integer getClientCountFail(Integer companyId);
	
	//	全机构用户
//	@ResultMap("getClient")
	@Select("select * from user where company_id = 14 and open_id in (SELECT open_id from user_id where user_identity in (1,3,4)) limit #{page},#{limit}")
	public List<Client> limitClients(Integer companyId,Integer page,Integer limit);
	
	@Select("select count(*) from user where company_id = 14 and open_id in (SELECT open_id from user_id where user_identity in (1,3,4))")
	public Integer getClientCount(Integer companyId);
	
	//	删除用户所有身份
	@Select("delete from user_id where open_id = #{openId}")
	public Integer deleteUserIdentity(String openId);
	
	//	添加用户身份
	@Insert("INSERT INTO `user_id`(`open_id`, `user_identity`) VALUES (#{openId}, #{identity})")
	public Integer addUserIdentity(String openId,Integer identity);
	
	//	查询用户身份
	@Select("select user_identity from user_id where open_id = #{openId} and user_identity != 2 order by user_identity asc")
	public List<Integer> queryUserIdentity(String openId);
	
	@Insert("UPDATE `user` SET `last_login_time` = #{lastTime} WHERE `open_id` = #{openId}")
	public Integer updateLastLoginTime(String openId,Date lastTime);
	
}
