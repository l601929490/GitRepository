package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	@NotNull(message = "code值不能为空")
	private String code;

	@NotNull(message = "用户数据不能为空")
	private String rawData;

//	@NotBlank(message = "encryptedData不能为空")
	private String encryptedData;

	@NotNull(message = "iv不能空")
	private String iv;

	private String openId;

	private String userPhone;

	private List<Integer> identitys;
	
	private String sessionKey;
	
	@NotBlank(message = "appId不能为空")
	private String appId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date loginTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;
	
	private Teacher teacher;
	
	private List<Student> student;
	
	private Guardian guardian;
	
	private Integer schoolId;

}
