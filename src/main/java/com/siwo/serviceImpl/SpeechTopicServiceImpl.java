package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.SpeechTopicDao;
import com.siwo.model.SpeechTopic;
import com.siwo.service.SpeechTopicService;

@Service
public class SpeechTopicServiceImpl implements SpeechTopicService{

	@Autowired
	private SpeechTopicDao dao;
	
	@Override
	public Map<String, Object> addSpeechTopic(SpeechTopic speechTopic) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.addSpeechTopic(speechTopic);
			map.put("code", 0);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "添加失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> querySpeechTopicsByStudentId(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SpeechTopic> speechTopics = dao.querySpeechTopicsByStudentId(studentId);
			map.put("code", 0);
			map.put("data", speechTopics);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "添加失败");
			return map;
		}
		return map;
	}

}
