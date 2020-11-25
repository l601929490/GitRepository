package com.siwo.model;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyMessageStudent implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer applyMessageInfoId;
	
	@Excel(name = "学生姓名",orderNum = "1")
	private String applyMessageStudentName;
	
	@Excel(name = "学生性别",orderNum = "2")
	private String applyMessageStudentSex;
	
	@Excel(name = "学生年龄",orderNum = "3")
	private Integer applyMessageStudentAge;
	
	//	是否已报名
	private Integer isSignUp;
	
	private Integer applyMessageId;
	
	@ExcelEntity
	private ApplyMessage message;
	
	@ExcelEntity
	private Apply apply;
}
