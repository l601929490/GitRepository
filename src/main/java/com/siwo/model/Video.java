package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer videoId;
	//	次数
	private Integer videoSum;
	//	标题
	private String videoTitle;
	//	视频时长
	private String videoTime;
	//	视频链接
	private String videoAddress;
	//	图片
	private String videoImg;
	
	private String videoMsg;
	//	课程id
	private Integer briefId;
	//	课程简介
	private String briefContent;
	
	private String videoCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date videoCreationTime;
	
	private String videoEditor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date videoEditSession;
	
	private Integer schoolId;

	private Brief brief;
	
	private Set<MyClass> myClasses;
	
	private String recording;
}
