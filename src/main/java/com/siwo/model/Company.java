package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Company implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer companyId;
	
	@NotBlank(message = "机构名不能为空")
	private String companyName;
	
	@NotNull(message = "分校区数量不能为空")
	private Integer companyCampus;
	
	
	private String companyCreator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date companyCreationTime;
	
	private String companyEditor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date companyEditSession;
	
	private Double companyCharge;
	
	@NotBlank(message = "负责人不能为空")
	private String companyPrincipal;
	
	@NotBlank(message = "负责人电话不能为空")
	private String companyPrincipalPhone;
	
	private Integer adminId;
	
	private Integer showSchoolId;
	
	private Integer wxShowSchoolId;
	
	private String companyAppId;
	
	private String companyAppSecret;
	
	private String companyQrcode;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endTime;
}
