package com.siwo.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author jiale xu
 * 获取积分
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreGetScore {
	private Integer getScoreId;//id
	private Integer studentId;//学生id
	private String ruleName;//通过什么方式打的卡
	private Integer attendanceId;//
	private Integer ruleScore;//获取的积分
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;//创建时间
}
