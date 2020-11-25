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
public class Suggest implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer suggestId;
	
	@NotBlank(message = "标题不能为空")
	private String suggestTitle;
	
	@NotBlank(message = "投诉内容不能为空")
	private String suggestContent;
	
	@NotBlank(message = "电话不能为空")
	private String suggestPhone;
	
	@NotNull(message = "schoolId不能为空")
	private Integer schoolId;
	
	private Integer status;
	
	private String creator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creationTime;
	
	private String editor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date editSession;
}
