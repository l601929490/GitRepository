package com.siwo.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreGoodsDetail {
	private Integer detailId;//积分明细id
	private Integer studentId;//学生id
	private String detailName;//通过什么方式
	private String detailTypeName;//简写
	private Integer detailScore;//积分
	private Integer detailFlag;//消费还是获取
	private Integer detailAttendance;//标志
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date detailCreateTime;//什么时间
	private Integer scoreSum;//总积分
	private Integer schoolId;//校区id
	private String schoolName;//校区名称
	private String studentName;//学生姓名
}
