package com.siwo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreSum;
import com.siwo.model.ScoreSumPage;
import com.siwo.service.ScoreGoodsSumService;

@RestController
public class ScoreGoodsSumController {
	@Autowired
	private ScoreGoodsSumService service;
	
	@GetMapping("/applet/queryScoreSumByStudentId")
	public Map<String, Object> queryScoreSumByStudentId(Integer studentId){
		return service.queryScoreSumByStudentId(studentId);
	}
	@PostMapping("/queryScoreSumBySchoolId")
	public Map<String, Object> queryScoreSumBySchoolId(@RequestBody ScoreSumPage sumPage){
		Integer schoolId=sumPage.getSchoolId();
		String studentName=sumPage.getStudentName();
		Integer pageNo=sumPage.getPageNo();
		
		Integer pageNum=10;
		Page page= PageHelper.startPage(pageNo, pageNum);
		 Map<String, Object> map=new HashMap<>();
		 map= service.queryScoreSumBySchoolId(schoolId,studentName);
		 List<ScoreSum> goodsList=(List<ScoreSum>)map.get("data");
		 
		 
		 if (goodsList!=null) {
			 int total = (int) new PageInfo<>(goodsList).getTotal();//总数
	            page.setTotal(total);
	            int pageSize=page.getPageSize();//每页数量
	            //页数
	            int pages=0;
	            if (total%pageSize>0){
	                pages=total/pageSize+1;
	            }else{
	                pages=total/pageSize;
	            }
	            map.put("pages",pages);
	            map.put("total",total);
	            map.put("pageNum", pageNum);
		 }
			return map;
	};
	
	 
}
