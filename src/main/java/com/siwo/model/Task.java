package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	任务、作业
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer taskId;	
	
	//	内容中的文本
	private String taskText;

	//	内容中的视频
	private List<String> taskVideo;
	
	//	内容中的图片
	private List<String> taskImg;
	
	//	录音
	private List<TaskRecording> recording;
	
	//	打开截止时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
	private Date taskStopTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date createTime;
	
	//	发起人姓名
//	@NotBlank(message = "发起人姓名不能为空")
	private String sponsorName;
	
	//	完成人数
	private int okNum;
	
	//	未完成人数
	private int notNum;
	
	//	任务时间是不是到了
	private int timing;
	
	//	任务所在班级
	@NotNull(message = "classId不能为空")
	private List<Integer> classIds;
	
	private Integer classId;
	
	//	发布的老师
	@NotNull(message = "teacherId不能为空")
	private Integer teacherId;
	
	private Teacher teacher;
	
	//	是否定时发布
	@NotNull(message = "未选择是否定时发布")
	private int isTiming;
	
	//	定时发布的时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date timingTime;
	
	//	是否允许补交作业
	@NotNull(message = "未选择是否可以补交作业")
	private Integer allowJob;
	
	//	作业科目
	private String taskSubject;
	
	//	可见性
	private int visible;
	
	//	整体点评
	private String comment;
	
	//	是否是长期任务
	@NotNull(message = "未选择任务类型")
	private Integer longTermUse;
	
	//	自己的打卡评论
//	private List<Attendance> attendances;
	
	//	自己的打卡评论
	private Attendance attendance;
	
	//	是否过期 0没过期 1已过期
	private Integer pastDue;
	
	private MyClass myClass;
	
	private List<MyClass> myClasses;
}
