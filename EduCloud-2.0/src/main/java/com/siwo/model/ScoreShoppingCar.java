package com.siwo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreShoppingCar {
	private Integer shoppingCarId;//购物车id
	private Integer studentId;//学生id
	private ScoreGoods goods;//商品表
	private Integer shoppingNum;//购物车商品数量
	private Integer isSelected;//是否选中
}
