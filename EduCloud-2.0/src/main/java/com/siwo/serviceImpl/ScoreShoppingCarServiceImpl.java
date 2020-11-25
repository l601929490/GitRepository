package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.siwo.commons.GetScoreShoppingCarMoney;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.ScoreGoodsDao;
import com.siwo.dao.ScoreShoppingCarDao;
import com.siwo.dao.ScoreSumDao;
import com.siwo.dao.StudentDao;
import com.siwo.model.School;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreShoppingCar;
import com.siwo.model.ScoreSum;
import com.siwo.model.Student;
import com.siwo.service.ScoreShoppingCarService;

@Service
public class ScoreShoppingCarServiceImpl implements ScoreShoppingCarService {
	@Autowired
	private ScoreShoppingCarDao dao;

	@Autowired
	private ScoreSumDao sumDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private GetScoreShoppingCarMoney getScoreShoppingCarMoney;
	@Autowired
	private ScoreGoodsDao goodsDao;

	@Override
	public Map<String, Object> addShoppingCar(Integer studentId, Integer goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//判断购物车有没有此商品
		ScoreShoppingCar scoreShoppingCar= dao.queryShoppingCarAndGoodsId(studentId, goodsId);
		if (scoreShoppingCar==null) {
			int res = dao.addShoppingCar(studentId, goodsId);
			if (res > 0) {
				map.put("code", 0);
				map.put("msg", "增加成功");
			} else {
				map.put("code", 1);
				map.put("msg", "增加失败");
			}
		}else {
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryShoppingCar(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId == null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
		} else {
			List<ScoreShoppingCar> list = dao.queryShoppingCar(studentId);
			if (list != null) {

				int sum = getScoreShoppingCarMoney.getPrice(studentId);
				for (ScoreShoppingCar scoreShoppingCar : list) {
					Integer schoolId = scoreShoppingCar.getGoods().getGoodsSchoolId();
					School school = schoolDao.querySchoolById(schoolId);
					ScoreGoods goods = scoreShoppingCar.getGoods();
					goods.setGoodsSchool(school);
					scoreShoppingCar.setGoods(goods);
					Integer gwc=scoreShoppingCar.getShoppingNum();
					Integer kc=scoreShoppingCar.getGoods().getGoodsNum().getGoodsNumRemain();
					if (gwc>kc) {
						//如果购物车商品大于库存 ，数量改为库存数量
						if (kc==0) {
							kc=1;
						}
						dao.updateShoppingNumByShoppingCarId(scoreShoppingCar.getShoppingCarId(),kc);
						map.put("aaaa", "请上滑刷新购物车");
					}
					
				}
				map.put("code", 0);
				map.put("data", list);
				map.put("sum", sum);
			} else {
				map.put("code", 1);
				map.put("msg", "暂无数据");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> setShoppingCarNum(Integer studentId, Integer goodsId, String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId == null || goodsId == null || flag == null) {
			if (studentId == null) {
				map.put("code", 1);
				map.put("msg", "studentId不能为空");
			}
			if (goodsId == null) {
				map.put("code", 1);
				map.put("msg", "goodsId不能为空");
			}
			if (flag == null) {
				map.put("code", 1);
				map.put("msg", "flag不能为空");
			}
		} else {
			// 根据studentId和goodsId查询购物车数据

			ScoreShoppingCar scoreShoppingCar = dao.queryShoppingCarAndGoodsId(studentId, goodsId);
			 ScoreGoods scoreGoods=  goodsDao.queryGoodsDetailByGoodsId(goodsId);
			//查询商品数量
			if (flag.equalsIgnoreCase("y")) {
				if (scoreShoppingCar != null&&scoreGoods.getGoodsNum().getGoodsNumRemain()>scoreShoppingCar.getShoppingNum()) {
					Integer goodsNum = scoreShoppingCar.getShoppingNum() + 1;
					int res = dao.changeShoppingNum(scoreShoppingCar.getShoppingCarId(), goodsNum);
					if (res > 0) {
						// 查询最新的购物车商品信息
						// 查询此人购物车选中的总价钱
						map.put("code", 0);
						map.put("msg", "增加数量成功");
						int sum = getScoreShoppingCarMoney.getPrice(studentId);
						map.put("sum", sum);
					} else {
						map.put("code", 1);
						map.put("msg", "增加数量失败");
					}
				} else {
					map.put("code", 2);
				}

			}
			if (flag.equalsIgnoreCase("n")) {
				if (scoreShoppingCar != null) {
					Integer goodsNum = scoreShoppingCar.getShoppingNum() - 1;
					if (goodsNum == 0) {
						// 删除购物车商品
						int res = dao.deleteShoppingCarByShoppingCar(scoreShoppingCar.getShoppingCarId());
						if (res > 0) {
							// 查询最新的购物车商品信息
							map.put("code", 0);
							map.put("msg", "删除成功");
							int sum = getScoreShoppingCarMoney.getPrice(studentId);
							map.put("sum", sum);
						} else {
							map.put("code", 1);
							map.put("msg", "删除失败");
						}
					} else {
						dao.changeShoppingNum(scoreShoppingCar.getShoppingCarId(), goodsNum);
						map.put("code", 0);
						map.put("msg", "删减数量成功");
						int sum = getScoreShoppingCarMoney.getPrice(studentId);
						map.put("sum", sum);
					}

				} else {
					map.put("code", 1);
				}

			}
			if (flag.equalsIgnoreCase("d")) {
				int res = dao.deleteShoppingCarByShoppingCar(scoreShoppingCar.getShoppingCarId());
				if (res > 0) {
					map.put("code", 0);
					map.put("msg", "删除成功");
					int sum = getScoreShoppingCarMoney.getPrice(studentId);
					map.put("sum", sum);
				} else {
					map.put("code", 1);
					map.put("msg", "删除失败");
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> changeIsSelected(Integer shoppingCarId, Integer studentId) {
		Map<String, Object> map = new HashMap<>();
		int result = 0;
		int isSelect = dao.selectIsSelectByShoppingCarIdAndStudentId(shoppingCarId, studentId);
		// 选中改为未选中
		if (isSelect == 0) {
			result = 1;
			int res = dao.changeIsSelected(shoppingCarId, result);
			if (res > 0) {
				map.put("code", 0);
				map.put("msg", "修改成功");
				int sum = getScoreShoppingCarMoney.getPrice(studentId);
				map.put("sum", sum);
			} else {
				map.put("code", 1);
				map.put("msg", "修改失败");
			}
		} else {
			result = 0;
			// 未选中改为选中
			int res = dao.changeIsSelected(shoppingCarId, result);
			if (res > 0) {
				map.put("code", 0);
				map.put("msg", "修改成功");
				int sum = getScoreShoppingCarMoney.getPrice(studentId);
				map.put("sum", sum);
			} else {
				map.put("code", 1);
				map.put("msg", "修改失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> changeIsSelectedAll(Integer studentId, Integer isSelected) {
		Map<String, Object> map = new HashMap<>();
		int result = 0;
		if (isSelected == 1) {
			// 改变为全部不选择
			int res = 0;
			result = dao.changeIsSelectedAll(studentId, res);
			if (result > 0) {
				map.put("code", 0);
				map.put("msg", "改为全部选中");
				int sum = getScoreShoppingCarMoney.getPrice(studentId);
				map.put("sum", sum);
			} else {
				map.put("code", 1);
				map.put("msg", "全部选中失败");
			}
		}
		if (isSelected == 0) {
			int res = 1;
			result = dao.changeIsSelectedAll(studentId, res);
			if (result > 0) {
				map.put("code", 0);
				map.put("msg", "改为全部不选中");
				int sum = getScoreShoppingCarMoney.getPrice(studentId);
				map.put("sum", sum);
			} else {
				map.put("code", 1);
				map.put("msg", "修改失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> queryGoodsByStudentIdAndIsSelected0(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId == null) {
			map.put("code", 1);
			map.put("msg", "studentId不能为空");
		} else {
			List<ScoreShoppingCar> list = dao.queryGoodsByStudentIdAndIsSelected0(studentId);
			if (list != null) {
				ScoreSum scoreSum = sumDao.queryScoreSumBystudentId(studentId);// 积分
				Student student = studentDao.queryById(studentId);
				Integer schoolId = student.getSchoolId();
				School school = schoolDao.querySchoolById(schoolId);
				student.setSchool(school);
				map.put("code", 0);
				map.put("data", list);
				int sum = getScoreShoppingCarMoney.getPrice(studentId);
				map.put("sum", sum);
				map.put("scoreSum", scoreSum);
				map.put("student", student);
			} else {
				map.put("code", 1);
			}
		}
		return map;
	}
}
