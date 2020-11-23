package com.siwo.model;

import java.io.Serializable;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 	超级管理员
 * @author EDZ
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdmin implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer adminId;
	
	private String adminName;
	
	private String adminPassword;
	
	private String superAdminPermission;
	
}
