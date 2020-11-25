package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.siwo.dao.WinXiApi;
import com.siwo.dao.WxSubscribeApi;
import com.siwo.model.OpenComponentParams;
import com.siwo.model.WxParam;
import com.siwo.service.TimeSchedulerService;

/**
 * 获取Access_token
 * @author EDZ
 *
 */
@Service
public class TimeScheduler implements TimeSchedulerService{

	@Autowired
	private WinXiApi winXiApi;
	@Autowired
	private WxSubscribeApi subApi;

//	@Scheduled(cron = "0 */1 * * * ?")
	@Override
	public void timeTask3() {
		
		// TODO Auto-generated method stub
		/* 核心定时器，每一个小时执行一次*/
	      
//		System.out.println("定时获取ToKen");
//		System.out.println("TICKET:"+OpenComponentParams.TICKET);
		
        // 先看看是不是已经获取到了ticket
        if (StringUtils.isNotBlank(OpenComponentParams.TICKET)) {
            Map<String, Object> params = new HashMap<>();
            params.put("component_appid", OpenComponentParams.appId);
            params.put("component_appsecret", OpenComponentParams.secret);
            params.put("component_verify_ticket", OpenComponentParams.TICKET);
            //微信接口客户端
            Map<String, Object> result = winXiApi.componentToken(params);
            //保存获取的token
            OpenComponentParams.ACCESS_TOKEN = MapUtils.getString(result, "component_access_token");
          
//            System.out.println("ACCESS_TOKEN-------------------:"+OpenComponentParams.ACCESS_TOKEN);
        }
	}
	
//	@PostConstruct
//	@Scheduled(cron = "0 */100 * * * ?")
//	public void test() {
//		
//		//	记报
//		Map<String, Object> jibaoresult = subApi.getAccessToken("client_credential","wxbc829d0b30f497af","1eea45bc977673ab03b9c72d78f4fd7f");
//		//	思雅
//		Map<String, Object> siyaresult = subApi.getAccessToken("client_credential","wx3b305549fae2d83e","e58c3aa6a9e93f4ac811cb04d495c9fd");
//		//	易乐
//		Map<String, Object> yileresult = subApi.getAccessToken("client_credential","wxef1777ebb8ce1af7","7233412b8cd19e84d01b4782e66f7a84");
//		
//		String access_token = (String)siyaresult.get("access_token");
//		
//		WxParam.access_token = access_token;
//	
//	}

}
