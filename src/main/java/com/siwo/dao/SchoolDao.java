package com.siwo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.School;
import com.siwo.model.SchoolHonor;
import com.siwo.model.SchoolSlideshow;

@Mapper
public interface SchoolDao {

	//	校园
	@Insert("INSERT INTO `school`( `school_name`, `school_phone`, `school_principal`, `school_principal_phone`, `school_create_time`, `school_end_time`, `school_address`, `school_longitude`, `school_latitude`, `school_type`, `school_synopsis`, `company_id`, `admin_id`, `creator`, `creation_time`) "
			+ "VALUES ( #{schoolName}, #{schoolPhone}, #{schoolPrincipal}, #{schoolPrincipalPhone}, #{schoolCreateTime}, #{schoolEndTime}, #{schoolAddress}, #{schoolLongitude}, #{schoolLatitude}, #{schoolType}, #{schoolSynopsis}, #{companyId}, #{adminId}, #{creator}, #{creationTime});")
	public Integer addSchool(School school);
	
	@Select("select * from school limit #{page},#{limit}")
	public List<School> limitSchool(Integer page,Integer limit);
	
	@Select("select * from school where company_id = #{companyId} limit #{page},#{limit}")
	public List<School> limitSchoolByCompanyId(Integer page,Integer limit,Integer companyId);
	
	public Integer updateSchool(School school);
	
	public Integer deleteManySchool(String[] schoolIds);
	
	@Select("select count(*) from school")
	public Integer getSchoolCount();
	
	@Select("select count(*) from school where company_id = #{companyId}")
	public Integer getCompanySchoolCount(Integer companyId);
	
	@Select("select * from school s left join school_slideshow ss on s.school_id = ss.school_id LEFT JOIN school_honor sh on s.school_id = sh.school_id WHERE s.school_id = #{schoolId}")
	@ResultMap("school_1")
	public School querySchoolBySchoolId(Integer schoolId);
	
	@Select("select * from school where school_id = #{schoolId}")
	public School querySchoolById(Integer schoolId);
	
	@Select("select * from school where company_id = #{companyId}")
	public List<School> querySchoolByCompanyId(Integer companyId);
	
	@Select("select school_id from school where school_name = #{schoolName}")
	public Integer querySchoolIdBySchoolName(String schoolName);
	
	@Select("select * from school where school_name like #{schoolName}")
	public List<School> querySchoolIdLikeSchoolName(String schoolName);
	
	@Select("select * from school where company_id = #{companyId} and school_name like #{schoolName}")
	public List<School> querySchoolLikeSchoolNameAndByCompanyId(String schoolName,Integer companyId);
	
	
	
	
	//	轮播图
	@Insert("INSERT INTO `school_slideshow`(`slideshow_img`, `school_id`,slideshow_creator,slideshow_creation_time) VALUES (#{slideshowImg}, #{schoolId},#{slideshowCreator},#{slideshowCreationTime});")
	public Integer addSchoolSlideshow(String slideshowImg,Integer schoolId,Date slideshowCreationTime,String slideshowCreator);
	
	@Select("select * from school_slideshow  where school_id = #{schoolId} limit #{page},#{limit} ")
	public List<SchoolSlideshow> limitSchoolSlideshow(Integer page,Integer limit,Integer schoolId);
	
	@Select("select count(*) from school_slideshow where school_id = #{schoolId}")
	public Integer getSchoolSlideshowCount(Integer schoolId);
	
	@Update("update school_slideshow set slideshow_img = #{slideshowImg},slideshow_editor=#{slideshowEditor},slideshow_edit_session = #{slideshowEditSession} where school_slideshow_id = #{schoolSlideshowId}")
	public Integer updateSchoolSlideshow(SchoolSlideshow slideshow);

	@Delete("delete from school_slideshow where school_slideshow_id = #{schoolSlideshowId}")
	public Integer deleteSchoolSlideshowById(Integer schoolSlideshowId);
	
	public Integer deleteManySchoolSlideshow(String[] schoolSlideshowId);
	
	
	
	
	//	荣誉图
	@Insert("INSERT INTO `school_honor`( `honor_img`, `school_id`,honor_creator,honor_creation_time) VALUES (#{honorImg},#{schoolId},#{honorCreator},#{honorCreationTime});")
	public Integer addSchoolHonor(String honorImg,Integer schoolId,String honorCreator,Date honorCreationTime);
	
	@Select("select * from school_honor where school_id = #{schoolId} limit #{page},#{limit} ")
	public List<SchoolHonor> limitSchoolHonor(Integer page,Integer limit,Integer schoolId);
	
	@Select("select count(*) from school_honor where school_id = #{schoolId}")
	public Integer getSchoolHonorCount(Integer schoolId);
	
	@Update("update school_honor set honor_img = #{honorImg},honor_editor=#{honorEditor},honor_edit_session = #{honorEditSession} where school_honor_id = #{schoolHonorId}")
	public Integer updateSchoolHonor(SchoolHonor honor);
	
	@Delete("delete from school_honor where school_honor_id = #{schoolHonorId}")
	public Integer deleteSchoolSchoolHonorId(Integer schoolHonorId);
	
	public Integer deleteManySchoolHonor(String[] SchoolHonorId);
	
	
}
