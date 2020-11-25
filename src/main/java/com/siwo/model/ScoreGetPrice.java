package com.siwo.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//	班级
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreGetPrice implements Serializable{

	private Integer goodsId;//商品id
	private Integer goodsScore;//积分
	
}
