package com.siwo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.service.ScoreShoppingCarService;

@RestController
public class ScoreShoppingCarController {

	@Autowired
	private ScoreShoppingCarService service;
	/**
	 * 小程序
	 * @param schoolId
	 * @return
	 */
	//商品加入购物车
	@PostMapping("/applet/addShoppingCar")
	public Map<String, Object> addShoppingCar(Integer studentId,Integer goodsId){
		return service.addShoppingCar(studentId,goodsId);
	}
	/**
	 *查询购物车信息 
	 */
	@GetMapping("/applet/queryShoppingCar")
	public Map<String, Object> queryShoppingCar(Integer studentId){
		return service.queryShoppingCar(studentId);
	}
	/**
	 *购物车商品数量加减 
	 */
	@GetMapping("/applet/setShoppingCarNum")
	public Map<String, Object> setShoppingCarNum(Integer studentId,Integer goodsId,String flag){
		return service.setShoppingCarNum(studentId,goodsId,flag);
	} 
	/**
	 *单个商品改变选中状态
	 */
	@GetMapping("/applet/changeIsSelected")
	public Map<String, Object> changeIsSelected(Integer shoppingCarId,Integer studentId ){
		return service.changeIsSelected(shoppingCarId,studentId);
	} 
	/**
	 *全部商品改变选中状态 
	 */
	@GetMapping("/applet/changeIsSelectedAll")
	public Map<String, Object> changeIsSelectedAll(Integer studentId,Integer isSelected){
		return service.changeIsSelectedAll(studentId,isSelected);
	}
	/**
	 * 查看用户选中的商品信息
	 */
	@GetMapping("/applet/queryGoodsByStudentIdAndIsSelected0")
	public Map<String, Object> queryGoodsByStudentIdAndIsSelected0(Integer studentId){
		return service.queryGoodsByStudentIdAndIsSelected0(studentId);
	}
	/**
	 * 后台
	 */
	 
}
