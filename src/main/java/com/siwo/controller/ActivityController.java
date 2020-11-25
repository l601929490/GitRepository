package com.siwo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Activity;
import com.siwo.service.ActivityService;

@RestController
public class ActivityController {

	@Autowired
	private ActivityService service;
	
	@PostMapping("/addActivity")
	public Map<String, Object> addActivity(@RequestBody Activity activity){
		activity.setActivityEditSession(new Date());
		return service.addActivity(activity);
		
	}
	
	@PostMapping("/addActivityImg")
	public Map<String, Object> addActivityImg(@RequestBody Map<String, Object> map){
		Integer activityId = (Integer)map.get("activityId");
		@SuppressWarnings("unchecked")
		List<String> activity_img = (List<String>)map.get("activity_img");
		return service.addActivityImg(activityId, activity_img);
	}
	
	@GetMapping("/limitActivitie")
	public Map<String, Object> limitActivitie(Integer page,Integer limit,Integer schoolId){
		return service.limitActivitie(page, limit, schoolId);
	}
	
	@GetMapping("/applet/queryActivitieBySchoolId")
	public Map<String, Object> wxqueryActivitieBySchoolId(Integer schoolId,String openId){
		return service.queryActivitieBySchoolId(schoolId,openId);
	}
	
	@GetMapping("/queryActivitieBySchoolId")
	public Map<String, Object> queryActivitieBySchoolId(Integer schoolId){
		return service.queryActivitieBySchoolId(schoolId,null);
	}
	
	@GetMapping("/applet/queryActivityByActivityId")
	public Map<String, Object> queryActivityByActivityId(Integer activityId){
		return service.queryActivityByActivityId(activityId);
	}
	
	@GetMapping("/deleteActivity")
	public Map<String, Object> deleteActivity(String activityIds){
		Map<String, Object> map = null;
		
		String[] ids = activityIds.split(",");
		
		if (ids.length == 1) {
			int activityId = Integer.parseInt(activityIds);
			map = service.deleteActivityByActivityId(activityId);
		} else if (ids.length > 1) {
			map = service.deleteManyActivity(ids);
		}
		return map;
	}
	
	@PostMapping("/updateActivity")
	public Map<String, Object> updateActivity(@RequestBody Activity activity){
		return service.updateActivity(activity);
	}
	
	@PostMapping("/applet/addActivityLike")
	public Map<String, Object> addActivityLike(Integer activityId, String openId,Integer isLike) {
		return service.addActivityLike(activityId, openId, isLike);
	}
}
