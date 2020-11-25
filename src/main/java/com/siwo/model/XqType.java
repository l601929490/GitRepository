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
@NoArgsConstructor
@AllArgsConstructor
public class XqType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer xqTypeId;
	
	private String xqTypeName;
	
	private Integer schoolId;
	
	private Integer defaultStar;
	
	private Integer star;
	
//	private List<XqStarLevel> xqStarLevels;
	
	private String creator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date start;
	
	private String editor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date last;
}
