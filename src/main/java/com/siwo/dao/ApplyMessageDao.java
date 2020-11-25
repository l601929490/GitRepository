package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Apply;
import com.siwo.model.ApplyMessage;
import com.siwo.model.ApplyMessageStudent;

@Mapper
public interface ApplyMessageDao {

	@Insert("insert into apply_message (apply_message_patriarch,apply_message_phone,apply_message_time,school_id) "
			+ "values (#{applyMessagePatriarch},#{applyMessagePhone},#{applyMessageTime},#{schoolId})")
	@Options(useGeneratedKeys = true,keyColumn = "apply_message_id",keyProperty = "applyMessageId")
	public Integer addApplyMessage(ApplyMessage message);
	
	@Insert("insert into apply_and_apply_message (apply_message_id,apply_id) values (#{applyMessageId},#{applyId})")
	public Integer addApplyAndApplyMessage(Integer applyId,Integer applyMessageId);
	
	public Integer addApplyMessageStudent(List<ApplyMessageStudent> students);
	
	@Select("SELECT * from apply_message where school_id = #{schoolId} LIMIT #{page},#{limit}")
	public List<ApplyMessage> limitApplyMessage(Integer page,Integer limit,Integer schoolId);
	
	@Select("select count(*) from apply_message where school_id = #{schoolId}")
	public Integer getApplyMessageCount(Integer schoolId);
	
	@Select("select * from apply_message_info where apply_message_id = #{messageId}")
	public List<ApplyMessageStudent> queryApplyMessageStudentsByApplyMessageId(Integer messageId);
	
	//	导出报名信息
	@Select("SELECT * from apply_message am left join apply_message_info ami on am.apply_message_id = ami.apply_message_id where school_id = #{schoolId}")
//	@Select("SELECT * FROM v_apply_message")
	@ResultMap("applyMessage_2")
	public List<ApplyMessageStudent> queryApplyMessageBySchoolId(Integer schoolId);
	
	@Select("select * from apply a right join apply_and_apply_message aam on a.apply_id = aam.apply_id left join apply_subject apps on a.apply_id = apps.apply_id where aam.apply_message_id = #{messageId}")
	public Apply queryApplyByApplyMessageId(Integer messageId);
	
}
