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
public class TeachingMaterial implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer teachingMaterialId;
	
	//	年级
	private Integer grade;
	
	//	学科
	private Integer subject;

	//	标题
	private String title;

	//	内容
	private List<TeachingMaterialContent> contents;

	//	范读录音
//	private String vanRead;
	
	private Integer schoolId;
	
	private String creator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creationTime;
	
	private String editor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date editSession;
}
