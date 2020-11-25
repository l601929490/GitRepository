package com.siwo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.ScoreDisable;

@Mapper
public interface ScoreDisableDao {
	@Select("select * from s_disable " + 
			"where disable_school_id=#{schoolId}")
	public ScoreDisable queryScoreDisable(Integer schoolId);
	
	@Insert("INSERT INTO  `s_disable`(`disable_id`, `disable_school_id`, `disable_flag`) "
			+ " VALUES(null, #{disableSchoolId}, #{disableFlag})")
	public int insertScoreDisable(ScoreDisable scoreDisable);
	@Update("update s_disable set disable_flag=#{disableFlag}  where disable_school_id=#{disableSchoolId}")
	public int updateScoreDisable(ScoreDisable scoreDisable);
	
}
