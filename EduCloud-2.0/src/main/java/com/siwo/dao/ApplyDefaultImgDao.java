package com.siwo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.applyDefaultImg;

@Mapper
public interface ApplyDefaultImgDao {

	@Insert("INSERT INTO `apply_default_img`(`schoolId`, `checked`, `img`) VALUES (#{schoolId}, #{checked}, #{img})")
	public Integer addApplyDefaultImg(applyDefaultImg applyDefaultImg);
	
	@Update("UPDATE `apply_default_img` SET `img` = #{img} WHERE `img_id` = #{imgId}")
	public Integer updateApplyDefaultImg(applyDefaultImg applyDefaultImg);

	@Select("select * from apply_default_img where schoolId = #{schoolId}")
	public applyDefaultImg queryImgBySchoolId(Integer schoolId);
	
	@Update("UPDATE `apply_default_img` SET `checked` = #{checked} where schoolId = #{schoolId}")
	public Integer updateChecked(Integer checked);
}
