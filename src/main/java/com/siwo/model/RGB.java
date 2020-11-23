package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RGB implements Serializable{

	private static final long serialVersionUID = 1L;

	private int red;

	private int green;

	private int blue;
}
