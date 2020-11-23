package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.ScoreSum;

@Mapper
public interface ScoreSumDao {
	@Select("select * from s_scsum where student_id=#{studentId}")
	public ScoreSum queryScoreSumBystudentId(Integer studentId);
	/**
	 * 给学生加积分
	 * @param studentId
	 * @param score
	 * @return
	 */
	@Update(" update s_scsum " + 
			" set sum=sum+#{score} , remain=remain+#{score} " + 
			" where student_id=#{studentId}")
	public int updateScoreSumByStudentId(Integer studentId,Integer score);
	/**
	 * 给学生-积分  
	 * @param scoreSum2
	 * @return
	 */
	@Update(" update s_scsum " + 
			" set  consumption=consumption+#{score}, remain=remain-#{score}  " + 
			" where student_id=#{studentId}")
	public int cutScoreSumByStudentId(Integer studentId,Integer score);
	
	/**
	 * 给学生-积分 撤回打卡 ，修改打分 ， 减去总计分 和 剩余积分
	 * @param scoreSum2
	 * @return
	 */
	@Update(" update s_scsum " + 
			" set  sum=sum-#{score}, remain=remain-#{score}  " + 
			" where student_id=#{studentId}")
	public int cutXGScoreSumByStudentId(Integer studentId,Integer score);
	
	@Insert("INSERT INTO `s_scsum`(`sum_id`, `student_id`, `sum`, `consumption`, `remain`) "
			+ "VALUES (null, #{studentId}, #{sum}, #{consumption}, #{remain});")
	public int addScoreSumByStudentId(ScoreSum scoreSum2);
	
	@Select("select * from s_scsum " + 
			"where student_id in (select student_id from student " + 
			"where school_id=#{schoolId} )")
	public List<ScoreSum> queryScoreSumBySchoolId(Integer schoolId);
	
	@Select("select * from s_scsum " + 
			"where student_id in (select student_id from student " + 
			"where school_id=#{schoolId} and student_name like concat ('%',#{studentName},'%'))")
	public List<ScoreSum> queryScoreSumBySchoolIdsAndStudentName(Integer schoolId,String studentName);
}
