package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Apply;
import com.siwo.model.ApplyMessageStudent;

@Mapper
public interface ApplyDao {

	//	添加
	@Insert("insert into apply (apply_title,apply_slideshow,apply_img,apply_show,apply_creator,apply_creation_time,school_id,apply_start_time,apply_stop_time,apply_address,apply_principal,apply_phone) "
			+ "values (#{applyTitle},#{applySlideshow},#{applyImg},#{applyShow},#{applyCreator},#{applyCreationTime},#{schoolId},#{applyStartTime},#{applyStopTime},#{applyAddress},#{applyPrincipal},#{applyPhone}) ")
	@Options(useGeneratedKeys = true,keyColumn = "apply_id",keyProperty = "applyId")
	public Integer addApply(Apply apply);
	
	//	添加科目
	@Insert("insert into apply_subject (apply_id,subject) values (#{applyId},#{subject})")
	public Integer addApplySubject(Integer applyId,String subject) ;
	
	//	分页
	@Select("select * from apply a left join apply_subject asb on a.apply_id = asb.apply_id where school_id = #{schoolId} limit #{page},#{limit}")
	@ResultMap("apply_1")
	public List<Apply> limitApply(Integer page,Integer limit,Integer schoolId);
	
	//	获取总数量
	@Select("select count(*) from apply where school_id = #{schoolId}")
	public Integer getApplyCount(Integer schoolId);
	
	//	查学科
	@Select("select subject from apply_subject where apply_id = #{applyId}")
	public List<String> queryApplySubject(Integer applyId);
	
	//	小程序展示的数据
	@Select("select * from apply a left join apply_subject asb on a.apply_id = asb.apply_id where apply_show = 1 and school_id = #{schoolId}")
	@ResultMap("apply_1")
	public List<Apply> queryApplyBySchoolIdAndApplyShow(Integer schoolId);
	
	//	修改报名活动信息
	public Integer updateApply(Apply apply);
	
	//	修改报名活动的课程类型
	@Update("delete from apply_subject where apply_id = #{applyId}")
	public Integer deleteApplySubject(Integer applyId);
	
	//	删除报名活动
	public Integer deleteApply(String[] applyIds);
	
	//	模糊查询报名活动
	@Select("select * from apply a left join apply_subject asb on a.apply_id = asb.apply_id where school_id = #{schoolId} and apply_title like #{title}")
	@ResultMap("apply_1")
	public List<Apply> queryApplyByApplyTitle(String title,Integer schoolId);
}
