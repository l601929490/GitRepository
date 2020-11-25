package com.siwo.serviceImpl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.siwo.commons.Tools;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.ScoreGoodsDao;
import com.siwo.dao.ScoreGoodsDetailDao;
import com.siwo.dao.ScoreGoodsNumDao;
import com.siwo.dao.ScoreOrderAndGoodsDao;
import com.siwo.dao.ScoreOrderDao;
import com.siwo.dao.ScoreRecipientDao;
import com.siwo.dao.ScoreShoppingCarDao;
import com.siwo.dao.ScoreSumDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.School;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreGoodsDetail;
import com.siwo.model.ScoreGoodsNum;
import com.siwo.model.ScoreOrder;
import com.siwo.model.ScoreOrderAndGoods;
import com.siwo.model.ScoreRecipient;
import com.siwo.model.Teacher;
import com.siwo.service.ScoreGoodsOrderService;

@Service
public class ScoreGoodsOrderSeviceImpl implements ScoreGoodsOrderService {

	@Autowired
	private ScoreOrderDao dao;
	@Autowired
	private ScoreOrderAndGoodsDao scoreOrderAndGoodsDao;

	@Autowired
	private ScoreGoodsDao goodsDao;
	@Autowired
	private ScoreGoodsDetailDao scoreGoodsDetailDao;
	@Autowired
	private ScoreSumDao sumDao;
	@Autowired
	private ScoreGoodsNumDao goodsNumDao;
	@Autowired
	private ScoreShoppingCarDao shoppingCarDao;
	
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private ScoreRecipientDao recipientDao;
	@Autowired
	private ScoreOrderDao orderDao;
	

