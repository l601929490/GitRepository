package com.siwo.service;

import java.util.Map;

import com.siwo.model.SpeechTopic;

public interface SpeechTopicService {
	
	public Map<String, Object> addSpeechTopic(SpeechTopic speechTopic);
	
	public Map<String, Object> querySpeechTopicsByStudentId(Integer studentId);
}
