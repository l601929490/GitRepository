package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jcajce.provider.asymmetric.ec.GMSignatureSpi.sha256WithSM2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.CompanyDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.ScoreGoodsDao;
import com.siwo.dao.ScoreGoodsNumDao;
import com.siwo.dao.ScoreGoodsTypeDao;
import com.siwo.dao.ScoreImgDao;
import com.siwo.dao.ScoreShoppingCarDao;
import com.siwo.dao.ScoreSumDao;
import com.siwo.dao.StudentDao;
import com.siwo.model.Company;
import com.siwo.model.Guardian;
import com.siwo.model.School;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreGoodsNum;
import com.siwo.model.ScoreGoodsType;
import com.siwo.model.ScoreImg;
import com.siwo.model.ScoreOrder;
import com.siwo.model.ScoreShoppingCar;
import com.siwo.model.ScoreSum;
import com.siwo.model.Student;
import com.siwo.model.Teacher;
import com.siwo.service.ScoreGoodsService;

@Service
public class ScoreGoodsServiceImpl implements ScoreGoodsService {

	@Autowired
	private ScoreGoodsDao dao;
	@Autowired
	private ScoreImgDao imgDao;

	@Autowired
	private ScoreShoppingCarDao shoppingCarDao;
	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private ScoreSumDao sumDao;
	@Autowired
	private ScoreGoodsNumDao numDao;
	@Autowired
	private ScoreGoodsTypeDao typeDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private GuardianDao guardianDao;
	@Autowired
	private CompanyDao companyDao;


	@Override
	public Map<String, Object> queryGoodsBySchoolId(Integer schoolId,Integer typeId,String name) {
		Map<String, Object> map = new HashMap<String, Object>();
			if (schoolId==null) {
				map.put("code", 1);
				map.put("data", "schoolId不能为空");
		}else {
			List<ScoreGoods> scoreGoods =new ArrayList<ScoreGoods>();
			if (typeId==null&&name.equals("")) {
				// 根据校区查询
				scoreGoods = dao.queryGoodsBySchoolIds(schoolId);
			}else if (typeId!=null&&name.equals("")) {
				//根据类别查询
				scoreGoods = dao.queryGoodsByTypeIds(schoolId,typeId);
			}else if(typeId==null&&name!="") {
				//根据名称查询
				scoreGoods = dao.queryGoodsByNames(schoolId,name);
			}else if(typeId!=null&&name!="") {
				scoreGoods=dao.queryGoodsByNamesAndTypes(schoolId, typeId, name);
			}
			for (ScoreGoods scoreGoods2 : scoreGoods) {
				School school=schoolDao.querySchoolById(scoreGoods2.getGoodsSchool().getSchoolId());
				scoreGoods2.setSchoolName(school.getSchoolName());
				ScoreGoodsType scoreGoodsType= typeDao.queryGoodsTypeByTypeId(scoreGoods2.getGoodsType());
				
				scoreGoods2.setTypeName(scoreGoodsType.getTypeName());
				//根据商品id查询图片
				List<ScoreImg> goods = imgDao.queryGoodsImgByGoodsId(scoreGoods2.getGoodsId(),0);//轮播图
				List<ScoreImg> goods2 = imgDao.queryGoodsImgByGoodsId(scoreGoods2.getGoodsId(),1);//详情图
				scoreGoods2.setLbtSeImgs(goods);
				scoreGoods2.setSeImgs(goods2); 
				
			}
			if (scoreGoods != null) {
				map.put("code", 0);
				map.put("data", scoreGoods);
			} else {
				map.put("code", 1);
				map.put("data", "暂无数据");
			}
		}
		return map;
	}

