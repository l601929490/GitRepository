package com.siwo.service;

import java.util.Map;

import com.siwo.model.BulletinBoard;

public interface BulletinBoardService {

	public Map<String, Object> addBulletinBoard(BulletinBoard board);
	
	public Map<String, Object> deleteBulletinBoard(String[] bulletinBoardId);
	
	public Map<String, Object> updateBulletinBoard(BulletinBoard board);
	
	public Map<String, Object> limitBulletinBoard(Integer page,Integer limit,Integer companyId);
	
	public Map<String, Object> queryBulletinBoardByCompanyIdAndChecked(Integer companyId);
}
