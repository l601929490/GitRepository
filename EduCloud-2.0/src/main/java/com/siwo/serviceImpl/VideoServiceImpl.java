package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.BriefDao;
import com.siwo.dao.VideoDao;
import com.siwo.model.Brief;
import com.siwo.model.MyClass;
import com.siwo.model.Video;
import com.siwo.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService{

	@Autowired
	private VideoDao dao;
	@Autowired
	private BriefDao briefDao;
	
	@Override
	public Map<String, Object> addVideo(Video viderAddress) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		viderAddress.setVideoCreationTime(new Date());
		
		Integer row = dao.addVideo(viderAddress);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		}else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> querybyBriefId(Integer briefId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Video> list = dao.querybyBriefId(briefId);
		map.put("code", 0);
		map.put("msg", "查询成功");
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> queryByVideoId(Integer videoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Video address = dao.queryByVideoId(videoId);
		Integer sum = address.getVideoSum();
		Integer videoSum = sum+1;
		address.setVideoSum(videoSum);
		dao.updateVideoSum(videoSum, address.getVideoId());
		
		Brief brief = briefDao.queryBriefByBriefId(address.getBriefId());
		
		map.put("code", 0);
		map.put("msg", "查询成功");
		map.put("data", address);
		map.put("brief", brief);
		return map;
	}

	@Override
	public Map<String, Object> deleteVideo(Integer videoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Integer row = dao.deleteVideo(videoId);
		
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
	public Map<String, Object> deleteManyVideo(String[] videoIds) {
		Map<String, Object> map  = new HashMap<String, Object>();
		Integer row = dao.deleteManyVideo(videoIds);
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
	public Map<String, Object> updateVideo(Video address) {
		Map<String, Object> map  = new HashMap<String, Object>();
		
		address.setVideoEditSession(new Date());
		
		Integer row = dao.updateVideo(address);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "更新成功");
		}else {
			map.put("code", 1);
			map.put("msg", "更新失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> limitVideo(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<Video> addresses = dao.limitVideo(start, limit, schoolId);

		for (Video video : addresses) {
			
			List<Brief> briefs = dao.queryVideoBindingBrief(video.getVideoId());
			
			//	所有课程绑定的班级
			List<MyClass> briefClasses = new ArrayList<MyClass>();
			
			for (Brief brief : briefs) {
				video.setBrief(brief);
				video.setBriefContent(brief.getBriefContent());
				List<MyClass> classes = briefDao.queryMyClassByBriefId(brief.getBriefId());
				briefClasses.addAll(classes);
			}
			Set<MyClass> set = new HashSet<MyClass>(briefClasses);
			video.setMyClasses(set);
		}
		
		int count = dao.getVideoCount(schoolId);

		if (addresses != null) {
			map.put("code", 0);
			map.put("data", addresses);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> bindingVideoBrief(String[] videoIds, String[] briefIds,Integer oneMany) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (videoIds == null) {
			for (String id : briefIds) {
				dao.deleteBriefBindingVideo(Integer.valueOf(id));
			}
			map.put("code", 0);
			map.put("msg", "操作成功");
			return map;
		}

		if (briefIds == null) {
			for (String id : videoIds) {
				dao.deleteVideoBindingBrief(Integer.valueOf(id));
			}
			map.put("code", 0);
			map.put("msg", "操作成功");
			return map;
		}

		if (videoIds.length > 1) {
			dao.deleteBriefBindingVideo(Integer.valueOf(briefIds[0]));
			for (String id : videoIds) {
				Integer row = dao.bindingVideoBrief( Integer.valueOf(id),Integer.valueOf(briefIds[0]));
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				} else {
					map.put("code", 1);
					map.put("msg", "绑定失败");
				}

			}

		} 
		if (briefIds.length >1)  {
			dao.deleteVideoBindingBrief(Integer.valueOf(videoIds[0]));
			for (String id_2 : briefIds) {

				Integer row = dao.bindingVideoBrief( Integer.valueOf(videoIds[0]),Integer.valueOf(id_2));
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				} else {
					map.put("code", 1);
					map.put("msg", "绑定失败");
				}

			}
		}
		
		if (briefIds.length == 1 && videoIds.length == 1) {
			//	1 视频绑定课程
			if (oneMany == 1) {
				dao.deleteVideoBindingBrief(Integer.valueOf(videoIds[0]));
			}else {
			//	2 课程绑定视频
				dao.deleteBriefBindingVideo(Integer.valueOf(videoIds[0]));
			}
			Integer row = dao.bindingVideoBrief(Integer.valueOf(videoIds[0]),Integer.valueOf(briefIds[0]));
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "绑定成功");
			} else {
				map.put("code", 1);
				map.put("msg", "绑定失败");
			}
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryVideoBindingBrief(Integer videoId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (videoId == null) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		
		List<Brief> briefs = dao.queryVideoBindingBrief(videoId);
		map.put("code", 0);
		map.put("data", briefs);
		return map;
	}

	@Override
	public Map<String, Object> queryVideoByVideoTitle(Integer schoolId, String videoTitle) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId == null || videoTitle == null) {
			map.put("code", 1);
			map.put("msg", "参数不能为空");
			return map;
		}
		try {
			String param = "%"+videoTitle+"%";
			List<Video> videos = dao.queryVideoByVideoTitle(schoolId, param);
			for (Video video : videos) {
				
				List<Brief> briefs = dao.queryVideoBindingBrief(video.getVideoId());
				
				//	所有课程绑定的班级
				List<MyClass> briefClasses = new ArrayList<MyClass>();
				
				for (Brief brief : briefs) {
					video.setBrief(brief);
					video.setBriefContent(brief.getBriefContent());
					List<MyClass> classes = briefDao.queryMyClassByBriefId(brief.getBriefId());
					briefClasses.addAll(classes);
				}
				Set<MyClass> set = new HashSet<MyClass>(briefClasses);
				video.setMyClasses(set);
			}
			map.put("code", 0);
			map.put("data", videos);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
			e.printStackTrace();
		}
		return map;
	}
 
}
