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
@AllArgsConstructor
@NoArgsConstructor
public class ApplyMessage implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer applyMessageId;
	
	@Excel(name = "家长姓名",orderNum = "4")
	@NotBlank(message = "家长姓名不能为空")
	private String applyMessagePatriarch;
	
	@Excel(name = "家长电话",orderNum = "5")
	@NotBlank(message = "手机号不能为空")
	private String applyMessagePhone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date applyMessageTime;
	
	@NotNull(message = "报名活动Id不能为空")
	private Integer applyId;
	
	@NotNull(message = "报名学生不能为空")
	private List<ApplyMessageStudent> students;
	
	@NotNull(message = "学区Id不能为空")
	private Integer schoolId;
	
	private Apply apply;
}
