package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guardian implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer guardianId;
	
	//	监护人姓名
	@Excel(name = "监护人姓名")
	@NotBlank(message = "监护人信息不能为空")
	private String guardianName;
	
	//	监护人电话
	@Excel(name = "监护人电话")
	@NotBlank(message = "监护人电话不能为空")
	private String guardianPhone;
	
//	private String guardianStudentName;
	
	//	与学生的关系
	@Excel(name = "亲属关系")
	private String relationship;
	
	private Integer guardianStudentId;
	
	private List<Integer> guardianStudentIds;
	
//	@Excel(name = "所在校区")
	private String schoolName;
	
	
	@NotNull(message = "schoolId不能为空")
	private Integer schoolId;
	
	private String guardianCreator;
	
	@DateTimeFormat(pattern  = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date guardianCreationTime;
	
	private String guardianEditor;
	
	@DateTimeFormat(pattern  = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date guardianEditSession;
	
	private Student attendanceStudent;
	
	private List<Student> students;
	
	
}
