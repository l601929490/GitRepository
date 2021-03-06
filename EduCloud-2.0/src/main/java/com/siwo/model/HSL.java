package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HSL implements Serializable {

	private static final long serialVersionUID = 1L;

	private float h;
	/** 饱和度 */
	private float s;
	/** 深度 */
	private float l;
}
