package com.siwo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.ScoreRecipient;

@Mapper
public interface ScoreRecipientDao {
 

	@Insert("INSERT INTO `s_recipient`(`recipient_id`, `teacher_id`) VALUES (null,#{teacherId});")
	public int addScoreRecipient(Integer teacherId);
	
	@Delete("delete from s_recipient where teacher_id=#{teacherId}")
	public int deleteScoreRecipient(Integer teacherId);
	
	@Select("select * from s_recipient where teacher_id=#{teacherId}")
	public ScoreRecipient queryRecipientByTeacherId(Integer teacherId);

 
	 

}
