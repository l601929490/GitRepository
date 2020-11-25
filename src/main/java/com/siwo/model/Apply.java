package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer applyId;
	
	//	报名主题
	@Excel(name = "报名主题",orderNum = "6")
	private String applyTitle;
	
	//	校园轮播图
	private String applySlideshow;
	
	//	详情图
	private String applyImg;
	
	//	是否展示
	private Integer applyShow;
	
	//	培训类型
	@Excel(name = "报名类型",orderNum = "6")
	private String subject;
	
	private String applyCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date applyCreationTime;
	
	private String applyEditor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date applyEditSession;
	
	private Integer schoolId;
	
	//	开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date applyStartTime;
	
	//	截止时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date applyStopTime;
	
	private List<String> subjects;
	
	//	活动地址
	private String applyAddress;
	//	负责人
	private String applyPrincipal;
	//	联系电话
	private String applyPhone;

}
