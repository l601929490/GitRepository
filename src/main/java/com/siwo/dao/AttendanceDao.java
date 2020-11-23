package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Attendance;
import com.siwo.model.AttendanceRecording;
import com.siwo.model.TaskRecording;

@Mapper
public interface AttendanceDao {

	/**
	 * 	添加打卡记录
	 * @param attendance
	 * @return
	 */
	@Insert("INSERT INTO attendance (`attendance_time`, `attendance_comment`, `phone`, `task_id`, `is_affirm`, `class_id`, `student_id`, `correct`, `score`, `make_up`) "
			+ "values  (#{attendanceTime}, #{attendanceComment}, #{phone},#{taskId},  #{isAffirm}, #{classId}, #{studentId}, #{correct}, #{score}, #{makeUp})")
	@Options(useGeneratedKeys = true,keyColumn = "attendance_id",keyProperty = "attendanceId")
	public Integer addAttendance(Attendance attendance);
	
	/**
	 * 	添加打卡图片
	 * @param attendanceId
	 * @param img
	 * @return
	 */
	@Insert("insert into attendance_and_img (attendance_id,attendance_img) values (#{attendanceId},#{img})")
	public Integer addAttendanceImg(Integer attendanceId,String img);
	
	/**
	 * 	添加打卡视频
	 * @param taskId
	 * @param video
	 * @return
	 */
	@Insert("insert into attendance_and_video (attendance_id,attendance_video) values (#{attendanceId},#{video})")
	public Integer addAttendanceVideo(Integer attendanceId,String video);
	
	/**
	 * 	添加打卡录音
	 * @return
	 */
	@Insert("insert into attendance_and_recording (attendance_id,recording,recording_time) values (#{attendanceId},#{recording},#{recordingTime})")
	public Integer addAttendanceRecording(AttendanceRecording recording);
	
	/**
	 * 	按attendanceId查作业图片
	 * @param taskId
	 * @return
	 */
	@Select("select attendance_img from attendance_and_img where attendance_id = #{attendanceId}")
	public List<String> queryAttendanceImgByTaskId(Integer attendanceId);
	
	/**
	 * 	按attendanceId查作业视频
	 * @param taskId
	 * @return
	 */
	@Select("select attendance_video from attendance_and_video where attendance_id = #{attendanceId}")
	public List<String> queryAttendanceVideoByTaskId(Integer attendanceId);
	
	/**
	 * 	按attendanceId查作业录音
	 * @param taskId
	 * @return
	 */
	@Select("select * from attendance_and_recording where attendance_id = #{attendanceId}")
	public List<AttendanceRecording> queryAttendanceRecordingByTaskId(Integer attendanceId);
	
	/**
	 * 	修改作业
	 * @param attendance
	 * @return
	 */
	public Integer updateAttendance(Attendance attendance);
	
	/**
	 * 	删除作业视频
	 * @param taskId
	 * @param video
	 * @return
	 */
	@Insert("DELETE FROM attendance_and_video WHERE `attendance_id` = #{attendanceId}")
	public Integer deleteAttendanceVideo(Integer attendanceId);
	
	/**
	 * 	删除作业图片
	 * @param taskId
	 * @param img
	 * @return
	 */
	@Insert("DELETE FROM attendance_and_img WHERE `attendance_id` = #{attendanceId}")
	public Integer deleteAttendanceImg(Integer attendanceId);
	
	/**
	 * 	添加作业录音
	 * @param recording
	 * @return
	 */
	@Insert("DELETE FROM attendance_and_recording WHERE `attendance_id` = #{attendanceId}")
	public Integer deleteAttendanceRecording(Integer attendanceId);
	
	/**
	 * 	按id查打卡记录
	 * @param attendanceId
	 * @return
	 */
	@Select("select * from attendance where attendance_id = #{attendanceId}")
	public Attendance queryById(Integer attendanceId);
	
	/**
	 * 	查询所有作业
	 * @param taskId
	 * @return
	 */
	@ResultMap("att_1")
	@Select("select * from attendance a "
			+ " left join student s on a.student_id = s.student_id "
			+ " left join user u on a.phone = u.user_phone "
			+ " where task_id = #{taskId} and class_id = #{classId}")
	public List<Attendance> queryAttendanceByTaskId(Integer taskId,Integer classId);
	
	@Select("select * from attendance where task_id = #{taskId}")
	public List<Attendance> queryAttendanceByTaskId_2(Integer taskId);
	
	/**
	 * 	查询优秀作业
	 * @param taskId
	 * @param isAffirm
	 * @return
	 */
	@ResultMap("att_1")
	@Select("select * from attendance a "
			+ " left join student s on a.student_id = s.student_id "
			+ " left join user u on a.phone = u.user_phone "
			+ " where task_id = #{taskId} and class_id = #{classId} and is_affirm = #{isAffirm}")
	public List<Attendance> queryGoodAttendanceByTaskId(Integer taskId,Integer isAffirm,Integer classId);
	
	/**
	 * 	根据手机号查询打卡记录
	 * @param phone
	 * @param taskId
	 * @return
	 */
	@Select("select * from attendance where phone = #{phone} and task_id = #{taskId}")
	public Attendance queryAttendanceByPhone(String phone,Integer taskId);
	
	/**
	 * 	按照Id删除打卡记录
	 * @param attendanceId
	 * @return
	 */
	@Delete("delete from attendance where attendance_id = #{attendanceId}")
	public Integer deleteAttendanceById(Integer attendanceId);
	
