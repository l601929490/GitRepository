package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.BriefDao;
import com.siwo.model.Brief;
import com.siwo.model.MyClass;
import com.siwo.service.BriefService;

@Service
public class BriefServoiceImpl implements BriefService{

	@Autowired
	private BriefDao dao;
	
	@Override
	public Map<String, Object> addBrief(Brief brief,BindingResult result) {
		Map<String, Object>map = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		brief.setCreationTime(new Date());
		Integer row = dao.addBrief(brief);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryBriefByClassId(Integer classId) {
		Map<String, Object>map = new HashMap<String, Object>();
		List<Brief> briefs = dao.queryBriefByClassId(classId);
		map.put("code", 0);
		map.put("msg", "查询成功");
		map.put("data", briefs);
		return map;
	}

	@Override
	public Map<String, Object> queryAllBrief(Integer schoolId) {
		Map<String, Object>map = new HashMap<String, Object>();
		List<Brief> briefs = dao.queryAllBrief(schoolId);
		map.put("code", 0);
		map.put("data", briefs);
		return map;
	}

	@Override
	public Map<String, Object> limiBriefs(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer start = (page-1)*limit;
		List<Brief> briefs = dao.limiBriefs(start, limit, schoolId);
		
		for (Brief brief : briefs) {
			List<MyClass> myClasses = dao.queryMyClassByBriefId(brief.getBriefId());
			brief.setMyClasses(myClasses);
		}
		
		Integer count = dao.getBriefCount(schoolId);
		if (briefs != null) {
			map.put("code", 0);
			map.put("data", briefs);
			map.put("pageCount", count);
		}else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryBriefsBySchoolIdAndIsChoiceness(Integer schoolId, Integer isChoiceness,Integer classId,String briefContent) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId == null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
			return map;
		}
		
		if (isChoiceness == null) {
			map.put("code", 1);
			map.put("msg", "是否为精选课不能为空");
			return map;
		}
		List<Brief> briefs = null;
		
		if (isChoiceness == 0) {
			//	班级教学课
			briefs = dao.queryBriefsBySchoolIdAndIsChoiceness(schoolId, "教学课",classId);
			List<Brief> briefs2 = dao.queryBriefsBySchoolIdAndIsChoiceness02(schoolId,"教学课");
			briefs.addAll(briefs2);
		}else if(isChoiceness == 1){
			//	游客 公开课精选课
			briefs = dao.queryBriefsBySchoolIdAndIsChoiceness02(schoolId, "教学课");
		}else if(isChoiceness == 2){
			//	班级 教学课
			String param = "%"+briefContent+"%";
			briefs = dao.getBriefByClassId(classId,"教学课",param);
		}else if (isChoiceness == 3) {
			//	游客查询 公开课，精选课
			String param = "%"+briefContent+"%";
			briefs = dao.queryBriefsBySchoolIdAndLikeSelect(schoolId, param,"教学课");
		}
		if (briefs != null) {
			map.put("code", 0);
			map.put("data", briefs);
		}else {
			map.put("code", 1);
			map.put("msg", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateBrief(Brief brief) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer row = dao.updateBrief(brief);
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
	public Map<String, Object> addBriefClass(String[] classIds, Integer briefId) {
		Map<String, Object> map = new HashMap<String, Object>();

//		if (classIds == null || classIds.length == 0) {
//			dao.deleteBriefClass(briefId);
//			map.put("code", 0);
//			map.put("msg", "操作成功");
//			return map;
//		}

		dao.deleteBriefClass(briefId);
		
		if (classIds != null && classIds.length != 0) {
			for (String id : classIds) {
				
				Integer row = dao.addBriefClass(Integer.valueOf(id), briefId);
				if (row > 0) {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				}else {
					map.put("code", 1);
					map.put("msg", "绑定失败");
				}
			}
		}else {
			map.put("code", 0);
			map.put("msg", "操作成功");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryMyClassByBriefId(Integer briefId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (briefId == null) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		
		List<MyClass> myClasses = dao.queryMyClassByBriefId(briefId);
		map.put("code", 0);
		map.put("data", myClasses);
		return map;
	}

	@Override
	public Map<String, Object> deleteBrief(String[] briefIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (briefIds != null && briefIds.length != 0) {
			
			for (String id : briefIds) {
				
				Brief brief = dao.queryBriefByBriefId(Integer.valueOf(id));
				
				List<MyClass> myClasses = dao.queryMyClassByBriefId(Integer.valueOf(id));
				if (myClasses != null && myClasses.size() != 0) {
					map.put("code", 1);
					map.put("msg", "请先解除"+brief.getBriefContent()+"绑定的班级");
					return map;
				}
				
				List<Integer> videoIds = dao.queryBriefBindingVideo(Integer.valueOf(id));
				if (videoIds != null && videoIds.size()!=0) {
					map.put("code", 1);
					map.put("msg", "请先解除"+brief.getBriefContent()+"绑定的视频");
					return map;
				}
				
			}
			
			Integer row = dao.deleteBrief(briefIds);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			} else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> queryBriefByBriefId(Integer briefId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Brief brief = dao.queryBriefByBriefId(briefId);
		map.put("code", 0);
		map.put("msg", "查询成功");
		map.put("brief", brief);
		return map;
	}

	@Override
	public Map<String, Object> queryBriefsByBriefContent(String briefContent, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId == null || briefContent == null) {
			map.put("code", 1);
			map.put("msg", "参数不能为空");
			return map;
		}
		try {
			String param = "%"+briefContent+"%";
			List<Brief> briefs = dao.queryBriefsByBriefContent(param, schoolId);
			map.put("code", 0);
			map.put("data", briefs);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
			e.printStackTrace();
		}
		return map;
	}

}
