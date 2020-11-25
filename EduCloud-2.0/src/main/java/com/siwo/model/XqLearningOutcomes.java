package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XqLearningOutcomes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer xqLearningOutcomesId;
	
	private Integer classId;
	
	private Integer studentId;
	
	private Integer xqTypeId;
	
	private Integer classDayId;
	
	private Integer star;
	
	private Date commentTime;
	
	private String comment;
	
	private List<XqTypeStar> xqTypeStars;
}