	/**
	 * 	按照任务id和学生id查询打卡记录
	 * @param taskId
	 * @param studentId
	 * @return
	 */
	@ResultMap("att_1")
	@Select("select * from attendance a " + 
			"left join student s on a.student_id = s.student_id " + 
			"left join user u on a.phone = u.user_phone " + 
			"where task_id = #{taskId} and class_id = #{classId} and a.student_id = #{studentId}")
	public Attendance queryAttendanceByStudentIdAndTaskId(Integer taskId,Integer studentId,Integer classId);
	
	@ResultMap("att_1")
	@Select("select * from attendance a " + 
			"left join student s on a.student_id = s.student_id " + 
			"left join user u on a.phone = u.user_phone " + 
			"where task_id = #{taskId} and class_id = #{classId} and a.student_id = #{studentId}")
	public List<Attendance> queryAttendanceByStudentIdAndLongTimeTaskId(Integer taskId,Integer studentId,Integer classId);
	
	/**
	 * 	查询学生一个班级的一个任务是否提交作业
	 * @param taskId
	 * @param studentId
	 * @param classId
	 * @return
	 */
	@Select("select * from attendance where task_id = #{taskId} and class_id = #{classId} and student_id = #{studentId}")
	public Attendance queryAttendanceByStudentIdAndTaskIdAndClassId(Integer taskId,Integer studentId,Integer classId);
	
	/**
	 * 	修改作业状态 通过/优秀
	 * @return
	 */
	@Update("update attendance set is_affirm = #{isAffirm} where attendance_id = #{attId}")
	public Integer updateIsAffirm(Integer attId,Integer isAffirm);
	
	/**
	 * 	修改作业订正状态
	 * @param attId
	 * @param correct
	 * @return
	 */
	@Update("update attendance set correct = #{correct} where attendance_id = #{attId}")
	public Integer updateCorrect(Integer attId,Integer correct);
	
	/**
	 * 	老师打分
	 * @param attId
	 * @param correct
	 * @return
	 */
	@Update("update attendance set score = #{score} where attendance_id = #{attId}")
	public Integer updateScore(Integer attId,Integer score);
	
	/**
	 * 	根据id查打卡记录
	 * @param attendanceId
	 * @return
	 */
	@Select("select * from attendance where attendance_id = #{attendanceId}")
	public Attendance queryAttendanceByAttendanceId(Integer attendanceId);
	
	/**
	 * 	按照时间和电话查记录
	 * @param startTime
	 * @param endTime
	 * @param phone
	 * @return
	 */
	@Select("SELECT " + 
			" * " + 
			" FROM " + 
			" attendance " + 
			" WHERE " + 
			" phone = #{phone} " + 
			" AND attendance_time > #{startTime}" + 
			" AND attendance_time < #{endTime}")
	public List<Attendance> queryAttendanceByTimeAndPhone(String startTime,String endTime,String phone);
	
	@Select("SELECT " + 
			" * " + 
			" FROM " + 
			" attendance " + 
			" WHERE " + 
			" phone = #{phone} " + 
			" AND class_id = #{classId}" +
			" AND attendance_time > #{startTime}" + 
			" AND attendance_time < #{endTime}")
	public List<Attendance> queryAttendanceByTimeAndPhoneAndClassId(String startTime,String endTime,String phone,Integer classId);
	
	/**
	 * 	按照studentId查一个任务的评论
	 * @param startTime
	 * @param endTime
	 * @param phone
	 * @return
	 */
	@Select("SELECT " + 
			" * " + 
			" FROM " + 
			" attendance " + 
			" WHERE " + 
			" student_id = #{studentId} "+ 
			" AND task_id = #{taskId}"+ 
			" AND class_id = #{classId}" + 
			" AND attendance_time > #{startTime}" + 
			" AND attendance_time < #{endTime}")
	public Attendance queryAttendanceByTimeAndStudentId(String startTime,String endTime,Integer studentId,Integer taskId,Integer classId);
	
	@Select("SELECT " + 
			" * " + 
			" FROM " + 
			" attendance " + 
			" WHERE " + 
			" student_id = #{studentId} "+ 
			" AND attendance_time > #{startTime}" + 
			" AND attendance_time < #{endTime}")
	public List<Attendance> queryManyAttendanceByTimeAndStudentId(String startTime,String endTime,Integer studentId);
	
	/**
	 * 	按照时间和班级id查记录
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	@Select("SELECT " + 
			" * " + 
			" FROM " + 
			" attendance " + 
			" WHERE class_id = #{classId} "+ 
			" AND task_id = #{taskId}" + 
			" AND attendance_time > #{startTime}" + 
			" AND attendance_time < #{endTime}")
	public List<Attendance> queryAttendanceByTime(String startTime,String endTime,Integer classId,Integer taskId);
	
	/**
	 * 	按classid查手机号
	 * @param classId
	 * @return
	 */
	@Select("select phone from attendance where class_id = #{classId} GROUP BY phone")
	public List<String> queryPhoneByClassId(Integer classId);
	
	/**
	 * 	点赞
	 * @param attendanceId
	 * @param phone
	 * @return
	 */
	@Insert("insert into like_attendance (attendance_id,phone) values (#{attendanceId},#{phone})")
	public Integer addLikeAttendance(Integer attendanceId,String phone);
	
	/**
	 * 	取消点赞
	 */
	@Delete("delete from like_attendance where attendance_id = #{attendanceId} and phone = #{phone}")
	public Integer deleteLikeAttendance(Integer attendanceId,String phone);
	
	/**
	 * 	查询作业所有点赞人的电话
	 * @param attendanceId
	 * @return
	 */
	@Select("select phone from like_attendance where attendance_id = #{attendanceId}")
	public List<String> queryLikeAttendancePhoneByAttendanceId(Integer attendanceId);
	
}
