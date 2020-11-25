package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.siwo.model.ScoreGoodsDetail;
import com.siwo.model.YearMonth;

@Mapper
public interface ScoreGoodsDetailDao {
	/**
	 * 增加明细
	 * @param detail
	 * @return
	 */
	@Insert("INSERT INTO `s_detail`(`detail_id`, `student_id`, `detail_name`, `detail_type_name`,`detail_score`, `detail_flag`, `detail_attendance`, `detail_create_time`) "
			+ " VALUES (null, #{studentId},#{detailName},#{detailTypeName}, #{detailScore}, #{detailFlag}, #{detailAttendance},#{detailCreateTime} );")
	public int addScoreGoodsDetail(ScoreGoodsDetail detail);
	/**
	 * 删除明细
	 * @param studentId
	 * @param attendance
	 */
	@Delete("delete from  s_detail " + 
			"where student_id=#{studentId} and detail_attendance=#{attendance} and detail_type_name=#{typeName} ")
	public int deleteScoreDetail(Integer studentId, Integer attendance,String typeName);
	/**
	 * 修改积分明细数据
	 */
	@Update("update s_detail " + 
			"set detail_score=detail_score-#{score} , detail_create_time=now()  " + 
			"where  student_id=#{studentId} and detail_attendance=#{attendance} and detail_type_name=#{typeName} ")
	public int updateScoreDetail(Integer studentId, Integer attendance,String typeName,Integer score);
	
	/***************************
	 * 查询每个孩子积分详情的年，月
	 */
	@Select("SELECT date_format(d.detail_create_time,'%Y') year,date_format(d.detail_create_time,'%m') month from s_detail d  " + 
			"where student_id=#{studentId} " + 
			"group by date_format(d.detail_create_time,'%Y-%m')  " + 
			"order by detail_create_time desc ")
	public List<YearMonth> queryYearMonth(Integer studentId);
	/****************************
	 * 查询每个孩子的积分明细 根据年，月
	 */
	@Select(" select * from   s_detail  " + 
			" where YEAR(detail_create_time)=#{year} AND MONTH(detail_create_time)=#{month} and student_id=#{studentId} " + 
			" order by detail_create_time desc ")
	public List<ScoreGoodsDetail> queryScoreGoodsDetailByYM(String year,String month,Integer studentId);
	/*********************************
	 * 查询每个孩子获取的积分
	 */
	@Select("select sum(detail_score) from   s_detail  " + 
			" where YEAR(detail_create_time)=#{year} AND MONTH(detail_create_time)=#{month} and student_id=#{studentId}  and detail_flag=#{flag} " + 
			"order by detail_create_time desc")
	public Integer queryScoreByStudentId(String year,String month,Integer studentId,Integer flag);
	/**
	 * 查询机构每个校区孩子的积分明细
	 * @param companyId
	 * @return
	 */
	@Select("select * from s_detail " + 
			"where student_id  in (select student_id from student  where school_id  =#{schoolId}) order by detail_create_time desc")
	public List<ScoreGoodsDetail> queryGoodsDetailBySchoolId(Integer schoolId);
	/**
	 * 根据学生id
	 */
	@Select(" select * from s_detail  where student_id= #{studentId}  order by  detail_create_time desc")
	public List<ScoreGoodsDetail> queryDetailByStudentId(Integer studentId);
	/**
	 * 获取积分详情
	 * @param studentId
	 * @return
	 */
	@Select(" select * from   s_detail  " + 
			" where YEAR(detail_create_time)=#{year} AND MONTH(detail_create_time)=#{month} and student_id=#{studentId} and detail_flag=0 " + 
			" order by detail_create_time desc ")
	public List<ScoreGoodsDetail> queryScoreGoodsDetailByHQ(String year,String month,Integer studentId);
	/**
	 * 消费积分详情
	 * @param studentId
	 * @return
	 */
	@Select(" select * from   s_detail  " + 
			" where YEAR(detail_create_time)=#{year} AND MONTH(detail_create_time)=#{month} and student_id=#{studentId} and detail_flag=1 " + 
			" order by detail_create_time desc ")
	public List<ScoreGoodsDetail> queryScoreGoodsDetailByXF(String year,String month,Integer studentId);
}
