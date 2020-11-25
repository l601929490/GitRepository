package com.siwo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.XqLearningOutcomes;
import com.siwo.model.XqTypeStar;

@Mapper
public interface XqLearningOutcomesDao {

	/**
	 * 选择课时版
	 * @param outcomes
	 * @return
	 */
	//	学情打分
	@Insert("INSERT INTO `xq_learning_outcomes`(`class_id`, `student_id`, `xq_type_id`, `class_day_id` ,`star`,comment_time,comment) "
			+ "VALUES (#{classId}, #{studentId}, #{xqTypeId}, #{classDayId} ,#{star},#{commentTime},#{comment})")
	@Options(useGeneratedKeys = true,keyColumn = "xq_learning_outcomes_id",keyProperty = "xqLearningOutcomesId")
	public Integer addXqLearningOutcomes(XqLearningOutcomes outcomes);
	
	//	添加学情详细分数
	@Insert("INSERT INTO `xq_type_star`(`xq_learning_outcomes_id`, `xq_type_id`, `star`) VALUES (#{xqLearningOutcomesId}, #{xqTypeId}, #{star})")
	public Integer addXqTypeStar(XqTypeStar xqTypeStar);
	
	//	修改学情详细分数
	@Update("UPDATE `xq_type_star` SET `star` = #{star} WHERE `xq_learning_outcomes_id` = #{xqLearningOutcomesId} AND `xq_type_id` = #{xqTypeId}")
	public Integer updateXqTypeStar(XqTypeStar xqTypeStar);
	
	//	查询学情详细分数
	@Select("select * from xq_type_star where xq_learning_outcomes_id = #{xqLearningOutcomesId} and xq_type_id = #{xqTypeId}")
	public XqTypeStar queryXqTypeStar(Integer xqLearningOutcomesId,Integer xqTypeId);
	
	//	查询学情详细分数
	@Select("select * from xq_type_star where xq_learning_outcomes_id = #{xqLearningOutcomesId}")
	public List<XqTypeStar> queryManyXqTypeStar(Integer xqLearningOutcomesId);
	
	//	修改学情打分
	@Update("update xq_learning_outcomes set star = #{star},comment = #{comment} where student_id = #{studentId} AND class_day_id = #{classDayId} and class_id = #{classId}")
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
	
	
	
	/**
	 *  每日点评版
	 * @param studentId
	 * @param classDayId
	 * @param classId
	 * @return
	 */
	//	查询学生学情打分
	@Select("SELECT * FROM xq_learning_outcomes WHERE student_id = #{studentId} AND class_day_id = #{classDayId} and class_id = #{classId}")
	public XqLearningOutcomes queryXqLearningOutcomesByStudentIdTwo(Integer studentId,Integer classDayId,Integer classId);
	
	//	修改学情打分
	@Update("update xq_learning_outcomes set star = #{star} where student_id = #{studentId} AND comment_time = #{commentTime} and xq_type_id = #{xqTypeId} and class_id = #{classId}")
	public Integer updateXqLearningOutcomesTwo(XqLearningOutcomes outcomes);
	
	//	查询学生学情打分
	@Select("SELECT * FROM xq_learning_outcomes WHERE student_id = #{studentId} AND class_day_id = #{classDayId} and class_id = #{classId}")
	public XqLearningOutcomes getXqLearningOutcomesByStudentIdTwo(Integer studentId,Integer classDayId,Integer classId);
	
	//	查询指定日期内的学情
	@Select("select * from xq_learning_outcomes where class_day_id = #{classDayId}")
	public List<XqLearningOutcomes> getXqLearningOutcomesByXqClassDayIdTwo(Integer classDayId);
}
