package com.siwo.model;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRecipient {
	private Integer recipientId;//收件人id
	private Integer teacherId;//收货老师id
}
