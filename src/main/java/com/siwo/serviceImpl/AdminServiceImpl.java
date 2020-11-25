package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.siwo.dao.AdminDao;
import com.siwo.model.Admin;
import com.siwo.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminDao dao;
	@Autowired
	private AdminDao adminDao;
	
	@Override
	public Map<String, Object> addAdmin(Admin admin,String companyId,String schoolId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(admin.getAdminAccount())) {
			map.put("code", 1);
			map.put("msg", "账号不能为空");
			return map;
		}
		if (StringUtils.isEmpty(admin.getAdminPassword())) {
			map.put("code", 1);
			map.put("msg", "密码不能为空");
			return map;
		}
		
		
		Admin admin2 = dao.queryAdminByAdminAccount(admin.getAdminAccount());
		
		if (admin2 != null) {
			map.put("code", 1);
			map.put("msg", "该账号已存在请输入其他账号");
		}else {
			admin.setAdminPassword(DigestUtils.md5DigestAsHex(admin.getAdminPassword().getBytes()));
			Integer row = dao.addAdmin(admin);
			if (companyId != null) {
				adminDao.addCompantAdmin(admin.getAdminId(),Integer.valueOf(companyId));
				
				adminDao.addAdminPermission(admin.getAdminId(),5);
				
			}else if (schoolId != null) {
				adminDao.addSchoolAdmin(admin.getAdminId(), Integer.valueOf(schoolId));
			}
			
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "创建成功");
			}else {
				map.put("code", 1);
				map.put("msg", "创建失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> limitAdmin(Integer page, Integer limit) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		Integer start = (page-1)*limit;
		
		List<Admin> admins = dao.limitAdmin(start, limit);
		
		int count = dao.getAdminCount();
		
		if (admins != null) {
			map.put("code", 0);
			map.put("data", admins);
			map.put("pageCount", count);
		}else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> updateAdminPassword(String adminAccount, String oldPassword, String newPassword) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(adminAccount) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
			map.put("code", 1);
			map.put("msg", "信心未填写完整，请重新填写");
			return map;
		}
		
		Admin admin = dao.queryAdminByAdminAccount(adminAccount);
		
		if (admin == null) {
			map.put("code", 1);
			map.put("msg", "用户不存在请重新再试");
			return map;
		}
		
		Integer row = dao.updateAdminPassword(adminAccount, oldPassword, newPassword);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "修改成功");
		}else {
			map.put("code", 1);
			map.put("msg", "密码不正确，请重新再试");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> superAdminDeleteAdmin(String[] adminIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (adminIds != null) {
			Integer row = dao.superAdminDeleteAdmin(adminIds);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			} else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		} else {
			map.put("code", 1);
			map.put("msg", "posterId不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryAdminByCompanyName(String companyName) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(companyName)) {
			map.put("code", 1);
			map.put("msg", "机构名不能为空");
			return map;
		}
		
		try {
			companyName = "%" + companyName + "%";
			List<Admin> admins = dao.queryAdminByCompanyName(companyName);
			map.put("code", 0);
			map.put("data", admins);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateAvailable(Admin admin) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			dao.updateAvailable(admin);
			map.put("code", 0);
			map.put("msg", "更新成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "更新失败");
			e.printStackTrace();
		}
		
		return map;
	}
}
