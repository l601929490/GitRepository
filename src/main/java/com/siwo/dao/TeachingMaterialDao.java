package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.TeachingMaterial;
import com.siwo.model.TeachingMaterialContent;

@Mapper
public interface TeachingMaterialDao {

	@Insert("insert into teaching_material (grade,subject,title,creator,creation_time,school_id) "
			+ "values (#{grade},#{subject},#{title},#{creator},#{creationTime},#{schoolId})")
	public Integer addTeachingMaterial(TeachingMaterial teachingMaterial);
	
	//	添加试题内容
	public Integer addTeachingMaterialContent(@Param(value = "contents") List<TeachingMaterialContent> contents);
	
	//	查询试题内容
	@Select("select * from teaching_material_content where teaching_material_id = #{teachingMaterialId}")
	public List<TeachingMaterialContent> queryTeachingMaterialContents(Integer teachingMaterialId);
	
	//	删除试题内容
	public Integer deleteContent(String[] materialIds);
	@Delete("delete from teaching_material_content where teaching_material_id = #{teachingMaterialId}")
	public Integer deleteContentById(Integer teachingMaterialId);
	
	//	查询试题内容
	@Select("select * from teaching_material_content where teachingMaterialContentId = #{contentId}")
	public Integer queryContentById(Integer contentId);
	
	//	修改试题内容
	public Integer updateContentById(TeachingMaterialContent content);
	
	public Integer deleteManyTeachingMaterial(String[] materialIds);
	
	public Integer updateTeachingMaterial(TeachingMaterial teachingMaterial);
	
	@ResultMap("tm")
	@Select("select * from teaching_material tm left join teaching_material_content tmc on tm.teaching_material_id = tmc.teaching_material_id where school_id = #{schoolId}")
	public List<TeachingMaterial> getAllTeachingMaterials(Integer schoolId);
	
	/**
	 * 	按年级和学科搜索教材
	 * @param grade
	 * @param subject
	 * @param schoolId
	 * @return
	 */
	@ResultMap("tm")
	@Select("select * from teaching_material tm left join teaching_material_content tmc on tm.teaching_material_id = tmc.teaching_material_id where school_id = #{schoolId} and grade = #{grade} and subject = #{subject}")
	public List<TeachingMaterial> queryTeachingMaterialsByGradeAndSubject(Integer grade,Integer subject,Integer schoolId);
	
	/**
	 * 	按照Id查询教材
	 * @param teachingMaterialId
	 * @return
	 */
	@ResultMap("tm")
	@Select("select * from teaching_material tm left join teaching_material_content tmc on tm.teaching_material_id = tmc.teaching_material_id where tm.teaching_material_id = #{teachingMaterialId}")
	public TeachingMaterial queryTeachingMaterialByTeachingMaterialId(Integer teachingMaterialId);
}
