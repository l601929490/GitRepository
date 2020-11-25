package com.siwo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScoreOrderPage {
	
	private Integer schoolId;//校区
	private String phone;//类型
	private Integer flag;//名称
	private Integer pageNo;//页数
}
