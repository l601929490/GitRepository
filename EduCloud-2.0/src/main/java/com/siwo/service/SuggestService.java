package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Suggest;

public interface SuggestService {

	public Map<String, Object> addSuggest(Suggest suggest,BindingResult result);
	
	public Map<String, Object> deleteSuggest(Integer suggestId);
	
	public Map<String, Object> deleteManySuggest(String[] suggestIds);
	
	public Map<String, Object> updateSuggest(Suggest suggest);
	
	public Map<String, Object> limitSuggest(Integer page,Integer limit,Integer schoolId);
	
	public Map<String, Object> updateSuggestStatus(Integer status,String[] suggestId);
}
