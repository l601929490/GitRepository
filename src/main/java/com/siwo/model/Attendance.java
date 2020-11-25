package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	打卡记录表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer attendanceId;

	//	打卡时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date attendanceTime;
	
	//	打卡文本
	private String attendanceComment;
	
	//	视频链接
	private List<String> attendanceVideo;
	
	//	图片链接
	private List<String> attendanceImg;
	
	//	录音
	private List<AttendanceRecording> recordings;
	
	//	用户手机号
	@NotNull(message = "手机号不能为空")
	private String phone;
	
	//	任务id
	@NotNull(message = "taskId不能为空")
	private Integer taskId;
	
	//	作业状态
	private Integer isAffirm;
	
	//	微信信息
	private String rawData;
	
	//	班级id
	@NotNull(message = "班级id不能为空")
	private Integer classId;
	
	//	点赞人姓名
	private List<String> likeAttendanceName;
	
	//	是否订正
	private Integer correct;
	
	//	老师打分
	private String score;
	
	//	是否补交
	private Integer makeUp;
	
	@NotNull(message = "学生id不能为空")
	private Integer studentId;
	
	//	评论
	private List<AttendanceComment> comments;
	
//	private Guardian guardian;
	
	private Student student;
	
	//	小程序二维码
	private Company company;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attendance other = (Attendance) obj;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}
	
	
	
}
