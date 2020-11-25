package com.siwo.model;

import java.util.List;

import org.aspectj.internal.lang.annotation.ajcPrivileged;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreOrderAndGoods {
	private String shoppingOrderId;//订单号
	private Integer shoppingGoodsId;//商品id
	private Integer shoppingGoodsNum;//商品数量
	private  ScoreGoods  goods;//商品信息
	private Integer price;
}
