package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIntegral implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer integralId;
	
	private int IntegralSum;
	
	private String userPhone;
	
	private Date integralTime;
	
	private Integer attendanceId;

	private Integer isShare;
}
