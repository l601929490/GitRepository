package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachingMaterialContent implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer teachingMaterialContentId;
	
	private String vanRead;
	
	private String content;
	
	private Integer teachingMaterialId;
}
