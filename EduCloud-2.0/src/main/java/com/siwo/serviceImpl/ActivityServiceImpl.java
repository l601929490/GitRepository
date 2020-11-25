package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ActivityDao;
import com.siwo.model.Activity;
import com.siwo.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	private ActivityDao dao;
	
	@Override
	public Map<String, Object> addActivity(Activity activity) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (activity == null) {
			map.put("code", 1);
			map.put("msg", "请填写数据");
			return map;
		}
		activity.setActivityCreationTime(new Date());
		Integer row = dao.addActivity(activity);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		} else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> addActivityImg(Integer activityId, List<String> activity_img) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (activityId == null || activity_img == null) {
			map.put("code", 1);
			map.put("msg", "添加失败");
			return map;
		}
		
		try {
			for (String img : activity_img) {
				dao.addActivityImg(img,activityId);
			}
			map.put("code", 0);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "添加失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> limitActivitie(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer start = (page - 1) * limit;

		List<Activity> activities = dao.limitActivitie(start,limit,schoolId);

		for (Activity activity : activities) {
			List<String> imgs = dao.queryActivityImgByActivityId(activity.getActivityId());
			activity.setActivityImg(imgs);
		}
		
		int count = dao.getActivitieCount(schoolId);

		if (activities != null) {
			map.put("code", 0);
			map.put("data", activities);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> queryActivitieBySchoolId(Integer schoolId,String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (schoolId == null) {
			map.put("code", 1);
			map.put("data", "schoolId不能为空");
			return map;
		}
		
		List<Activity>activities = dao.queryActivitieBySchoolId(schoolId);
		for (Activity activity : activities) {
			Integer count = dao.queryActivityLikeCount(activity.getActivityId());
			activity.setActivityLikeCount(count);
			Integer isLike = dao.queryActivityLikeByOpenId(activity.getActivityId(), openId);
			activity.setIsLike(isLike);
		}
		
		if (activities != null) {
			map.put("code", 0);
			map.put("data", activities);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryActivityByActivityId(Integer activityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (activityId == null) {
			map.put("code", 1);
			map.put("data", "activityId不能为空");
			return map;
		}
		
		Activity activity = dao.queryActivityByActivityId(activityId);
		
		if (activity != null) {
			map.put("code", 0);
			map.put("data", activity);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> deleteActivityByActivityId(Integer activityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer row = dao.deleteActivityByActivityId(activityId);
		dao.deleteActivityImgByActivityId(activityId);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		}else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteManyActivity(String[] activityIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(activityIds != null) {
			Integer row = dao.deleteManyActivity(activityIds);
			dao.deleteManyActivityImg(activityIds);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}else {
			map.put("code", 1);
			map.put("msg", "学生id不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateActivity(Activity activity) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (activity.getActivityId() == null) {
			map.put("code", 1);
			map.put("msg", "ActivityId不能为空");
			return map;
		}
		activity.setActivityEditSession(new Date());
		
		Integer row = dao.updateActivity(activity);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "编辑成功");
			dao.deleteActivityImgByActivityId(activity.getActivityId());
			List<String> list = activity.getActivityImg();
			for (String string : list) {
				dao.addActivityImg(string, activity.getActivityId());
			}
		} else {
			map.put("code", 1);
			map.put("msg", "编辑失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> addActivityLike(Integer activityId, String openId,Integer isLike) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			if (isLike == 0) {
				dao.addActivityLike(activityId, openId);
			}else if (isLike == 1) {
				dao.deleteActivityLike(activityId, openId);
			}
			map.put("code", 0);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "操作失败");
			e.printStackTrace();
		}
		
		return map;
	}


}
