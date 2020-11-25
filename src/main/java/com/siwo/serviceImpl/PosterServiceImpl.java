package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.PosterDao;
import com.siwo.model.Poster;
import com.siwo.service.PosterService;

@Service
public class PosterServiceImpl implements PosterService{

	@Autowired
	private PosterDao dao;

	@Override
	public Map<String, Object> addPoster(Poster poster,BindingResult result) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		
		Integer row = dao.addPoster(poster);
		
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
	public Map<String, Object> deletePoster(Integer posterId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (posterId == null) {
			map.put("code", 1);
			map.put("msg", "posterId不能为空");
		}else {
			Integer row = dao.deletePoster(posterId);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteManyPoster(String[] posterIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(posterIds != null) {
			Integer row = dao.deleteManyPoster(posterIds);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}else {
			map.put("code", 1);
			map.put("msg", "posterId不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> updatePoster(Poster poster) {
		Map<String, Object> map = new HashMap<String, Object>();
		poster.setPosterEditSession(new Date());
		Integer row = dao.updatePoster(poster);
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
	public Map<String, Object> limitPosters(Integer page, Integer limit, Integer schoolId,Integer code) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<Poster> posters = dao.limitPosters(start, limit, schoolId,code);

		int count = dao.queryPosterCount(schoolId,code);

		if (posters != null) {
			map.put("code", 0);
			map.put("data", posters);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> queryPosterByPosterId(Integer schoolId,String openId,Integer code) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId == null) {
			map.put("code", 1);
			map.put("msg", "posterId不能为空");
		}else {
			List<Poster> posters = dao.queryPosterBySchoolId(schoolId,code);
			int bound = posters.size();
			int random = (int)(Math.random()*bound);
			if (posters != null && posters.size() != 0) {
				map.put("code", 0);
				map.put("data", posters.get(random));
			}
		}
		return map;
	}

}
