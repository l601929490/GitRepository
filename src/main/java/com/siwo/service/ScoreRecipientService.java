package com.siwo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.ScoreRecipient;

public interface ScoreRecipientService {
	 

	public Map<String, Object> changeScoreRecipient(String[] strs, String flag);

	public Map<String, Object> queryRecipientByTeacherId(Integer teacherId);
	
	 
}
