package com.siwo.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Suggest;
import com.siwo.service.SuggestService;

@RestController
public class SuggestController {

	@Autowired
	private SuggestService service;
	
	@PostMapping("/applet/addSuggest")
	public Map<String, Object> addSuggest(@Valid Suggest suggest,BindingResult result){
		return service.addSuggest(suggest, result);
	}
	
	@GetMapping("/deleteSuggest")
	public Map<String, Object> deleteSuggest(String suggestIds){
		Map<String, Object> map = null;
		
		String[] ids = suggestIds.split(",");
		
		if (ids.length == 1) {
			int suggestId = Integer.parseInt(suggestIds);
			map = service.deleteSuggest(suggestId);
		} else if (ids.length > 1) {
			map = service.deleteManySuggest(ids);
		}
		return map;
	}
	
	@PostMapping("/updateSuggest")
	public Map<String, Object> updateSuggest(@RequestBody Suggest suggest){
		return service.updateSuggest(suggest);
	}
	
	@GetMapping("/limitSuggest")
	public Map<String, Object> limitSuggest(Integer page,Integer limit,Integer schoolId){
		return service.limitSuggest(page, limit, schoolId);
	}
	
	@GetMapping("/updateSuggestStatus")
	public Map<String, Object> updateSuggestStatus(Integer status, String suggestIds) {
		if (StringUtils.isEmpty(suggestIds)) {
			return null;
		}
		String[] id = suggestIds.split(",");
		return service.updateSuggestStatus(status, id);
	}
}
