package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Activity;

@Mapper
public interface ActivityDao {

	@Insert("INSERT INTO `activity`(`activity_preview_img`, `activity_intro`,activity_creator,activity_creation_time,school_id) "
			+ "VALUES ( #{activityPreviewImg}, #{activityIntro},#{activityCreator},#{activityCreationTime},#{schoolId});")
	public Integer addActivity(Activity activity);
	
	@Insert("insert into activity_img (activity_is_figure,activity_id) "
			+ "values (#{activityIsFigure},#{activityId})")
	public Integer addActivityImg(String activityIsFigure,Integer activityId);
	
	@Select("select * from activity where school_id = #{schoolId} limit #{page},#{limit}")
	public List<Activity> limitActivitie(Integer page, Integer limit, Integer schoolId);
	
	@Select("select school_id from activity where activity_id")
	public Integer querySchoolIdByactivityId(Integer activityId);
	
	@Select("select count(*) from activity where school_id = #{schoolId}")
	public Integer getActivitieCount(Integer schoolId);
	
	@Select("select * from activity where school_id = #{schoolId} ORDER BY activity_creation_time desc")
	public List<Activity> queryActivitieBySchoolId(Integer schoolId);
	
	@ResultMap("activity_1")
	@Select("select * from activity a left join activity_img ai on a.activity_id = ai.activity_id where a.activity_id = #{activityId}")
	public Activity queryActivityByActivityId(Integer activityId);
	
	@Delete("delete from activity where activity_id = #{activityId}")
	public Integer deleteActivityByActivityId(Integer activityId);
	
	public Integer deleteManyActivity(String[] activityIds);
	
	@Delete("delete from activity_img where activity_id = #{activityId}")
	public Integer deleteActivityImgByActivityId(Integer activityId);
	
	public Integer deleteManyActivityImg(String[] activityIds);
	
	public Integer updateActivity(Activity activity);
	
	@Select("select activity_is_figure from activity_img where activity_id = #{activityId}")
	public List<String> queryActivityImgByActivityId(Integer activityId);
	
	//	活动点赞
	@Insert("insert into activity_like (activity_id,open_id) values (#{activityId},#{openId})")
	public Integer addActivityLike(Integer activityId,String openId);
	//	取消点赞
	@Delete("delete from activity_like where activity_id = #{activityId} and open_id = #{openId}")
	public Integer deleteActivityLike(Integer activityId,String openId);
	//	查询点赞数量
	@Select("select count(*) from activity_like where activity_id = #{activityId}")
	public Integer queryActivityLikeCount(Integer activityId);
	//	查询当前用户是否为活动点过赞
	@Select("select count(*) from activity_like where activity_id = #{activityId} and open_id = #{openId}")
	public Integer queryActivityLikeByOpenId(Integer activityId,String openId);
}
