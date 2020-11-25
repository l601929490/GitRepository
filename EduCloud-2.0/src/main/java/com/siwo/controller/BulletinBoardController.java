package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.BulletinBoard;
import com.siwo.service.BulletinBoardService;

@RestController
public class BulletinBoardController {

	@Autowired
	private BulletinBoardService service;
	
	@PostMapping("/addBulletinBoard")
	public Map<String, Object> addBulletinBoard(@RequestBody BulletinBoard board){
		return service.addBulletinBoard(board);
	}
	
	@GetMapping("/deleteBulletinBoard")
	public Map<String, Object> deleteBulletinBoard(String bulletinBoardIds){
		String[] ids = bulletinBoardIds.split(",");
		return service.deleteBulletinBoard(ids);
	}
	
	@PostMapping("/updateBulletinBoard")
	public Map<String, Object> updateBulletinBoard(@RequestBody BulletinBoard board){
		return service.updateBulletinBoard(board);
	}
	
	@GetMapping("/limitBulletinBoard")
	public Map<String, Object> limitBulletinBoard(Integer page,Integer limit,Integer companyId){
		return service.limitBulletinBoard(page, limit,companyId);
	}
	
	@GetMapping("/applet/queryBulletinBoardByCompanyIdAndChecked")
	public Map<String, Object> queryBulletinBoardByCompanyIdAndChecked(Integer companyId){
		return service.queryBulletinBoardByCompanyIdAndChecked(companyId);
	}
}
