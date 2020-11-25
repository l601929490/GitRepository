package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.ApplyMessage;

public interface ApplyMessageService {

	public Map<String, Object> addApplyMessage(ApplyMessage message,BindingResult result);

	public Map<String, Object> limitApplyMessage(Integer page,Integer limit,Integer schoolId);
}
