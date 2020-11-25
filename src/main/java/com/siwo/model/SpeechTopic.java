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
public class SpeechTopic implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer speechTopicId;
	
	private Integer teacherId;
	
	private Integer studentId;
	
	private Integer teachingMaterialId; 
	
	private String recording;
	
	private String totalScore;
	
	private String accuracyScore;
	
	private String fluencyScore;
	
	private String integrityScore;
	
	private String text;
	
	private Integer yesNo;
	
	private Integer language;
	
	@DateTimeFormat(style = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creationTime;
}
