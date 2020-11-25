package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XqMonthly implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer xqMonthlyId;
	
	private Integer studentId;
	
	private String remark;
	
	@DateTimeFormat(pattern = "yyyy-MM")
	@JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
	private Date month;
	
	private Integer teacherId;

	private Integer classId;
}
