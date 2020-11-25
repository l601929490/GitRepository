package com.siwo.service;

import java.util.Map;

public interface ScorePayService {

	Map<String, Object> queryPayByStudentId(Integer studentId);

	Map<String, Object> queryPayByorderNum( String orderNum);

}
