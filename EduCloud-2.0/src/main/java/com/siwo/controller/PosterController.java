package com.siwo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Poster;
import com.siwo.service.PosterService;

@RestController
public class PosterController {

	@Autowired
	private PosterService service;
	@PostMapping("/addPoster")
	public Map<String, Object> addPoster(@RequestBody @Valid Poster poster,BindingResult result){
		poster.setPosterCreateTime(new Date());
		return service.addPoster(poster, result);
	}
	
	@GetMapping("/deletePoster")
	public Map<String, Object> deletePoster(String posterIds){
		if (StringUtils.isEmpty(posterIds)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", 1);
			map.put("msg", "id不能为空");
			return map;
		}
		String[] ids = posterIds.split(",");
		return service.deleteManyPoster(ids);
	}
	
	@PostMapping("/updatePoster")
	public Map<String, Object> updatePoster(@RequestBody Poster poster){
		return service.updatePoster(poster);
	}
	
	@GetMapping("/limitPosters")
	public Map<String, Object> limitPosters(Integer page,Integer limit,Integer schoolId,Integer code){
		return service.limitPosters(page, limit, schoolId,code);
	}
	
	/**
	 *	分享海报
	 * @param schoolId
	 * @param openId
	 * @return
	 */
	@GetMapping("/applet/queryPosterByPosterId")
	public Map<String, Object> queryPosterByPosterId(Integer schoolId,String openId,Integer code){
		return service.queryPosterByPosterId(schoolId, openId,code);
	}
	
}
