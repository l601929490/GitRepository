package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Poster;

@Mapper
public interface PosterDao {

	@Insert("insert into poster (poster_background_image,poster_proverb,poster_create_time,poster_creator,poster_checked,school_id,code) values "
			+ "(#{posterBackgroundImage},#{posterProverb},#{posterCreateTime},#{posterCreator},#{posterChecked},#{schoolId},#{code})")
	public Integer addPoster(Poster poster);
	
	@Delete("DELETE FROM `poster` WHERE `poster_id` = #{posterId}")
	public Integer deletePoster(Integer posterId);
	
	public Integer deleteManyPoster(String[] posterIds);
	
	public Integer updatePoster(Poster poster);
	
	@Select("select * from poster where school_id = #{schoolId} and code = #{code} limit #{page},#{limit} ")
	public List<Poster> limitPosters(Integer page,Integer limit,Integer schoolId,Integer code);
	
	@Select("select count(*) from poster where school_id = #{schoolId} and code = #{code}")
	public Integer queryPosterCount(Integer schoolId,Integer code);
	
	@Select("select * from poster where school_id = #{schoolId} and poster_checked = 1 and code = #{code}")
	public Poster queryPosterIsChecked(Integer schoolId,Integer code);
	
	@Select("SELECT school_id from poster where code = #{code} GROUP BY school_id")
	public List<Integer> querySchoolId(Integer code);
	
	@Update("UPDATE poster SET poster_checked = 0 where school_id = #{schoolId} and code = #{code}")
	public boolean updatePosterChecked(Integer schoolId,Integer code);
	
	@Select("SELECT * from poster where poster_checked = 1 where school_id = #{schoolId} and code = #{code}")
	public Poster queryPosterByPosterChecked(Integer schoolId,Integer code);
	
	@Select("select * from poster where school_id = #{schoolId} and code = #{code}")
	public List<Poster> queryPosterBySchoolId(Integer schoolId,Integer code);
}
