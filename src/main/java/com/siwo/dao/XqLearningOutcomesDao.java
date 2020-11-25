package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.XqLearningOutcomes;

@Mapper
public interface XqLearningOutcomesDao {

	//	学情打分
	@Insert("INSERT INTO `xq_learning_outcomes`(`class_id`, `student_id`, `xq_type_id`, `class_day_id` ,`star`) VALUES (#{classId}, #{studentId}, #{xqTypeId}, #{classDayId} ,#{star})")
	public Integer addXqLearningOutcomes(XqLearningOutcomes outcomes);
	
	//	修改学情打分
	@Update("update xq_learning_outcomes set star = #{star} where student_id = #{studentId} AND class_day_id = #{classDayId} and xq_type_id = #{xqTypeId} and class_id = #{classId}")
	public Integer updateXqLearningOutcomes(XqLearningOutcomes outcomes);
	
	//	查询学生学情打分
	@Select("SELECT * FROM xq_learning_outcomes WHERE student_id = #{studentId} AND class_day_id = #{classDayId} and xq_type_id = #{xqTypeId} and class_id = #{classId}")
	public XqLearningOutcomes queryXqLearningOutcomesByStudentId(Integer studentId,Integer classDayId,Integer xqTypeId,Integer classId);
	
	//	查询学生学情打分
	@Select("SELECT * FROM xq_learning_outcomes WHERE student_id = #{studentId} AND class_day_id = #{classDayId} and class_id = #{classId}")
	public List<XqLearningOutcomes> getXqLearningOutcomesByStudentId(Integer studentId,Integer classDayId,Integer classId);
	
	//	查询指定日期内的学情
	@Select("select * from xq_learning_outcomes where class_day_id = #{classDayId}")
	public List<XqLearningOutcomes> getXqLearningOutcomesByXqClassDayId(Integer classDayId);
}
