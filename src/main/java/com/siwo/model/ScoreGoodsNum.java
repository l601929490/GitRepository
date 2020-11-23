package com.siwo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreGoodsNum {
	private Integer goodsNumId;//商品详情id
	private Integer goodsNumSum;//商品总数----
	private Integer goodsNumSale;//销量
	private Integer goodsNumRemain;//库存
	private Integer goodsId;//商品id
	private Integer goodsNumBasicSale;//基本销量
}
