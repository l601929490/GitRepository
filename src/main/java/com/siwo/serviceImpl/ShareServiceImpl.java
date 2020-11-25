package com.siwo.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.CompanyDao;
import com.siwo.dao.WxSubscribeApi;
import com.siwo.model.Company;
import com.siwo.model.WxParam;
import com.siwo.service.ShareService;

@Service
public class ShareServiceImpl implements ShareService{

	@Autowired
	private WxSubscribeApi subApi;
	@Autowired
	private CompanyDao comDao;
	
	@Override
	public synchronized Map<String, Object> ShareQrCode(String scene, String page, String width,String appId,String secret,HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> siyaresult = subApi.getAccessToken("client_credential", appId, secret);
		
		String access_token = (String)siyaresult.get("access_token");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scene", scene);
		params.put("page", page);
		params.put("width", width);
		byte[] result = null;
		try {
			result = subApi.getQrcode(access_token, params);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "参数不正确");
			return map;
		}
		Company company = comDao.queryCompanyByAppId(appId);
		String filePath = "C:/myFile/siwo/"+company.getCompanyId();
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String Path = file.getAbsolutePath()+"/"+System.currentTimeMillis()+".png";
		
		File imageFile = new File(Path); 
		//创建输出流 
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(imageFile);
			//写入数据 
			outStream.write(result);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("mdg", "服务器异常");
			return map;
		} finally {
			try {
				//关闭输出流 
				outStream.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			} 
		
		}
		String imgAddress = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()+ req.getContextPath() + "/" +company.getCompanyId()+ "/" + imageFile.getName();
		map.put("code", 0);
		map.put("data", imgAddress);
		return map;
	}

}
