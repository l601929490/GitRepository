package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.siwo.dao.AdminDao;
import com.siwo.dao.ClientDao;
import com.siwo.dao.CompanyDao;
import com.siwo.dao.SchoolDao;
import com.siwo.model.Admin;
import com.siwo.model.Client;
import com.siwo.model.School;
import com.siwo.model.Company;
import com.siwo.service.UserLoginService;
import com.siwo.service.WxLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private WxLoginService wxLoginService;

	@Override
	public Map<String, Object> theBackgroundLogin(Admin admin) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Subject subject = SecurityUtils.getSubject();
		String md5Password = DigestUtils.md5DigestAsHex(admin.getAdminPassword().getBytes());
		UsernamePasswordToken token = new UsernamePasswordToken(admin.getAdminAccount(), md5Password);
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			map.put("code", 1);
			map.put("msg", "用户名或密码错误");
			return map;
		}catch (IncorrectCredentialsException e) {
			map.put("code", 1);
			map.put("msg", "用户名或密码错误");
			return map;
		}
		
		Admin admin2 = adminDao.queryAdminByAdminAccount(admin.getAdminAccount());
		
		Integer compantId = null;
		List<School> schools = new ArrayList<School>();;
		List<Company> companys = null;
		
		if ("Company".equals(admin2.getAdminRole())) {
			
			compantId = adminDao.queryCompantIdByAdminId(admin2.getAdminId());
			schools = schoolDao.querySchoolByCompanyId(compantId);
			
		}else if ("superAdmin".equals(admin2.getAdminRole())) {
			
			companys = companyDao.getAllCompany();
			schools = schoolDao.querySchoolByCompanyId(companys.get(0).getCompanyId());
			
		}else if ("teacher".equals(admin2.getAdminRole())) {
			
			School school = schoolDao.querySchoolById(admin2.getShowSchoolId());
			schools.add(school);
			
		}
		
		map.put("code", 0);
		map.put("msg", "登录成功");
		map.put("data", admin2.getAdminRole());
		map.put("school", schools);
		map.put("adminName", admin2.getAdminName());
		map.put("adminAccount", admin2.getAdminAccount());
		map.put("isSuperAdmin", admin2.getIsSuperAdmin());
		map.put("companyId", compantId);
		map.put("companys", companys);
		return map;
	}

	@Override
	public Map<String, Object> refreshUser(String appId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Company company = companyDao.queryCompanyByAppId(appId);
		
		List<Client> clients = clientDao.queryByCompanyId(company.getCompanyId());
		
		for (Client client : clients) {
			wxLoginService.everyLogon(client.getUserPhone(), 6, client.getOpenId());
		}
		map.put("msg", "成功");
		return map;
	}

}
