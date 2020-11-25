package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResultBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS = 0;
	public static final int FAIL = -1;
	
	private String msg = "SUCCESS";
	private int code = SUCCESS;
	private T data;
	private String errorMsg;
}
