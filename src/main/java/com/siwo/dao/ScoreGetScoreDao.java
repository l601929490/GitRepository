package com.siwo.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.siwo.model.ScoreGetScore;

@Mapper
public interface ScoreGetScoreDao {
	/**
	 *给学生加积分
	 * @return
	 */
	@Insert("INSERT INTO  `s_getscore`(`getscore_id`, `student_id`, `rule_type`,`attendance_id`, `rule_score`, `create_time`) "
			+ " VALUES (null, #{studentId}, #{ruleName}, #{attendanceId},#{ruleScore},#{createTime});")
	public int addScore(ScoreGetScore score );
	
	/***********************
	 * 判断孩子今天 是否打过卡 还是登录过？？
	 * 
	 */
	@Select("select count(*) from s_getscore " + 
			"where student_id =#{studentId} and rule_type =#{ruleName} and  to_days(create_time)=  to_days(now());")
	public int quertCount(Integer studentId,String ruleName); 
	
	/*************
	 * 判断该任务 是否已经打过卡？还是老师已经点评过？
	 * @param studentId
	 * @param attendanceId
	 * @return
	 */
	@Select("select * from s_getscore " + 
			"where student_id =#{studentId} and rule_type =#{ruleName} and attendance_id=#{attendanceId}")
	public ScoreGetScore quertCountDz(Integer studentId,String ruleName,Integer attendanceId);
	/**
	 * 撤回点赞
	 * @param studentId
	 * @param attendanceId
	 * @return
	 */
	@Delete("delete from s_getscore " + 
			"where student_id=#{studentId} and attendance_id=#{attendanceId} and rule_type =#{ruleName}")
	public int deleteScore(Integer studentId,Integer attendanceId,String ruleName);
	/**
	 * 修改打分
	 */
	@Update("UPDATE  `s_getscore` " + 
			"SET  `rule_score` = #{RuleScore} ,create_time=now()" + 
			"WHERE `getscore_id` = #{score}  ;")
	public int updateScore(Integer score,Integer RuleScore);
	
}
