package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	课程表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer courseId;
	
	//	课程文字简介
	private String courseContent;
	
	//	内容配图链接
	private String courseImg;
	
	private Integer briefId;

}
