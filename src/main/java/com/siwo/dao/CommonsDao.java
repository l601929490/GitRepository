package com.siwo.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommonsDao {

	@Insert("insert into img_color (home_img,school_id,img_colour,whether_need,creator,creation_time) "
			+ "values (#{homeImg},#{schoolId},#{colour},#{WhetherNeed},#{creator},#{createTime})")
	public Integer addColorImg(String homeImg,Integer schoolId,String colour,int WhetherNeed,String creator,Date createTime);
	
	public Integer updateColorImg(@Param("homeImg") String homeImg,@Param("schoolId") Integer schoolId,@Param("colour") String colour,
			@Param("imgColorId") Integer imgColorId,@Param("WhetherNeed") Integer WhetherNeed,@Param("editor") String editor,@Param("editSession") Date editSession);
	
//	@Update("update from img_color set whether_need = #{WhetherNeed} where img_color_id = #{imgColorId}")
//	public Integer updateWhetherNeed(Integer imgColorId,Integer WhetherNeed);
	
	@Select("select * from img_color where school_id = #{schoolId} limit #{page},#{limit}")
	public List<Map<String, String>> limitColorImg(Integer page,Integer limit,Integer schoolId);
	
	@Select("select count(*) from img_color where school_id = #{schoolId}")
	public Integer getColorImgCount(Integer schoolId);
	
	@Delete("delete from img_color where img_color_id = #{imgColorId}")
	public Integer deleteColorImgById(Integer imgColorId);
	
	public Integer deleteManyColorImg(String[] imgColorIds);
	
	@Select("select * from img_color where school_id = #{schoolId} and whether_need = 1")
	public List<Map<String, String>> queryColorImgBySchoolId(Integer schoolId);
}
