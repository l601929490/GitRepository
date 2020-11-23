package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.siwo.model.Consult;
import com.siwo.model.MyClass;

public interface ConsultService {

	public Map<String, Object> addConsult(Consult consult);
	
	/**
	 * 	查询全部咨询
	 * @return
	 */
	public Map<String, Object> queryAllConsult(Integer schoolId);
	
	/**
	 * 	更新咨询
	 * @param consult
	 * @return
	 */
	public Map<String, Object> updateConsult(Consult consult);
	
	/**
	 * 	删除一个咨询
	 * @param consultId
	 * @return
	 */
	public Map<String, Object> deleteConsult(String consultId);
	
	/**
	 * 	删除多个咨询
	 * @param consultIds
	 * @return
	 */
	public Map<String, Object> deleteManyConsult(String[] consultIds);
	
	/**
	 * 	按照ID查咨询
	 * @param consultId
	 * @return
	 */
	public Map<String, Object> queryConsultById(Integer consultId);
	
	/**
	 *	分页查询
	 * @param page
	 * @param limit
	 * @param schoolId
	 * @return
	 */
	public Map<String, Object> limitConsults(Integer page, Integer limit,Integer schoolId);
	
	public Map<String, Object> fuzzyQueryConsults(String param,Integer schoolId);
	
}
