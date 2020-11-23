package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceComment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer commentAttendanceId;
	
	@NotNull(message = "attendanceId不能为空")
	private Integer attendanceId;
	
	@NotNull(message = "openId不能为空")
	private String openId;
	
	private String comment;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date commentTime;
	
	private String recording;
	
	private Double recordingTime;
	
	//	是否为私密留言
	private Integer isSecret;
	
	@NotNull(message = "teacherId不能为空")
	private Integer teacherId;
	
	private Teacher teacher;
}
