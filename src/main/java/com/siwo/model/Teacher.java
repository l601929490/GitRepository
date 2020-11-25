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

//	老师
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer teacherId;
	
	@NotBlank(message = "老师名不可以为空")
	private String teacherName;
	
	//	老师手机号
	@NotBlank(message = "手机号不能为空")
	private String teacherPhone;
	
	@NotNull(message = "年龄不能为空")
	private Integer teacherAge;
	
	@NotBlank(message = "手机号不能为空")
	private String teacherSex;

	@NotBlank(message = "头像不能为空")
	private String teacherImg;
	
	//	简介
	private String teacherIntro;
	
	//	毕业院校
	//@NotBlank(message = "毕业院校不能为空")
	private String teacherCollege;
	//	教龄
	private Integer teacherWorkYear;
	
	private String teacherCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date teacherCreationTime;
	
	private String teacherEditor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date teacherEditSession;
	
	private Integer schoolId;
	
	private Integer classId;
	
	private List<MyClass> myClasses;
	
	private int teacherShow;
	
	private String adminPassword;
	
	//	在职状态
	private String status;
	
	//是否为核销员
	private String flag;
}
