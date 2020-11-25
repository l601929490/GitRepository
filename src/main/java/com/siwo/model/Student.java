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
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer studentId;
	
//	@Excel(name = "学生姓名",orderNum = "1",needMerge = true)
	@NotBlank(message = "学生姓名不能为空")
	private String studentName;
	
//	@Excel(name = "学生年龄",orderNum = "2",needMerge = true)
	@NotNull(message = "学生年龄不能为空")
	private Integer studentAge;
	
//	@Excel(name = "学生性别",orderNum = "3",needMerge = true)
	@NotBlank(message = "学生性别不能为空")
	private String studentSex;
	
//	@Excel(name = "手机号码",orderNum = "4",needMerge = true)
	private String studentPhone;
	
//	@Excel(name = "所在机构",orderNum = "5",needMerge = true)
	private String schoolName;
	
	//	真实学校名称
//	@Excel(name = "学校名称",orderNum = "6",needMerge = true)
	private String schoolTitle;
	
	//	几年级
//	@Excel(name = "所在校区",orderNum = "7",needMerge = true)
	private String grade;
	
	@ExcelCollection(name = "")
	private List<Guardian> guardians;
	
	private String studentClassName;
	
	//	打卡总记录
	private int count;
	
	private Integer studentClassId;
	
	private Integer schoolId;
	
	private String studentCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date studentCreationTime;
	
	private String studentEditor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date studentEditSession;
	
	//	积分
	private Integer integral;
	
	private MyClass myClass;
	
	private List<MyClass> myClasses;
	
	private School school;
	//	打卡天数
	private int fate;
	
	//	学生状态
	private Integer state;
	
	//	学情是否点评
	private Integer isMark;
	
	//	学生学情
	private List<XqType> xqTypes;
	
	//	月度评价
	private XqMonthly xqMonthly;
	
	//	头像
	private String rawData;

}
