package com.siwo.model;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAndGuardian implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer studentId;
	
	@Excel(name = "电话")
	private String guardianPhone;
}
