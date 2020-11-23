package com.siwo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.XqClassDay;
import com.siwo.model.XqMonthly;
import com.siwo.model.XqStarLevel;
import com.siwo.model.XqType;

@Mapper
public interface XqAllocationDao {

	//	课时
	public Integer addXqClassDay(@Param("classDays") List<XqClassDay> classDays);
	
	public Integer deleteManyXqClassDay(String[] xqClassdayIds);
	
	public Integer updateXqClassDay(XqClassDay xqClassDay);
	
	@Select("select * from xq_class_day where school_id = #{schoolId}")
	public List<XqClassDay> queryXqClassDaysBySchoolId(Integer schoolId);
	
	@Select("select * from xq_class_day where class_id = #{classId}")
	public List<XqClassDay> queryXqClassDaysByClassId(Integer classId);
	
	@Select("select * from xq_class_day where class_date >= #{start} and class_date <= #{end}")
	public List<XqClassDay> queryXqClassDayByTime(String start,String end);
	
	@Delete("delete from xq_class_day where class_id = #{classId}")
	public Integer deleteXqClassDayByClassIds(Integer classId);
	
	
	
	//	学情类别
	@Insert("INSERT INTO `xq_type`( `xq_type_name`, `school_id`, `creator`, `start`, `default_star`) VALUES (#{xqTypeName},#{schoolId},#{creator},#{start},#{defaultStar})")
	@Options(useGeneratedKeys = true,keyColumn = "xq_type_id",keyProperty = "xqTypeId")
	public Integer addXqType(XqType xqType);
	
	public Integer deleteManyXqType(String[] xqTypeIds);
	
	public Integer updateXqType(XqType xqType);
	
	@Select("select * from xq_type where school_id = #{schoolId}")
	public List<XqType> queryXqTypes(Integer schoolId);
	
	@Select("select * from xq_type where xq_type_id = #{xqTypeId}")
	public XqType queryXqTypeByXqTypeId(Integer xqTypeId);
	
	
	
	//	月度点评
	@Insert("insert into xq_monthly (student_id,remark,month,teacher_id,class_id) values (#{studentId},#{remark},#{month},#{teacherId},#{classId})")
	public Integer addXqMonthly(XqMonthly xqMonthly);
	
	public Integer updateXqMonthly(XqMonthly xqMonthly);
	
	@Delete("delete from xq_monthly where xq_monthly_id = #{xqMonthlyId}")
	public Integer deleteXqMonthly(Integer xqMonthlyId);
	
	@Select("select * from xq_monthly where student_id = #{studentId} and month = #{month} and class_id = #{classId}")
	public XqMonthly queryXqMonthlyByStudentIdAndMonth(Integer studentId,Date month,Integer classId);
	
	@Select("select * from xq_monthly where student_id = #{studentId} and class_id = #{classId}")
	public List<XqMonthly> queryXqMonthlyByStudentId(Integer studentId,Integer classId);
}
