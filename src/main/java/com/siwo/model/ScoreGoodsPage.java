package com.siwo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScoreGoodsPage {
	
	private Integer schoolId;//校区
	private Integer typeId;//类型
	private String name;//名称
	private Integer pageNo;//页数
}
