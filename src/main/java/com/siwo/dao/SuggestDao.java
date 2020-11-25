package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Suggest;

@Mapper
public interface SuggestDao {

	@Insert("INSERT INTO `suggest`(`suggest_title`, `suggest_content`, `suggest_phone`,`school_id`,`creator`,`creation_time`,`status`) "
			+ "VALUES ( #{suggestTitle}, #{suggestContent}, #{suggestPhone},#{schoolId},#{creator},#{creationTime},#{status})")
	public Integer addSuggest(Suggest suggest);
	
	@Delete("delete from suggest where suggest_id = #{suggestId}")
	public Integer deleteSuggest(Integer suggestId);
	
	public Integer deleteManySuggest(String[] suggestIds);
	
	public Integer updateSuggest(Suggest suggest);
	
	@Select("select * from suggest where school_id = #{schoolId} limit #{page},#{limit}")
	public List<Suggest> limitSuggest(Integer page,Integer limit,Integer schoolId);
	
	@Select("select count(*) from suggest where school_id = #{schoolId}")
	public Integer getSuggestCount(Integer schoolId);
	
	@Update("update suggest set status = #{status} where suggest_id = #{suggestId}")
	public Integer updateSuggestStatus(Integer status,Integer suggestId);
}
