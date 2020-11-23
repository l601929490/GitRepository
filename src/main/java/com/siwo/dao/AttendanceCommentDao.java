package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.AttendanceComment;


@Mapper
public interface AttendanceCommentDao {

	//	新增评论
	@Insert("INSERT INTO `comment_attendance`(`attendance_id`, `open_id`, `comment`,`comment_time`,`is_secret`,recording,recording_time,teacher_id) "
			+ "VALUES (#{attendanceId}, #{openId}, #{comment}, #{commentTime},#{isSecret},#{recording},#{recordingTime},#{teacherId})")
	public Integer addAttendanceComment(AttendanceComment comment);
	
	//	删除评论
	@Delete("DELETE FROM `comment_attendance` WHERE `comment_attendance_id` = #{commentAttendanceId}")
	public Integer deleteAttendanceComment(Integer commentAttendanceId);
	
	//	删除一个任务的评论
	@Delete("DELETE FROM `comment_attendance` WHERE `attendance_id` = #{attendanceId}")
	public Integer deleteAttendanceCommentByAttendanceId(Integer attendanceId);
	
	//	查询一个作业的所有评论
	@Select("select * from comment_attendance ca left join teacher t on ca.teacher_id = t.teacher_id where attendance_id = #{attendanceId}")
	@ResultMap("comment_1")
	public List<AttendanceComment> queryCommentsByAttendanceId(Integer attendanceId);
	
	//	按私密度查询一个任务的评论
	@ResultMap("comment_1")
	@Select("select * from comment_attendance ca left join teacher t on ca.teacher_id = t.teacher_id where attendance_id = #{attendanceId} and is_secret = #{isSecret}")
	public List<AttendanceComment> queryCommentsByAttendanceIdAndIsSecret(Integer attendanceId,Integer isSecret);
	
	//	按私密度查询一个任务的评论
	@ResultMap("comment_1")
	@Select("select * from comment_attendance ca left join teacher t on ca.teacher_id = t.teacher_id where attendance_id = #{attendanceId} and is_secret = #{isSecret} and ca.teacher_id = #{teacherId}")
	public List<AttendanceComment> queryCommentsByAttendanceIdAndIsSecretAndTeacherId(Integer attendanceId,Integer isSecret,Integer teacherId);
}
