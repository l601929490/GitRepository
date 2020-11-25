package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Company;

public interface CompanyService {

	public Map<String, Object> addCompany(Company company,BindingResult result);
	
	public Map<String, Object> getAllCompany();
	
	public Map<String, Object> limitCompany(Integer page,Integer limit);
	
	public Map<String, Object> queryWxShowSchoolId(String appId);
	
	public Map<String, Object> updateCompany(Company company);
	
	public Map<String, Object> deleteCompanyById(Integer compantId);
	
	public Map<String, Object> deleteManyCompany(String[] compantIds);
	
	public Map<String, Object> queryCompanyLikeCompanyName(String companyName);
}
