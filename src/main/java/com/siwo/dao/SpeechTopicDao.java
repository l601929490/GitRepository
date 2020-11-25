package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.SpeechTopic;

@Mapper
public interface SpeechTopicDao {

	@Insert("INSERT INTO `speech_topic`(`teacher_id`, `student_id`, `teaching_material_id`, `recording`, `total_score`, `accuracy_score`, `fluency_score`, `integrity_score`, `text`, `yes_no`, `language`, `creation_time`) "
			+ "VALUES (#{teacherId}, #{studentId}, #{teachingMaterialId}, #{recording}, #{totalScore}, #{accuracyScore}, #{fluencyScore}, #{integrityScore}, #{text}, #{yesNo}, #{language},#{creationTime})")
	public Integer addSpeechTopic(SpeechTopic speechTopic);
	
	@Select("select * from speech_topic where student_id = #{studentId}")
	public List<SpeechTopic> querySpeechTopicsByStudentId(Integer studentId);
}
