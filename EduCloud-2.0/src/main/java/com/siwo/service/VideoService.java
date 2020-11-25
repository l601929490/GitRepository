package com.siwo.service;

import java.util.List;
import java.util.Map;

import com.siwo.model.Video;

public interface VideoService {

	public Map<String, Object> addVideo(Video viderAddress);
	
	public Map<String, Object> querybyBriefId(Integer briefId);
	
	public Map<String, Object> queryByVideoId(Integer videoId);
	
	public Map<String, Object> deleteVideo(Integer videoId);
	
	public Map<String, Object> deleteManyVideo(String[] videoIds);
	
	public Map<String, Object> updateVideo(Video address);
	
	public Map<String, Object> limitVideo(Integer page, Integer limit,Integer schoolId);
	
	public Map<String, Object> bindingVideoBrief(String[] videoIds, String[] briefIds,Integer oneMany);
	
	public Map<String, Object> queryVideoBindingBrief(Integer videoId);
	
	public Map<String, Object> queryVideoByVideoTitle(Integer schoolId,String videoTitle);
}
