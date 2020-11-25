package com.siwo.service;

import java.util.Map;

import com.siwo.model.Admin;

public interface AdminService {

	public Map<String, Object> addAdmin(Admin admin,String companyId,String schoolId);
	
	public Map<String, Object> limitAdmin(Integer page,Integer limit);
	
	public Map<String, Object> updateAdminPassword(String adminAccount,String oldPassword,String newPassword);
	
	public Map<String, Object> superAdminDeleteAdmin(String[] adminIds);
	
	public Map<String, Object> queryAdminByCompanyName(String companyName);
	
	public Map<String, Object> updateAvailable(Admin admin);
}
