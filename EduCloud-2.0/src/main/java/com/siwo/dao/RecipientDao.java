package com.siwo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.ScoreRecipient;
 

@Mapper
public interface RecipientDao {
	@Select("select * from s_recipient " + 
			"where teacher_id =#{teacherId}")
	public ScoreRecipient queryRecipient(Integer teacherId);
	 
}
