package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.alibaba.fastjson.JSONObject;
import com.siwo.commons.SchoolUntil;
import com.siwo.commons.WechatUtil;
import com.siwo.dao.CompanyDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TeacherDao;
import com.siwo.dao.ClientDao;
import com.siwo.model.Company;
import com.siwo.model.Client;
import com.siwo.service.WxLoginService;

@Service
public class WxLoginServiceImpl implements WxLoginService {

	@Autowired
	private ClientDao dao;
	@Autowired
	private StudentDao stuDao;
	@Autowired
	private TeacherDao teaDao;
	@Autowired
	private GuardianDao guDao;
	@Autowired
	private MyClassDao classDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private SchoolUntil until;

	/**
	 * 微信登录
	 */
	@Override
	public synchronized Map<String, Object> addUserInfo(Client request, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}

		Company company = companyDao.queryCompanyByAppId(request.getAppId());

		// 获取sessionKey和openId
		JSONObject sessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(request.getCode(), company.getCompanyAppId(),
				company.getCompanyAppSecret());
		String openid = sessionKeyOpenId.getString("openid");
		String sessionKey = sessionKeyOpenId.getString("session_key");
		request.setSessionKey(sessionKey);
		request.setOpenId(openid);
		request.setLoginTime(new Date());
		
		Client user = dao.queryUserByOpenId(openid);
		
		// 用户为null重新添加
		if (user == null) {
			Integer row = 0;
			if (!StringUtils.isEmpty(openid)) {
				row = dao.addUser(request);
			}
			if (row > 0) {
				map.put("code", 0);
				map.put("data", openid);
				map.put("msg", "登录成功");
			} else {
				map.put("code", 1);
				map.put("msg", "登录失败");
			}
		} else {
			dao.updateSessionKey(sessionKey, openid);
			dao.updateRawData(request.getRawData(), openid);
			dao.updateLoginTime(new Date(), openid);
			map.put("code", 0);
			map.put("data", openid);
			map.put("msg", "登录成功");
		}
		return map;
	}

	/**
	 * 获取微信手机号
	 */
	@Override
	public synchronized Map<String, Object> getPhoneNumber(Client user) {

		Map<String, Object> map = new HashMap<String, Object>();

		String openid = user.getOpenId();

		if (openid == null) {
			return null;
		}

		Company company = companyDao.queryCompanyByAppId(user.getAppId());

		if (company == null) {
			map.put("code", 1);
			map.put("msg", "appId不正确");
			return map;
		}

		// 检查是否登录过
		Client request = dao.queryUserByOpenId(openid);

		if (request == null) {
			System.out.println("请先登录再获取手机号");
		}

		String phoneNumber = request.getUserPhone();

		// 没登录过重新添加
		if (phoneNumber == null) {
			JSONObject userInfo = null;
			userInfo = WechatUtil.getPhoneNumber(user.getEncryptedData(), request.getSessionKey(), user.getIv());

			phoneNumber = userInfo.getString("phoneNumber");

			dao.updateUserPhone(phoneNumber, openid, company.getCompanyId());

		}
		
		//	获取所有身份
		List<Map<String, Object>>list = until.selectId(user.getSchoolId(), phoneNumber,openid);
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> selectIdentity(String appId, String phone, Integer schoolId,String openId) {

		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> list = until.selectId(schoolId, phone,openId);

		map.put("code", 0);
		map.put("data", list);
		return map;
	}

	@Override
	public Map<String, Object> everyLogon(String phone, Integer schoolId, String openId) {
		try {
			until.selectId(schoolId, phone, openId);
			dao.updateLastLoginTime(openId, new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
