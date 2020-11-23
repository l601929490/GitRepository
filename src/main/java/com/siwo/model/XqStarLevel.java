package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XqStarLevel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer xqStarLevelId;
	
	private Integer xqTypeId;
	
	private Integer xqStar;
	
	private Integer xqLevel;
}
