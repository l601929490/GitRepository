package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.MyClass;
import com.siwo.model.Task;
import com.siwo.model.TaskComment;
import com.siwo.model.TaskRecording;

@Mapper
public interface TaskDao {

	/**
	 * 	添加作业
	 * @param task
	 * @return
	 */
	@Insert("insert into task (task_stop_time,sponsor_name,create_time,teacher_id,is_timing,allow_job,timing_time,task_text,task_subject,visible,long_term_use) "
			+ "values (#{taskStopTime},#{sponsorName},#{createTime},#{teacherId},#{isTiming},#{allowJob},#{timingTime},#{taskText},#{taskSubject},#{visible},#{longTermUse})")
	@Options(keyColumn = "task_id",keyProperty = "taskId",useGeneratedKeys = true)
	public Integer addTask(Task task);
	
	/**
	 * 	添加作业班级
	 * @param taskId
	 * @param classId
	 * @return
	 */
	@Insert("insert into task_and_class (task_id,class_id,ok_num) values (#{taskId},#{classId},#{okNum})")
	public Integer addTaskClass(Integer taskId,Integer classId,Integer okNum);
	
	/**
	 * 	添加作业视频
	 * @param taskId
	 * @param video
	 * @return
	 */
	@Insert("insert into task_and_video (task_id,task_video) values (#{taskId},#{video})")
	public Integer addTaskVideo(Integer taskId,String video);
	
	/**
	 * 	添加作业图片
	 * @param taskId
	 * @param img
	 * @return
	 */
	@Insert("insert into task_and_img (task_id,task_img) values (#{taskId},#{img})")
	public Integer addTaskImg(Integer taskId,String img);
	
	/**
	 * 	添加作业录音
	 * @param recording
	 * @return
	 */
	@Insert("insert into task_and_recording (task_id,recording,recording_time) values (#{taskId},#{recording},#{recordingTime})")
	public Integer addTaskRecording(TaskRecording recording);

	/**
	 * 	按班级id查询作业
	 * @param classId
	 * @return
	 */
	@Select("select * from task where task_id in (select task_id from task_and_class where class_id = #{classId}) ORDER BY create_time desc")
	public List<Task> queryTaskByClassId(Integer classId);
	
	/**
	 * 	查询全校作业
	 * @param classId
	 * @return
	 */
	@Select("select * from task t left join teacher tc on t.teacher_id = tc.teacher_id where tc.school_id = #{schoolId} ORDER BY create_time desc")
	public List<Task> limitTask(Integer schoolId);
	
	/**
	 * 	全校作业数量
	 * @param schoolId
	 * @return
	 */
//	@Select("select count(*) from task t left join teacher tc on t.teacher_id = tc.teacher_id where tc.school_id = #{schoolId}")
//	public Integer taskCount(Integer schoolId);
	/**
	 * 	查询学生未完成作业
	 * @param classId
	 * @return
	 */
//	@Select("select * from task where task_id in (select task_id from task_and_class where class_id = {classId})")
//	public List<Task> queryTaskByClassIdAndAllowJob(Integer classId,Integer allowJob);
	
	/**
	 * 	按班级id查作业id
	 * @param classId
	 * @return
	 */
	@Select("select task_id from task where class_id = #{classId}")
	public List<Integer> queryTaskIdByClassId(Integer classId);
	
	/**
	 * 	按taskId查作业
	 * @param taskId
	 * @return
	 */
	@Select("select * from task where task_id = #{taskId}")
	public Task queryTaskByTaskId(Integer taskId);
	
	/**
	 * 	按taskId查作业图片
	 * @param taskId
	 * @return
	 */
	@Select("select task_img from task_and_img where task_id = #{taskId}")
	public List<String> queryTaskImgByTaskId(Integer taskId);
	
	/**
	 * 	按taskId查作业视频
	 * @param taskId
	 * @return
	 */
	@Select("select task_video from task_and_video where task_id = #{taskId}")
	public List<String> queryTaskVideoByTaskId(Integer taskId);
	
	/**
	 * 	按taskId查作业录音
	 * @param taskId
	 * @return
	 */
	@Select("select * from task_and_recording where task_id = #{taskId}")
	public List<TaskRecording> queryTaskRecordingByTaskId(Integer taskId);
	
	/**
	 * 	修改作业
	 * @param task
	 * @return
	 */
	public Integer updateTask(Task task);
	
	/**
	 * 	删除作业视频
	 * @param taskId
	 * @param video
	 * @return
	 */
	@Insert("DELETE FROM task_and_video WHERE `task_id` = #{taskId}")
	public Integer deleteTaskVideo(Integer taskId);
	
	/**
	 * 	删除作业图片
	 * @param taskId
	 * @param img
	 * @return
	 */
	@Insert("DELETE FROM task_and_img WHERE `task_id` = #{taskId}")
	public Integer deleteTaskImg(Integer taskId);
	
	/**
	 * 	添加作业录音
	 * @param recording
	 * @return
	 */
	@Insert("DELETE FROM task_and_recording WHERE `task_id` = #{taskId}")
	public Integer deleteTaskRecording(Integer taskId);
	
	/**
	 * 	修改任务的已打卡人数
	 * @param clockNumber
	 * @param taskId
	 * @return
	 */
	@Update("update task_and_class set ok_num = #{okNum} where task_id = #{taskId} and class_id = #{classId}")
	public Integer updateokNum(Integer okNum,Integer taskId,Integer classId);
	
	/**
	 * 	获取任务的已打卡人数
	 * @param taskId
	 * @return
	 */
	@Select("select count(*) from attendance where task_id = #{taskId} and class_id = #{classId}")
	public Integer selectTaskOkNum(Integer taskId,Integer classId);
		
	/**
	 * 	添加整体点评
	 * @param taskId
	 * @param comment
	 * @return
	 */
	@Insert("insert into task_comment (task_id,comment,recording,recording_time,comment_time) values (#{taskId},#{comment},#{recording},#{recordingTime},#{commentTime})")
	public Integer addTaskComment(TaskComment taskComment);
	
	/**
	 * 	修改整体点评
	 * @param taskComment
	 * @return
	 */
	public Integer updateTaskComment(TaskComment taskComment);
	
	/**
	 * 	查询整体点评
	 * @return
	 */
	@Select("select * from task_comment where task_id = #{taskId}")
	public TaskComment queryTaskCommentById(Integer taskId);
	
	/**
	 * 	删除任务
	 * @param ids
	 * @return
	 */
	@Delete("delete from task where task_id = #{taskId}")
	public Integer deleteTask(Integer taskId);
	
	/**
	 * 	解除任务和班级的管理
	 * @param classId
	 * @param taskId
	 * @return
	 */
	@Delete("delete from task_and_class where class_id = #{classId} and task_id = #{taskId}")
	public Integer deleteClassTask(Integer classId,Integer taskId);
	
	/**
	 * 	查询作业绑定班级数量
	 * @param taskId
	 * @return
	 */
	@Select("select count(*) from task_and_class where task_id = #{taskId}")
	public Integer queryClassTaskCount(Integer taskId);
	
	/**
	 * 	查询任务发布的班级
	 * @param taskId
	 * @return
	 */
	@Select("select * from class where class_id in (select class_id from task_and_class where task_id = #{taskId})")
	public List<MyClass> queryTaskClassByTaskId(Integer taskId);
	
	/**
	 * 	解除所有班级的绑定
	 * @param taskId
	 * @return
	 */
	@Delete("delete from task_and_class where task_id = #{taskId}")
	public Integer deleteTaskBangdingClass(Integer taskId);
}
