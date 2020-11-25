package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.dao.CompanyDao;
import com.siwo.dao.SchoolDao;
import com.siwo.model.Company;
import com.siwo.model.School;
import com.siwo.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	private CompanyDao dao;
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Override
	public Map<String, Object> addCompany(Company company,BindingResult result) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}
		Integer row = dao.addCompany(company);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		}else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> getAllCompany() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Company> list = dao.getAllCompany();
		
		if (list != null) {
			map.put("code", 0);
			map.put("data", list);
		}else {
			map.put("code", 1);
			map.put("msg", "暂无数据");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> limitCompany(Integer page,Integer limit) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		Integer start = (page-1)*limit;
		
		List<Company> companies = dao.limitCompany(start,limit);
		
		int count = dao.getCompanyCount();
		
		if (companies != null) {
			map.put("code", 0);
			map.put("data", companies);
			map.put("pageCount", count);
		}else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryWxShowSchoolId(String appId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(appId)) {
			Company company = dao.queryCompanyByAppId(appId);
			if (company == null) {
				map.put("code", 1);
				map.put("msg", "appId不正确");
				return null;
			}
			List<School> schools = schoolDao.querySchoolByCompanyId(company.getCompanyId());
			School school = null;
			
			if (schools.size()>0) {
				school = schools.get(0);
			}
//			= schoolDao.querySchoolBySchoolId(company.getWxShowSchoolId());
			if (school == null) {
				map.put("code", 1);
				map.put("msg", "还未选择机构");
			}else {
				map.put("code", 0);
				map.put("school", school);
				map.put("companyId", company.getCompanyId());
				map.put("company", company);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> updateCompany(Company company) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (company.getCompanyId() == null) {
			map.put("code", 1);
			map.put("msg", "compantId不能为空");
			return map;
		}
		
		Integer row = dao.updateCompany(company);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "更新成功");
		}else {
			map.put("code", 1);
			map.put("msg", "更新失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteCompanyById(Integer compantId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (compantId == null) {
			map.put("code", 1);
			map.put("msg", "posterId不能为空");
		}else {
			Integer row = dao.deleteCompanyById(compantId);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteManyCompany(String[] compantIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(compantIds != null) {
			Integer row = dao.deleteManyCompany(compantIds);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}else {
			map.put("code", 1);
			map.put("msg", "posterId不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryCompanyLikeCompanyName(String companyName) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(companyName)) {
			map.put("code", 1);
			map.put("msg", "机构名不能为空");
			return map;
		}
		try {
			companyName = "%" + companyName + "%";
			List<Company> companies =  dao.queryCompanyLikeCompanyName(companyName);
			
			map.put("code", 0);
			map.put("data", companies);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
		}
		return map;
	}

}
