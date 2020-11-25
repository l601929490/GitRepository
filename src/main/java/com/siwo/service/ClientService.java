package com.siwo.service;

import java.util.Map;


public interface ClientService {

	public Map<String, Object> limitClientsAccomplish(Integer companyId,Integer page,Integer limit,Integer schoolId);
	
	
	public Map<String, Object> limitClientsAccomplishByClassId(Integer companyId,Integer schoolId,Integer classId);
	
	public Map<String, Object> limitClientsFail(Integer companyId,Integer page,Integer limit,Integer schoolId);
	
	//	全机构用户
	public Map<String, Object> limitClients(Integer companyId,Integer page,Integer limit,Integer schoolId);
}
