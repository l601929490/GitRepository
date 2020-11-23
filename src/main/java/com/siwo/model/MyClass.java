package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	班级
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyClass implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer classId;
	
	@Excel(name = "学生班级")
	@NotBlank(message = "班级名称不能为空")
	private String className;
	
	//	班级类别
	private String classCategory;
	
	//	班级级别
	private String classLevel;
	
	//	总课时
	@NotNull(message = "总课时不能为空")
	private int classHour;
	
	//	开班时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@NotNull(message = "开班时间不能为空")
	private Date promotionTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@NotNull(message = "毕业时间不能为空")
	private Date graduationTime;
	
	private Teacher teacher;

//	@NotNull(message = "老师Id不能为空")
//	private Integer teacherId;
	
	//	班级头像
	private String classImg;
	
	private Integer schoolId;
	
	private Student student;
	
	private String classCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date classCreateTime;
	
	private String classEditor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date classEditSession;
	
	//	班级状态
	private Integer state;
	
	//	未完成作业数量
	private Integer unfinished;
	
}
