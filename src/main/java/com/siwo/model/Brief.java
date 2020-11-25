package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	课程表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brief implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer briefId;

	// 课程标题
	private String briefContent;

	// 课堂内容
	private String courseContent;
	
//	// 上传的时间
//	@DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//	private Date briefTime;

	// 是否是精选课
	@NotBlank(message = "课程种类不能为空")
	private String briefWhether;

	// 图片链接
	private String briefImg;

//	@NotNull(message = "班级Id不能为空")
	private Integer classId;
	
	private List<MyClass> myClasses;
	
	private List<Video> video;
	
	private Integer schoolId;
	
	private String creator;
	
	@DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creationTime;
	
	private String editor;
	
	@DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date editSession;
}
