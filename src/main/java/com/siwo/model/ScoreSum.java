package com.siwo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreSum {
	private Integer sumId;//总积分id
	private Integer studentId;//学生id
	private Integer sum;//总计分
	private Integer consumption;//消费的积分
	private Integer remain;//剩余积分
	private String studentName;//学生姓名
	private String schoolName;//校区名
}
