package com.siwo.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreGoods {
	private Integer liushui;//
	
	private Integer goodsId;//商品id
	private String goodsName;//商品名称----
	private Integer goodsScore;//兑换所需要的积分----
	private String goodsImg;//商品图片--
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date goodsCreateTime;//上架时间
	private Integer goodsType;//商品类型-----
	private String goodsDetail;//商品详情----
	private String goodsRule;//商品规则
	private Integer goodsSchoolId;//校区id----
	private School goodsSchool;//所属校区
	private ScoreGoodsNum goodsNum;//详情----
	private List<ScoreImg> seImgs;//详情图片 ----
	private List<ScoreImg> lbtSeImgs;//轮播图图片---
	private Integer status;
	private Integer goodsDisplay;//是否显示----
	private String typeName;//分类名称
	private String schoolName;//机构名称
}
