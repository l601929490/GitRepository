package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Poster;

public interface PosterService {

	public Map<String, Object> addPoster(Poster poster,BindingResult result);
	
	public Map<String, Object> deletePoster(Integer posterId);
	
	public Map<String, Object> deleteManyPoster(String[] posterIds);
	
	public Map<String, Object> updatePoster(Poster poster);
	
	public Map<String, Object> limitPosters(Integer page,Integer limit,Integer school_id,Integer code);
	
	public Map<String, Object> queryPosterByPosterId(Integer schoolId,String phone,Integer code);
}
