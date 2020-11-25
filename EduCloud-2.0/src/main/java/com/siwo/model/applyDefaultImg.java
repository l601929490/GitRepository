package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class applyDefaultImg implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer imgId;
	
	private String img;
	
	private Integer schoolId;
	
	private Integer checked;
}
