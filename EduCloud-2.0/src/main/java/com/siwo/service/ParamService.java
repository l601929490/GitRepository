package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.siwo.model.Param;

public interface ParamService {

	/**
	 * 	添加基础参数
	 * @param param
	 * @return
	 */
	public Map<String, Object> addParam(Param param);
	
	/**
	 * 	添加语音评测提示音
	 * @param param
	 * @return
	 */
	public Map<String, Object> addRecordParam(List<Param> param);
	
	/**
	 * 	删除基础参数
	 * @param paramId
	 * @return
	 */
	public Map<String, Object> deleteParamByParamId(Integer paramId);
	
	/**
	 * 	删除多条基础参数
	 * @param paramId
	 * @return
	 */
	public Map<String, Object> deleteManyParam(String[] paramIds);
	
	/**
	 * 	修改基础参数
	 * @param param
	 * @return
	 */
	public Map<String, Object> updateParam(Param param);
	
	/**
	 * 	按学校id和参数类型查
	 * @param schoolId
	 * @return
	 */
	public Map<String, Object> queryParamBySchoolId(Integer schoolId,String paramType);
	
	/**
	 * 	分页查询
	 * @param page
	 * @param limit
	 * @param schoolId
	 * @return
	 */
	public Map<String, Object> limitParam(Integer page,Integer limit,Integer schoolId,String paramType);
	
	/**
	 * 	按名字模糊查讯
	 * @param paramName
	 * @param schoolId
	 * @param paramType
	 * @return
	 */
	public Map<String, Object> queryParamByParamName(String paramName,Integer schoolId,String paramType);
}
