package com.siwo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siwo.commons.FileUntis;
import com.siwo.commons.HslTest;
import com.siwo.commons.OperaColor;
import com.siwo.model.RGB;
import com.siwo.service.CommonsService;

@RestController
public class CommonsController {

	@Autowired
	private CommonsService comService;
	/**
	 * 文件上传
	 * @param file
	 * @param req
	 * @return
	 */
	@PostMapping("/fileUpload")
	public Map<String, Object> fileUpload(MultipartFile file,Integer companyId,HttpServletRequest req){
		Map<String, Object>map = new HashMap<String, Object>();
		
		if (companyId == null) {
			map.put("code", 1);
			map.put("msg", "companyId不能为空");
			return map;
		}
		
		try {
			Map<String, String> result = FileUntis.upload(file,companyId,req);
			map.put("data", result.get("imgAddress"));
			map.put("videoTime", result.get("videoTime"));
			map.put("filePath", result.get("filePath"));
		} 
		catch (Exception e) {
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("msg", "您上传的文件格式暂不支持");
			return map;
		}
		return map;
	}
	
	@PostMapping("/applet/fileUpload")
	public Map<String, Object> wxfileUpload(MultipartFile file,Integer companyId,HttpServletRequest req){
		Map<String, Object>map = new HashMap<String, Object>();
		if (companyId == null) {
			map.put("code", 1);
			map.put("msg", "companyId不能为空");
			return map;
		}
		try {
			Map<String, String> result = FileUntis.upload(file,companyId, req);
			map.put("data", result.get("imgAddress"));
			map.put("videoTime", result.get("videoTime"));
			map.put("filePath", result.get("filePath"));
		} 
		catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("msg", "您上传的文件格式暂不支持");
			return map;
		}
		return map;
	}
	
	@PostMapping("/fileUploadImg")
	public Map<String, Object> fileUploadImg(MultipartFile file,Integer companyId,HttpServletRequest req){
		Map<String, Object>map = new HashMap<String, Object>();
		
		try {
			Map<String, String> result = FileUntis.upload(file,companyId,req);
			String fileName = result.get("imgAddress");
			RGB rgb = HslTest.getMainRgb(fileName);
			
			String color = OperaColor.toHex(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
			
			map.put("color", color);
			map.put("data", fileName);
		} 
		catch (Exception e) {
			map = new HashMap<String, Object>();
			map.put("msg", "您上传的文件格式暂不支持");
			return map;
		}
		return map;
	}
	
	@PostMapping("/addColorImg")
	public Map<String, Object> addColorImg(@RequestBody Map<String, Object> map){
		
		String img = (String)map.get("home_img");
		String schoolId = (String)map.get("school_id");
		String colour = (String)map.get("img_colour");
		Integer WhetherNeed = (Integer)map.get("whether_need");
		String creator = (String)map.get("creator");
		
		return comService.addColorImg(img, Integer.valueOf(schoolId), colour,WhetherNeed,creator,new Date());
	}
	
	@PostMapping("/updateColorImg")
	public Map<String, Object> updateColorImg(@RequestBody Map<String, Object> map){
		
		String img = (String)map.get("home_img");
		String schoolId = (String)map.get("school_id");
		String colour = (String)map.get("img_colour");
		Integer WhetherNeed = (Integer)map.get("whether_need");
		Integer imgColorId = (Integer)map.get("img_color_id");
		String editor = (String)map.get("editor");
		
		if (StringUtils.isEmpty(colour)) {
			colour = null;
		}
		
		return comService.updateColorImg(img, Integer.valueOf(schoolId), colour, imgColorId, WhetherNeed,editor,new Date());
	}
	
	@GetMapping("/limitColorImg")
	public Map<String, Object> limitColorImg(Integer page,Integer limit,Integer schoolId){
		return comService.limitColorImg(page, limit, schoolId);
	}
	
	@GetMapping("/deleteColorImg")
	public Map<String, Object> deleteColorImg(String imgColorIds){
		Map<String, Object> map = null;
		
		String[] ids = imgColorIds.split(",");
		
		if (ids.length == 1) {
			int imgColorId = Integer.parseInt(imgColorIds);
			map = comService.deleteColorImgById(imgColorId);
		} else if (ids.length > 1) {
			map = comService.deleteManyColorImg(ids);
		}
		return map;
	}
	
	@GetMapping("/applet/queryColorImgBySchoolId")
	public Map<String, Object> queryColorImgBySchoolId(Integer schoolId){
		return comService.queryColorImgBySchoolId(schoolId);
	}
	
	@GetMapping("/applet/learningCondition")
	public Map<String, Object> learningCondition(String startTime,String endTime,Integer classId,String studentName){
		
		Map<String, Object> map = new HashMap<String, Object>();

		if (startTime == null || endTime == null) {
			map.put("code", 1);
			map.put("msg", "时间不能为空");
			return map;
		}
		if (classId == null) {
			map.put("code", 1);
			map.put("msg", "班级Id不能为空");
			return map;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			Date date = format.parse(startTime);
//			System.out.println(date.getTime());
//			Date date2 = format.parse(endTime);
//			System.out.println(date2.getTime());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (StringUtils.isEmpty(studentName)) {
			return comService.learningCondition(Long.valueOf(startTime), Long.valueOf(endTime), classId);
		}else {
			return comService.queryStudentMessage(Long.valueOf(startTime), Long.valueOf(endTime), classId, studentName);
		}
	}
}
