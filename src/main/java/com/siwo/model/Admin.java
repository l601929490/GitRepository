package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer adminId;
	
	private String adminRole;
	
	private String adminAccount;
	
	private String adminPassword;
	
	private String available;
	
	private int isSuperAdmin;
	
	private Integer showSchoolId;
	
	private List<String> permissions;
	
	private String adminName;
	
	private String companyName;
	
	private String creator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creationTime;
	
	private String editor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date editSession;
	
}
