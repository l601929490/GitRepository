package com.siwo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreGoodsType {
	private Integer typeId;//类型id
	private String typeName;//类型名称
	private Integer schoolId;//所属校区
	private Integer num;//类型下面的商品数量
	private String schoolName;//校区名称
}
