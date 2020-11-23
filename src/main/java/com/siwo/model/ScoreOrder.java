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
public class ScoreOrder {
	@Excel(name = "订单编号",orderNum = "1",needMerge = true)
	private Integer liushui;//流水号
	private Integer orderId;//订单id--
	private Integer studentId;//学生id
	@Excel(name = "订单号",orderNum = "3",needMerge = true)
	private String orderNum;//订单号--
	@Excel(name = "订单数量",orderNum = "4",needMerge = true)
	private Integer goodsNum;//订单数量
	
	private Integer orderFlag;//订单状态---
	@Excel(name = "消耗积分",orderNum = "6",needMerge = true)
	private Integer orderScore;//所用到的积分

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date orderTime; //下单时间---
	
	private Integer schoolId;//收货校区
	@Excel(name = "收货地址",orderNum = "9",needMerge = true)
	private String orderAddress;//收货校区地址
	@Excel(name = "收货手机号",orderNum = "10",needMerge = true)
	private String orderPhone;//收件人手机号
	@Excel(name = "收件人",orderNum = "11",needMerge = true)
	private String orderCustomer;//收件人)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	
	private Date addressTime;//收货时间
	private List<ScoreOrderAndGoods> params;//接收的参数
	private String flag;
	private Integer teacherId;//收件人老师id
	@Excel(name = "订单状态",orderNum = "5",needMerge = true)
	private String statu;//取货状态
	@Excel(name = "收货校区",orderNum = "8",needMerge = true)
	private String schoolName;//取货校区
	@Excel(name = "送货人",orderNum = "13",needMerge = true)
	private String teacherName;//取货员
	@Excel(name = "下单时间",orderNum = "7",needMerge = true)
	private String orderTimes;//下单时间
	@Excel(name = "收货时间",orderNum = "12",needMerge = true)
	private String addressTimes;
//	private String flag;
	 
}
