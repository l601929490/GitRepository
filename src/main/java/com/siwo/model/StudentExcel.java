package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	学生信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExcel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer studentId;
	
	@Excel(name = "学生姓名",orderNum = "0",needMerge = true)
	@NotBlank(message = "学生姓名不能为空")
	private String studentName;
	
	@Excel(name = "学生年龄",orderNum = "0",needMerge = true)
	@NotNull(message = "学生年龄不能为空")
	private Integer studentAge;
	
	@Excel(name = "学生性别",orderNum = "0",needMerge = true)
	@NotBlank(message = "学生性别不能为空")
	private String studentSex;
	
	@Excel(name = "手机号码",orderNum = "0",needMerge = true)
	private String studentPhone;
	
	@Excel(name = "所在机构",orderNum = "0",needMerge = true)
	private String schoolName;
	
	@Excel(name = "学校名称",orderNum = "0",needMerge = true)
	private String schoolTitle;
	
	@Excel(name = "学生年级",orderNum = "0",needMerge = true)
	private String grade;
	
	@ExcelEntity
	private Guardian guardian;
	
	@ExcelEntity
	private Guardian guardian2;
	
	@ExcelEntity
	private Guardian guardian3;
	
	@ExcelEntity
	private Guardian guardian4;
	
}
