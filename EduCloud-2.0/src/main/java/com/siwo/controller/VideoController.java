package com.siwo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Video;
import com.siwo.service.VideoService;

@RestController
public class VideoController {

	@Autowired
	private VideoService service;
	
	@PostMapping("/addCourseVideo")
	public Map<String, Object> addVideo(@RequestBody Video viderAddress){
		return service.addVideo(viderAddress);
	}
	
	@GetMapping("/applet/getCourseVideoByBriefId")
	public Map<String, Object> querybyBriefId(Integer briefId){
		return service.querybyBriefId(briefId);
	}
	
	@GetMapping("/applet/getCourseVideoByVideoId")
	public Map<String, Object> queryByVideoId(Integer videoId){
		return service.queryByVideoId(videoId);
	}
	
	@GetMapping("deleteVideo")
	public Map<String, Object> deleteManyVideo(String videoIds){
		Map<String, Object> map = null;
		
		String[] ids = videoIds.split(",");
		
		if (ids.length == 1) {
			int videoId = Integer.parseInt(videoIds);
			map = service.deleteVideo(videoId);
		} else if (ids.length > 1) {
			map = service.deleteManyVideo(ids);
		}
		return map;
	}
	
	@PostMapping("/updateVideo")
	public Map<String, Object> updateVideo(@RequestBody Video address){
		return service.updateVideo(address);
	}
	
	@GetMapping("/limitVideo")
	public Map<String, Object> limitVideo(Integer page, Integer limit, Integer schoolId,String videoTitle) {
		if (StringUtils.isEmpty(videoTitle)) {
			return service.limitVideo(page, limit, schoolId);
		}else {
			//	按视频标题模糊查询
			return service.queryVideoByVideoTitle(schoolId, videoTitle);
		}
		
	}
	
	@PostMapping("/bindingVideoBrief")
	public Map<String, Object> bindingVideoBrief(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String videoIds =(String)map.get("videoIds");
		String briefIds = (String)map.get("briefIds");
		Integer oneToMany = (Integer)map.get("oneToMany");
		String[] ids =null;
		String[] ids_2 =null;
		
		if (StringUtils.isEmpty(briefIds) && StringUtils.isEmpty(videoIds)) {
			result.put("code", 0);
			result.put("msg", "操作成功");
			return result;
		}else {
			if (briefIds != null) {
				ids = briefIds.split(",");
			}
			
			if (videoIds != null) {
				ids_2 = videoIds.split(",");
			}
		}
		return service.bindingVideoBrief(ids_2, ids, oneToMany);
	}
	
	@GetMapping("/queryVideoBindingBrief")
	public Map<String, Object> queryVideoBindingBrief(Integer videoId) {
		return service.queryVideoBindingBrief(videoId);
	}

}
