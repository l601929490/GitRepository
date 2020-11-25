package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletinBoard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer bulletinBoardId;
	
	private String content;
	
	private Integer checked;
	
	private Integer companyId;

}
