package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer activityId;
	
	private String activityPreviewImg;

	private String activityIntro;
	
	private List<String> activityImg;
	
	private String activityCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date activityCreationTime;
	
	private String activityEditor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date activityEditSession;
	
	private Integer schoolId;
	
	//	点赞数量
	private Integer activityLikeCount;
	
	//	是否点过赞 0否 1是
	private Integer isLike;
}
