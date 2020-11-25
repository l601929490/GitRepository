package com.siwo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	咨询
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consult {

	private Integer consultId;
	
	/**
	 * 	咨询标题
	 */
	private String consultTitle;
	
	/**
	 * 	副标题
	 */
	private String consultSubhead;
	
	/**
	 * 	咨询图片
	 */
	private String subheadImg;
	
	/**
	 * 	咨询文本
	 */
	private String subheadText;
	
	@DateTimeFormat(pattern  = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date time;
	
	/**
	 * 	咨询阅读量
	 */
	private int readConsult;
	
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