	@Override
	public Map<String, Object> addOrder(ScoreOrder order) {
		Map<String, Object> map = new HashMap<String, Object>();
		String scoreGoodsNum = Tools.getOrderCode();// 订单号
		List<ScoreOrderAndGoods> list = order.getParams();
		int sum = 0;

		for (ScoreOrderAndGoods param : list) {
			param.setShoppingOrderId(order.getOrderNum());
			int num1 = param.getShoppingGoodsNum();
			sum += num1;
			//查询商品数量
			ScoreGoodsNum goodsNum= goodsNumDao.queryGoodsNumByGoodsId(param.getShoppingGoodsId());
			//查询商品信息
			ScoreGoods scoreGoods=goodsDao.queryGoodsDetailByGoodsId(param.getShoppingGoodsId());
			
			if (goodsNum.getGoodsNumRemain()>=param.getShoppingGoodsNum()&&param.getPrice()==scoreGoods.getGoodsScore()) {
				//可以兑换
			}else if(param.getPrice()!=scoreGoods.getGoodsScore()) {
				map.put("code", 4);
				return map;
			}
			else {
				
				map.put("code", 3);
//				map.put("msg", "");
				return map;
			}
		}
		order.setGoodsNum(sum);
		order.setOrderNum(scoreGoodsNum);
		order.setOrderFlag(1);
		order.setOrderTime(new Date());
		
		int res = dao.addOrder(order);
		if (res > 0) {
			// 增加商品订单关联表
			for (ScoreOrderAndGoods param : list) {
				param.setShoppingOrderId(order.getOrderNum());
			}
			int ress = scoreOrderAndGoodsDao.addScoreOrderAndGoods(list);
			//兑换成功
			if (ress > 0) {

				List<ScoreGoods> scoreGoods = scoreOrderAndGoodsDao.queryByOrderNum(order.getOrderNum());
				List<ScoreOrderAndGoods> orderAndGoods = order.getParams();

				// 修改商品数量

				for (ScoreOrderAndGoods scoreOrderAndGoods : orderAndGoods) {

					goodsNumDao.updateGoodsNum(scoreOrderAndGoods.getShoppingGoodsId(),
							scoreOrderAndGoods.getShoppingGoodsNum());
				}
				// 增加积分明细
				
				ScoreGoodsDetail detail = new ScoreGoodsDetail();
				
				// 根据订单号查询购买的商品
				String stringBuffer = "";
				for (ScoreGoods scoreGoods2 : scoreGoods) {
					stringBuffer += scoreGoods2.getGoodsName() + "、";
				}
				String str=stringBuffer.substring(0,stringBuffer.length()-1);
				detail.setDetailName("兑换商品:" + str );
				detail.setStudentId(order.getStudentId());
				detail.setDetailScore(order.getOrderScore());
				detail.setDetailFlag(1);
				detail.setDetailTypeName(order.getOrderNum() + "");
				detail.setDetailCreateTime(new Date());
				scoreGoodsDetailDao.addScoreGoodsDetail(detail);
				// 修改积分总数
				sumDao.cutScoreSumByStudentId(order.getStudentId(), order.getOrderScore());
				
				if (order.getFlag().equalsIgnoreCase("o")) {
					//不清空购物车
				}else {
					//清空购物车
					shoppingCarDao.deleteShoppingCarByStudentId(order.getStudentId());
				}
				map.put("code", 0);
				map.put("msg", "增加成功");
			} else {
				map.put("code", 1);
				map.put("msg", "增加失败");
			}
		} else {
			map.put("code", 1);
			map.put("msg", "增加失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryOrderByStudentId(Integer studentId,Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ScoreOrder> orders =null;
		if (flag==0) {
			// 全部
			orders= dao.queryOrderByStudentId(studentId);
		}else if (flag==1) {
			// 已收货
			orders= dao.queryOrderByStudentIdYSH(studentId);
		}else if (flag==2) {
			// 待收货
			orders= dao.queryOrderByStudentIdDSH(studentId);
		}
	
		for (ScoreOrder scoreOrder : orders) {
			// 根据订单号查询商品
			List<ScoreOrderAndGoods> scoreOrderAndGoods = scoreOrderAndGoodsDao.queryByOrderNums(scoreOrder.getOrderNum());
			for (ScoreOrderAndGoods scoreOrder2 : scoreOrderAndGoods) {
				ScoreGoods scoreGoods = new ScoreGoods();
				// 根据商品id查询商品
				scoreGoods = goodsDao.queryGoodsDetailByGoodsId(scoreOrder2.getShoppingGoodsId());
				scoreOrder2.setGoods(scoreGoods);
			}
			scoreOrder.setParams(scoreOrderAndGoods);
			if (scoreOrder.getOrderFlag() == 0) {
				scoreOrder.setFlag("已收货");
			}
			if (scoreOrder.getOrderFlag() == 1) {
				scoreOrder.setFlag("待收货");
			}
		}
		if (orders.size() != 0) {
			map.put("code", 0);
			map.put("data", orders);
		}
		return map;
	}

	@Override
	public Map<String, Object> queryOrderByOrderNum(String orderNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询出每个学生的订单信息
		ScoreOrder orders = dao.queryOrderByorderNum(orderNum);
		List<ScoreOrderAndGoods> scoreOrderAndGoods = scoreOrderAndGoodsDao.queryByOrderNums(orderNum);
		for (ScoreOrderAndGoods scoreOrder2 : scoreOrderAndGoods) {
			ScoreGoods scoreGoods = new ScoreGoods();
			// 根据商品id查询商品
			scoreGoods = goodsDao.queryGoodsDetailByGoodsId(scoreOrder2.getShoppingGoodsId());
			scoreOrder2.setGoods(scoreGoods);
		}
		orders.setParams(scoreOrderAndGoods);
		if (orders.getOrderFlag() == 0) {
			orders.setFlag("已收货");
		}
		if (orders.getOrderFlag() == 1) {
			orders.setFlag("待收货");
		}

		if (orders != null) {
			map.put("code", 0);
			map.put("data", orders);
		}
		return map;
	}

	@Override
	public Map<String, Object> queryOrderBySchoolId(Integer schoolId,String name,Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId==null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
		}else {
			List<ScoreOrder> orders=new ArrayList<ScoreOrder>();
			if (flag==null&&name.isEmpty()) {
				// 根据校区查询
				orders= dao.queryOrderBySchoolId(schoolId);
			}else if (flag!=null&&name.isEmpty()) {
				
				//根据状态查询
				orders = dao.queryGoodsByFlags(schoolId,flag);
			}else if(flag==null&&!name.isEmpty()) {
				//根据手机号查询
				orders = dao.queryGoodsByNames(schoolId,name);
			}else if(flag!=null&&!name.isEmpty()) {
				//根据手机号和状态查询
				orders=dao.queryGoodsByNamesAndFlags(schoolId, flag, name);
			}
			
			
			 if (orders.size()!=0) {
				
			
			for (ScoreOrder scoreOrder : orders) {
					if (scoreOrder.getOrderFlag()==0) {
						scoreOrder.setStatu("已收货");
					}
					if (scoreOrder.getOrderFlag()==1) {
						scoreOrder.setStatu("待收货");
					}
					School school=schoolDao.querySchoolById(scoreOrder.getSchoolId());
					scoreOrder.setSchoolName(school.getSchoolName());
					if (scoreOrder.getTeacherId()!=null) {
						Teacher teacher =teacherDao.queryTeacherById(scoreOrder.getTeacherId());
						scoreOrder.setTeacherName(teacher.getTeacherName());
					}
				//	
				}
			 }
			map.put("code", 0);
			map.put("data", orders);
		} 
		return map;
	}

	@Override
	public Map<String, Object> confirmReceipt(String orderNum, String flag,Integer teacherId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (orderNum.equals("")||flag.equals("")||teacherId==null) {
			if (orderNum.equals("")) {
				map.put("code", 1);
				map.put("msg", "orderNum不能为空");
			}
			if (flag.equals("")) {
				map.put("code", 1);
				map.put("msg", "flag不能为空");
			}
			if (teacherId==null) {
				map.put("code", 1);
				map.put("msg", "teacherId不能为空");
			}
		}else {
			//将订单状态修改为 已收货 收货时间为当前时间
			if (flag.equalsIgnoreCase("y")) {
				//查看该老师是否还是核销员
				ScoreRecipient recipient=recipientDao.queryRecipientByTeacherId(teacherId);
				//查看核销员和订单是否为一个校区
				Teacher teacher=teacherDao.queryTeacherById(teacherId);
				Integer teacherSchoolId=teacher.getSchoolId();//核销员所在校区
				ScoreOrder order= orderDao.queryOrderByorderNum(orderNum);
				Integer orderSchoolId=order.getSchoolId();//订单所在校区
				if (recipient!=null) {
					if (teacherSchoolId==orderSchoolId) {
						int res= dao.confirmReceipt(orderNum,teacherId,new Date());
						if (res>0) {
							map.put("code", 0);
							map.put("msg", "收货成功");
						}
					}else {
							map.put("code", 1);
							map.put("msg", "你不是该校区的核销员");
					}
				}else {
					map.put("code", 1);
					map.put("msg", "你当前不是核销员");
				}
				
			}
			if (flag.equalsIgnoreCase("n")) {
							
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> getOrderNum(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId==null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
		}else {
			//已收货数量
			List<ScoreOrder> ywc=dao.getOrderNum(schoolId,0);
			//待收货数量
			List<ScoreOrder> dsh=dao.getOrderNum(schoolId,1);

				map.put("code", 0);
				map.put("ywc", ywc.size());
				map.put("dsh",dsh.size() );
		 
		}
		return map;
	}

	@Override
	public Map<String, Object> queryOrderYwcAndDshNum(Integer schoolId, Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId==null||flag==null) {
			if (schoolId==null) {
				map.put("code", 1);
				map.put("msg", "studentId不能为空");
			}
			if (flag==null) {
				map.put("code", 1);
				map.put("msg", "flag不能为空");
			}
		}
		else {
			//订单信息
			List<ScoreOrder> orders=dao.getOrderNum(schoolId,flag);
			for (ScoreOrder scoreOrder : orders) {
				if (scoreOrder.getOrderFlag()==0) {
					scoreOrder.setStatu("已收货");
				}
				if (scoreOrder.getOrderFlag()==1) {
					scoreOrder.setStatu("待收货");
				}
				School school=schoolDao.querySchoolById(scoreOrder.getSchoolId());
				scoreOrder.setSchoolName(school.getSchoolName());
				if (scoreOrder.getTeacherId()!=null) {
					Teacher teacher =teacherDao.queryTeacherById(scoreOrder.getTeacherId());
					scoreOrder.setTeacherName(teacher.getTeacherName());
				}
			//	
			}
			map.put("code", 0);
			map.put("data", orders);
		 
		}
		return map;
	}

}
