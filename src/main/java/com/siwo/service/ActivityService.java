package com.siwo.service;

import java.util.List;
import java.util.Map;


import com.siwo.model.Activity;

public interface ActivityService {

	public Map<String, Object> addActivity(Activity activity);
	
	public Map<String, Object> addActivityImg(Integer activityId, List<String> activity_img);
	
	public Map<String, Object> limitActivitie(Integer page,Integer limit,Integer schoolId);
	
	public Map<String, Object> queryActivitieBySchoolId(Integer schoolId,String openId);
	
	public Map<String, Object> queryActivityByActivityId(Integer activityId);
	
	public Map<String, Object> deleteActivityByActivityId(Integer activityId);
	
	public Map<String, Object> deleteManyActivity(String[] activityIds);
	
	public Map<String, Object> updateActivity(Activity activity);

	public Map<String, Object> addActivityLike(Integer activityId,String openId,Integer isLike);
}
