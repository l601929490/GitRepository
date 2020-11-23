package com.siwo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScoreTypePage {
	private Integer schoolId;
	private String name;
	private Integer pageNo;
}
