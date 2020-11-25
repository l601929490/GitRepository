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
import com.siwo.model.ScoreGoodsPage;
import com.siwo.service.ScoreGoodsService;

@RestController
public class ScoreGoodsController {

	@Autowired
	private ScoreGoodsService service;

	// 根据学生查商品
	@GetMapping("/applet/queryGoodsByStudentId")
	public Map<String, Object> queryGoodsByStudentId(Integer studentId) {
		return service.queryGoodsByStudentId(studentId);
	}

	// 猜你喜欢商品 根据学生
	@GetMapping("/applet/queryGoodsLikeByStudentId")
	public Map<String, Object> queryGoodsLikeByStudentId(Integer studentId) {
		return service.queryGoodsLikeByStudentId(studentId);
	}

	// 根据商品id查询详细信息
	@GetMapping("/applet/queryGoodsDetailByGoodsId")
	public Map<String, Object> queryGoodsDetailByGoodsId(Integer studentId, Integer goodsId) {
		return service.queryGoodsDetailByGoodsId(studentId, goodsId);
	}

	// 根据商品类型id查询商品
	@GetMapping("/applet/queryGoodsByTypeId")
	public Map<String, Object> queryGoodsByTypeId(Integer typeId, Integer studentId) {
		return service.queryGoodsByTypeId(typeId, studentId);
	}

	// 查询机构下校区的所有商品 加分页 ，加条件查询
	@PostMapping("queryGoodsBySchoolId")
	public Map<String, Object> queryGoodsBySchoolId(@RequestBody ScoreGoodsPage scorePage) {

		Integer schoolId = scorePage.getSchoolId();// 校区
		Integer typeId = scorePage.getTypeId();// 类型
		Integer pageNo = scorePage.getPageNo();// 页数
		String name = scorePage.getName();// 名称
		Integer pageNum = 10;// 每页显示几个数

		@SuppressWarnings("rawtypes")
		Page page = PageHelper.startPage(pageNo, pageNum);
		Map<String, Object> map = new HashMap<>();
		map = service.queryGoodsBySchoolId(schoolId, typeId, name);
		@SuppressWarnings("unchecked")
		List<ScoreGoods> goodsList = (List<ScoreGoods>) map.get("data");

		if (goodsList != null) {
			int total = (int) new PageInfo<>(goodsList).getTotal();// 总数
			page.setTotal(total);
			int pageSize = page.getPageSize();// 每页数量
			// 页数
			int pages = 0;
			if (total % pageSize > 0) {
				pages = total / pageSize + 1;
			} else {
				pages = total / pageSize;
			}
			map.put("pages", pages);
			map.put("total", total);
			map.put("pageNum", pageNum);
		}
//			return null;
		return map;

	}

	// 上架商品
	@PostMapping("addScoreGoods")
	public Map<String, Object> addScoreGoods(@RequestBody ScoreGoods sGoods) {

		if (sGoods.getGoodsId() == null) {
			return service.addScoreGoods(sGoods);
		} else {
			return service.updateScoreGoods(sGoods);
		}

	}

	// 搜索商品
	@PostMapping("/applet/searchGoodsByName")
	public Map<String, Object> searchGoodsByName(Integer type, String name, Integer studentId) {
		return service.searchGoodsByName(type, name, studentId);
	}

	// 下架商品
	@GetMapping("/offShelfGoodsByGoodsId")
	public Map<String, Object> offShelfGoodsByGoodsId(Integer goodsId) {
		return service.offShelfGoodsByGoodsId(goodsId);
	}

	// 删除商品
	@GetMapping("/deleteGoodsByGoodsId")
	public Map<String, Object> deleteGoodsByGoodsId(String goodsId) {
		String[] strs = goodsId.split(",");
		return service.deleteGoodsByGoodsId(strs);
	}

}
