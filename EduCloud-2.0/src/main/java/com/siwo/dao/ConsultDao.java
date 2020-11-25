package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Consult;

@Mapper
public interface ConsultDao {

	/**
	 * 添加咨询
	 * @return
	 */
	@Select("insert into consult (consult_title,consult_subhead,subhead_img,subhead_text,time,read_consult,school_id,creator,creation_time) "
			+ "values (#{consultTitle},#{consultSubhead},#{subheadImg},#{subheadText},#{time},#{readConsult},#{schoolId},#{creator},#{creationTime})")
	public Integer addConsult(Consult consult);
	
	/**
	 * 查询所有咨询
	 * @return
	 */
	@Select("select * from consult where school_id = #{schoolId}")
	public List<Consult> queryAllConsult(Integer schoolId);
	
	/**
	 * 	修改咨询信息
	 * @param consult
	 * @return
	 */
	@Update("update consult set consult_title = #{consultTitle},consult_subhead = #{consultSubhead},subhead_img = #{subheadImg},subhead_text = #{subheadText},editor = #{editor},edit_session = #{editSession} where consult_id = #{consultId}")
	public Integer updateConsult(Consult consult);
	
	/**
	 * 	删除咨询信息
	 * @param consultId
	 * @return
	 */
	@Delete("delete from consult where consult_id = #{consultId}")
	public Integer deleteConsult(String consultId);
	
	/**
	 * 	删除多个咨询
	 * @param consultIds
	 * @return
	 */
	public Integer deleteManyConsult(String[] consultIds);
	
	/**
	 * 	按照Id查询咨询
	 * @param consultId
	 * @return
	 */
	@Select("select * from consult where consult_id = #{consultId}")
	public Consult queryConsultById(Integer consultId);
	
	/**
	 * 	更新阅读量
	 * @param read
	 * @param consultId
	 * @return
	 */
	@Update("update consult set read_consult = #{read} where consult_id = #{consultId}")
	public Integer updateReadConsult(Integer read,Integer consultId);
	
	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @param schoolId
	 * @return
	 */
	@Select("SELECT * FROM consult WHERE school_id = #{schoolId} LIMIT #{page},#{limit}")
	public List<Consult> limitConsults(Integer page, Integer limit,Integer schoolId);
	
	@Select("select count(*) from consult where school_id = #{schoolId}")
	public Integer getConsultCount(Integer schoolId);
	
	@Select("SELECT * from consult where school_id = #{schoolId} and (consult_title LIKE #{param} or consult_subhead LIKE #{param} or subhead_text LIKE #{param})")
	public List<Consult> fuzzyQueryConsults(String param,Integer schoolId);
}
