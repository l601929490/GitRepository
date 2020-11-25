package com.siwo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreImg {
	private Integer imgId;//商品详情图片id
	private String imgName;//地址---
	private Integer goodsId;//商品id
	private Integer imgType;//是轮播图还是  下面的详情图片 0为 轮播图 1为下面的详情图片
}
	