package com.siwo.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Admin;
import com.siwo.service.AdminService;

@RestController
public class AdminController {

	@Autowired
	private AdminService service;
	
	@PostMapping("/addAdmin")
	public Map<String, Object> addAdmin(@RequestBody Map<String, Object>map){
		
		String adminName = (String)map.get("adminName");
		
		String adminAccount = (String)map.get("adminAccount");
		
		String password = (String)map.get("adminPassword");
		
		String available = (String)map.get("available");
		
		String creator = (String)map.get("creator");
		
		Admin admin = new Admin(null, null, adminAccount, password, available, 0, null,null,adminName,null,creator,new Date(),null,null);
		
		String companyId = (Integer)map.get("companyId")+"";
		String schoolId = (String)map.get("schoolId");
		
		if (!StringUtils.isEmpty(companyId)) {
			admin.setAdminRole("Company");
		}else if (!StringUtils.isEmpty(schoolId)) {
			admin.setAdminRole("School");
		}
		
		return service.addAdmin(admin,companyId,schoolId);
	}

	@GetMapping("/limitAdmin")
	public Map<String, Object> limitAdmin(Integer page,Integer limit,String companyName){
		if (!StringUtils.isEmpty(companyName)) {
			return service.queryAdminByCompanyName(companyName);
		}
		return service.limitAdmin(page, limit);
	}
	
	
	@PostMapping("/updateAdminPassword")
	public Map<String, Object> updateAdminPassword(@RequestBody Map<String, Object>map) {
		
		String adminAccount = (String)map.get("adminAccount");	
		String oldPassword = (String)map.get("oldPassword");
		String newPassword = (String)map.get("newPassword");
		
		return service.updateAdminPassword(adminAccount, oldPassword, newPassword);
	}
	
	@GetMapping("/superAdminDeleteAdmin")
	public Map<String, Object> superAdminDeleteAdmin(String adminIds) {
		String[] ids = adminIds.split(",");
		return service.superAdminDeleteAdmin(ids);
	}
	
	@PostMapping("/updateAvailable")
	public Map<String, Object> updateAvailable(@RequestBody Admin admin) {
		return service.updateAvailable(admin);
	}
}
