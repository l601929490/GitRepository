package com.siwo.commons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ScoreShoppingCarDao;
import com.siwo.model.ScoreShoppingCar;

@Service
public class GetScoreShoppingCarMoney {
	@Autowired
	public  ScoreShoppingCarDao dao;
	public  int getPrice(Integer studentId) {
		Integer sum=0;
		List<ScoreShoppingCar> scoreGoods=dao.queryGoodsByStudentIdAndIsSelected0(studentId);
		for (ScoreShoppingCar scoreShoppingCar2 : scoreGoods) {
			Integer sumNum=0;		
			sumNum+=scoreShoppingCar2.getShoppingNum()*scoreShoppingCar2.getGoods().getGoodsScore();
			sum+=sumNum;
		}
		return sum;
	};
}
