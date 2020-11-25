package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XqTypeStar implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer xqLearningOutcomesId;
	
	private Integer xqTypeId;
	
	private Integer star;
	
	private XqType xqType;
}
