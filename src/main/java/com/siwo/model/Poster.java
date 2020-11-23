package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Poster implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer posterId;
	
	@NotBlank(message = "背景图不能为空")
	private String posterBackgroundImage;
	
//	@NotBlank(message = "谚语不能为空")
	private String posterProverb;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date posterCreateTime;
	
	private String posterCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date posterEditSession;
	
	private String posterEditor;
	
	private int posterChecked;
	
	private Integer schoolId;
	
	//	1 打卡     2 活动
	private Integer code;
}
