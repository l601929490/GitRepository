package com.siwo.controller;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.commons.FileUntis;
import com.siwo.commons.IseDemo;
import com.siwo.service.SpeechTopicService;
import com.siwo.commons.MP3ToWav;
import com.siwo.model.SpeechTopic;

@RestController
public class SpeechTopicController {

	@Autowired 
	private SpeechTopicService service;
	
	@GetMapping("/applet/speechEvaluation")
	public Map<String, Object> test(String ent,String text,Integer studentId,Integer companyId,Integer teacherId,Integer teachingMaterialId, String filePath,String fileUrl,HttpServletRequest req){
		
		System.out.println("语音测评");
		IseDemo iseDemo = new IseDemo();
		
		String path = FileUntis.m4aToMp3(filePath,companyId);
		File file3 = new File("C:/myFile/siwo/"+companyId+"/"+System.currentTimeMillis()+".wav");
		MP3ToWav.byteToWav(MP3ToWav.getBytes(path), file3.getAbsolutePath());
		
		Map<String, Object> map = iseDemo.star(ent, text, file3.getAbsolutePath());
		
		File file = new File(path);
		file.delete();
		file3.delete();
		
		
		int yesNo = 0;
		int language = 1;
		if (teachingMaterialId != null) {
			yesNo = 1;
		}
		if (ent.equals("en_vip")) {
			language = 2;
		}
		SpeechTopic speechTopic = new SpeechTopic(null, teacherId, studentId, teachingMaterialId, fileUrl, (String)map.get("totalScore"),
				(String)map.get("accuracy_score"), (String)map.get("fluency_score"), (String)map.get("integrity_score"), text, yesNo,language,new Date());
		service.addSpeechTopic(speechTopic);
		return map;
	}
}
