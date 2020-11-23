package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Param;

@Mapper
public interface ParamDao {

	/**
	 * 	添加基础参数
	 * @param param
	 * @return
	 */
	@Insert("INSERT INTO `param`(`param_name`, `param_type`, `param_schoolId`, `creator`, `creation_time`,`audio_type`) VALUES "
			+ "(#{paramName}, #{paramType}, #{paramSchoolId}, #{creator}, #{creationTime},#{audioType});")
	public Integer addParam(Param param);
	
	/**
	 * 	删除基础参数
	 * @param paramId
	 * @return
	 */
	@Delete("DELETE FROM `param` WHERE `param_id` = #{paramId}")
	public Integer deleteParamByParamId(Integer paramId);
	
	/**
	 * 	删除多个基础参数
	 * @param paramIds
	 * @return
	 */
	public Integer deleteManyParam(String[] paramIds);
	
	/**
	 * 	修改基础参数
	 * @param param
	 * @return
	 */
	public Integer updateParam(Param param);
	
	/**
	 * 	按学校id和参数类型查
	 * @param schoolId
	 * @return
	 */
	@Select("select * from param where param_schoolId = #{schoolId} and param_type = #{paramType}")
	public List<Param> queryParamBySchoolId(Integer schoolId,String paramType);
	
	/**
	 * 	分页查询
	 * @param page
	 * @param limit
	 * @param schoolId
	 * @return
	 */
	@Select("select * from param where param_schoolId = #{schoolId} and param_type = #{paramType} limit #{page},#{limit}")
	public List<Param> limitParam(Integer page,Integer limit,Integer schoolId,String paramType);
	
	/**
	 * 	获取数量
	 * @param schoolId
	 * @return
	 */
	@Select("select count(*) from param where param_schoolId = #{schoolId} and param_type = #{paramType}")
	public Integer getParamCount(Integer schoolId,String paramType);
	
	/**
	 * 	按名字模糊查讯
	 * @param paramName
	 * @param schoolId
	 * @param paramType
	 * @return
	 */
	@Select("select * from param where param_schoolId = #{schoolId} and param_type = #{paramType} and param_name like #{paramName}")
	public List<Param> queryParamByParamName(String paramName,Integer schoolId,String paramType);
}
