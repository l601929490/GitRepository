package com.siwo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.model.Company;
import com.siwo.service.CompanyService;

@RestController
public class CompanyController {

	@Autowired
	private CompanyService service;

	@PostMapping("/addCompany")
	public Map<String, Object> addCompany(@Valid @RequestBody Company company, BindingResult result) {
		company.setCompanyCreationTime(new Date());
		return service.addCompany(company, result);
	}

	@GetMapping("/limitCompany")
	public Map<String, Object> limitCompany(Integer page, Integer limit,String companyName) {
		if (!StringUtils.isEmpty(companyName)) {
			return service.queryCompanyLikeCompanyName(companyName);
		}
		return service.limitCompany(page, limit);
	}

	@GetMapping("/applet/queryWxShowSchoolId")
	public Map<String, Object> queryWxShowSchoolId(String appId) {
		return service.queryWxShowSchoolId(appId);
	}

	@PostMapping("/updateCompany")
	public Map<String, Object> updateCompany(@RequestBody Company company) {
		company.setCompanyEditSession(new Date());
		return service.updateCompany(company);
	}

	@GetMapping("/deleteCompany")
	public Map<String, Object> deleteCompany(String companyIds) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (companyIds == null) {
			map.put("code", 1);
			map.put("msg", "id不能为空");
			return map;
		}
		String[] ids = companyIds.split(",");

		if (ids.length == 1) {
			int compantId = Integer.parseInt(companyIds);
			map = service.deleteCompanyById(compantId);
		} else if (ids.length > 1) {
			map = service.deleteManyCompany(ids);
		}
		return map;
	}

	@GetMapping("/getAllCompany")
	public Map<String, Object> getAllCompany() {
		return service.getAllCompany();
	}
	
}