	private void queryGoodsTypeByTypeId(Integer goodsType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> queryGoodsByStudentId(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId == null) {
			map.put("code", 1);
			map.put("data", "studentId不能为空");
		}

		List<ScoreGoods> gScoreGoods = dao.queryGoodsByStudentId(studentId);

		if (gScoreGoods != null) {
			
			map.put("code", 0);
			map.put("data", gScoreGoods);

		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryGoodsLikeByStudentId(Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (studentId==null) {
			if (studentId==null) {
				map.put("code", 0);
				map.put("msg", "studentId不能为空");
			}
		 
		}else {
			List<ScoreGoods> gScoreGoods = dao.queryGoodsLikeByStudentId(studentId);
			if (gScoreGoods != null) {
				map.put("code", 0);
				map.put("data", gScoreGoods);
			} else {
				map.put("code", 1);
				map.put("data", "暂无数据");
			}
		}
		
		return map;
	}

	@Override
	public Map<String, Object> queryGoodsDetailByGoodsId(Integer studentId, Integer goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (goodsId == null) {
			map.put("code", 1);
			map.put("data", "goodsId不能为空");
			return map;
		}
		ScoreGoods gScoreGoods = dao.queryGoodsDetailByGoodsId(goodsId);
		if (gScoreGoods != null) {
			// 根据商品id查询出所有商品相关的图片
			List<ScoreImg> imgs = imgDao.queryImgByGoodsId(goodsId);
			if (imgs != null) {
				//根据学生查询校区信息
				Student student=studentDao.queryById(studentId);
				
				// 查询此人购物车的是否有商品
				ScoreShoppingCar shoppingCar = shoppingCarDao.queryShoppingCarAndGoodsId(studentId, goodsId);
				Integer status = 1;
				if (shoppingCar != null) {
					status = 0;
				} else {
					status = 1;
				}
				// 查询孩子的积分
				ScoreSum scoreSum = sumDao.queryScoreSumBystudentId(studentId);
				gScoreGoods.setSeImgs(imgs);
				map.put("code", 0);
				map.put("data", gScoreGoods);
				if(scoreSum!=null) {
					if (scoreSum.getSum() == null) {
						scoreSum.setSum(0);
					}
					if (scoreSum.getConsumption() == null) {
						scoreSum.setConsumption(0);
					}
					if (scoreSum.getRemain() == null) {
						scoreSum.setRemain(0);
					}
				}else {
					//增加一条数据
					ScoreSum scoreSum2=new ScoreSum();
					scoreSum2.setStudentId(studentId);
					scoreSum2.setSum(0);
					scoreSum2.setRemain(0);
					scoreSum2.setConsumption(0);
					
					sumDao.addScoreSumByStudentId(scoreSum2); 
				}
				map.put("score", scoreSum.getRemain());
				map.put("status", status);
			}

		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryGoodsByTypeId(Integer typeId, Integer studentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (typeId == null || studentId == null) {
			if (typeId == null) {
				map.put("code", 1);
				map.put("data", "typeId不能为空");
				return map;
			}
			if (studentId == null) {
				map.put("code", 1);
				map.put("data", "schoolId不能为空");
				return map;
			}
			
			
		} else {
			List<ScoreGoods> list = null;
			if (typeId==-1||typeId==0) {
				if (typeId==-1) {
					list=dao.queryGoodsAll(studentId);
				}
				if (typeId==0) {
					list=dao.queryGoodsOKexchange(studentId);
				}
			}
			else {
				
				list=dao.queryGoodsByTypeId(typeId);
			}

			Collections.sort(list, new Comparator<ScoreGoods>() {

				@Override
				public int compare(ScoreGoods o1, ScoreGoods o2) {
					
					return o1.getGoodsScore() - o2.getGoodsScore();
				}
			});
			// 根据学生id和商品id查询购物车里有没有 此商品
			for (ScoreGoods scoreGoods : list) {
				if (scoreGoods != null) {
					
					ScoreShoppingCar scoreShoppingCar = shoppingCarDao.queryShoppingCarAndGoodsId(studentId,scoreGoods.getGoodsId());
					if (scoreShoppingCar != null) {
						scoreGoods.setStatus(0);
					} else {
						scoreGoods.setStatus(1);
					}
					map.put("code", 0);
					map.put("data", list);
				} else {
					map.put("code", 1);
					map.put("msg", "暂无数据");
				}
			}
			
		}
		return map;
	}

	@Override
	public Map<String, Object> addScoreGoods(ScoreGoods sGoods) {
		Map<String, Object> map = new HashMap<String, Object>();
		sGoods.setGoodsCreateTime(new Date());	
		int res = dao.addScoreGoods(sGoods);
		
		
		if (res > 0) {
			ScoreGoodsNum sGoodsNum = sGoods.getGoodsNum();
			sGoodsNum.setGoodsNumRemain(sGoodsNum.getGoodsNumSum());
			sGoodsNum.setGoodsNumSale(0);
			sGoodsNum.setGoodsId(sGoods.getGoodsId());
			sGoodsNum.setGoodsNumBasicSale(sGoodsNum.getGoodsNumBasicSale());//增加基本销量
			numDao.addScoreGoodsNum(sGoodsNum);
			// 增加轮播图
			ScoreImg scoreImg = null;
			for (ScoreImg scoreImg2 : sGoods.getLbtSeImgs()) {
				scoreImg = new ScoreImg();
				scoreImg.setImgType(0);
				scoreImg.setImgName(scoreImg2.getImgName());
				scoreImg.setGoodsId(sGoods.getGoodsId());
				
				imgDao.addScoreGoodsImg(scoreImg);
			}

			// 详情图片
			ScoreImg scoreImg3 = null;
			for (ScoreImg scoreImg4 : sGoods.getSeImgs()) {
				scoreImg3 = new ScoreImg();
				scoreImg3.setImgType(1);
				scoreImg3.setGoodsId(sGoods.getGoodsId());
				scoreImg3.setImgName(scoreImg4.getImgName());
				imgDao.addScoreGoodsImg(scoreImg3);
			}

			map.put("code", 0);
			map.put("msg", "增加成功");
		} else {
			map.put("code", 1);
			map.put("msg", "增加失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> searchGoodsByName(Integer type,String name,Integer studentId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (name.equals("")||studentId==null||type==null) {
			if (name.equals("")) {
				map.put("code", 1);
				map.put("msg", "name不能为空");
			}
			if (studentId==null) {
				map.put("code", 1);
				map.put("msg", "studentId不能为空");
			}
			if (type==null) {
				map.put("code", 1);
				map.put("msg", "type不能为空");
			}
	
			
		}else {
			List<ScoreGoods> goods=null;
			if (type==-1||type==0) {
				if (type==-1) {//全部商品
					goods= dao.searchGoodsByName(name,studentId);
				}
				if (type==0) {//可兑换商品
					goods=dao.searchGoodsOKexchange(name,studentId);
				}
			}else {
				 //其他类型的
				goods=dao.searchGoodsByOtherTypeId(type,name,studentId);
			}
			
			for (ScoreGoods scoreGoods : goods) {
				if (scoreGoods != null) {
				 
					ScoreShoppingCar scoreShoppingCar = shoppingCarDao.queryShoppingCarAndGoodsId(studentId,scoreGoods.getGoodsId());
					if (scoreShoppingCar != null) {
						scoreGoods.setStatus(0);
					} else {
						scoreGoods.setStatus(1);
					}
					map.put("code", 0);
					map.put("data", goods);
				} else {
					map.put("code", 1);
					map.put("msg", "暂无数据");
				}
			}
		
			if (goods.size()!=0) {
				map.put("code", 0);
				map.put("data", goods);
			}else {
				map.put("code", 1);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> offShelfGoodsByGoodsId(Integer goodsId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (goodsId==null) {
			map.put("code", 1);
			map.put("msg", "goodsId不能为空");
		}else {
			int res= dao.offShelfGoodsByGoodsId(goodsId);
			if (res>0) {
				map.put("code", 0);
				map.put("msg", "下架成功");
			}else {
				map.put("code", 1);
				map.put("msg", "下架失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteGoodsByGoodsId(String[] goodsId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (goodsId==null) {
			map.put("code", 1);
			map.put("msg", "goodsId不能为空");
		}else {
			int res= dao.deleteGoodsByGoodsId(goodsId);
			if (res>0) {
				int res1= numDao.deleteGoodsNumByGoodsId(goodsId);
				if (res1>0) {
					int res2=imgDao.deleteGoodsImgByGoodsId(goodsId);
			 
						map.put("code", 0);
						map.put("msg", "删除成功");
					}else {
						map.put("code", 1);
						map.put("msg", "删除失败");
					}
				 
				}
				
		}
		return map;
	}
 
	@Override
	public Map<String, Object> updateScoreGoods(ScoreGoods sGoods) {
		Map<String, Object> map=new HashMap<String, Object>();
		ScoreGoodsNum numGoods=numDao.queryGoodsNumByGoodsId(sGoods.getGoodsId());
		
		//改变的值
		Integer change=sGoods.getGoodsNum().getGoodsNumSum()-numGoods.getGoodsNumSum();
		//改变库存的值
		Integer kc=numGoods.getGoodsNumRemain()+change;
		sGoods.getGoodsNum().setGoodsNumRemain(kc);
		
		
		
		int res= dao.updateScoreGoods(sGoods);
	 
			
			//删除图片以及轮播图重新添加 
			//删除轮播图和详情图片
			int ress= imgDao.deleteGoodsImgByGoodsIdOne(sGoods.getGoodsId());
			
//				// 增加轮播图
			
				ScoreImg scoreImg = null;
				for (ScoreImg scoreImg2 : sGoods.getLbtSeImgs()) {
					scoreImg = new ScoreImg();
					scoreImg.setImgType(0);
					scoreImg.setImgName(scoreImg2.getImgName());
					scoreImg.setGoodsId(sGoods.getGoodsId());
					imgDao.addScoreGoodsImg(scoreImg);
				}
				//增加详情图片
				ScoreImg scoreImg3 = null;
				for (ScoreImg scoreImg4 : sGoods.getSeImgs()) {
					scoreImg3 = new ScoreImg();
					scoreImg3.setImgType(1);
					scoreImg3.setGoodsId(sGoods.getGoodsId());
					scoreImg3.setImgName(scoreImg4.getImgName());
					imgDao.addScoreGoodsImg(scoreImg3);
				}
				map.put("code", 0);
				map.put("msg", "修改成功");
			 

		 
		return map;
	}

	 
	
}
