package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamicCondition implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer dynamicConditionId;
	
	/**
	 * 签到积分规则
	 */
	private Integer loginDynamicCondition;
	
	/**
	 * 打卡积分规则
	 */
	private Integer clockDynamicCondition;
	/**
	 * 看视频学习规则
	 */
	private Integer videoDynamicCondition;
	/**
	 * 分享积分规则
	 */
	private Integer shareDynamicCondition;
	/**
	 * 点赞积分规则
	 */
	private Integer likeDynamicCondition;
	/**
	 * 老师点评规则
	 */
	private Integer reviewDynamicCondition;
	/**
	 * 优秀作业
	 */
	private Integer goodDynamicCondition;
	/**
	 * 所在校区
	 */
	private Integer schoolId;
}
