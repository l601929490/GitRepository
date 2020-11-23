package com.siwo.serviceImpl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.SchoolDao;
import com.siwo.dao.ScoreGoodsDao;
import com.siwo.dao.ScoreGoodsNumDao;
import com.siwo.dao.ScoreGoodsTypeDao;
import com.siwo.dao.ScoreImgDao;
import com.siwo.dao.ScoreShoppingCarDao;
import com.siwo.model.School;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreGoodsType;
import com.siwo.service.ScoreGoodsTypeService;
@Service
public class ScoreGoodsTypeServiceImpl implements ScoreGoodsTypeService {
	@Autowired
	private ScoreGoodsTypeDao dao;
	@Autowired
	private ScoreGoodsDao goodsDao;
	@Autowired
	private ScoreGoodsNumDao numDao;
	@Autowired
	private ScoreImgDao imgDao;

	@Autowired
	private SchoolDao schoolDao;
	@Autowired
	private ScoreShoppingCarDao carDao;
	@Override
	public Map<String, Object> queryGoodsTypeByStudentId(Integer studentId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (studentId==null) {
			if (studentId==null) {
				map.put("code", 0);
				map.put("msg", "studentId不能为空");
			}
		}
		else {
			List<ScoreGoodsType> scoreGoodsTypes=dao.queryGoodsTypeByStudentId(studentId);
			if (scoreGoodsTypes!=null) {
				map.put("code", 0);
				map.put("data", scoreGoodsTypes);
			}else {
				map.put("code", 1);
				map.put("msg", "暂无数据");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> queryGoodsTypeBySchoolId(Integer schoolId) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (schoolId==null) {
			
			map.put("code", 0);
			map.put("msg", "schoolId不能为空");
		}else {
			List<ScoreGoodsType> scoreGoodsTypes=dao.queryGoodsTypeBySchoolId(schoolId);
			List<ScoreGoods> goods=null;
			for (ScoreGoodsType scoreGoodsType : scoreGoodsTypes) {
				//查询类别id下面的商品
				goods= goodsDao.queryGoodsByTypeId(scoreGoodsType.getTypeId());
				scoreGoodsType.setNum(goods.size());
				//查询校区名称
				School school=schoolDao.querySchoolById(scoreGoodsType.getSchoolId());
				scoreGoodsType.setSchoolName(school.getSchoolName());
			}
			if (scoreGoodsTypes!=null) {
				map.put("code", 0);
				map.put("data", scoreGoodsTypes);
			}else {
				map.put("code", 1);
				map.put("msg", "暂无数据");
			}
			
		}
		return map;
	}

	@Override
	public Map<String, Object> addGoodsType(ScoreGoodsType type) {
		Map<String,Object> map=new HashMap<String, Object>();
		
		int res=dao.addGoodsType(type);
		if (res>0) {
			map.put("code", 0);
			map.put("msg", "添加成功");
		}else {
			map.put("code", 1);
			map.put("msg", "添加失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> updateGoodsType(ScoreGoodsType type) {
		Map<String,Object> map=new HashMap<String, Object>();
		int res=dao.updateGoodsType(type);
		if (res>0) {
			map.put("code", 0);
			map.put("msg", "修改成功");
		}else {
			map.put("code", 1);
			map.put("msg", "修改失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteGoodsType(String [] typeId) {
		Map<String,Object> map=new HashMap<String, Object>();
		if (typeId==null) {
			map.put("code", 0);
			map.put("msg", "typeId不能为空");
		}else {
			int res=dao.deleteGoodsType(typeId);
//			if (res>0) {
				///查询出此类别下面的所有商品 ,删除
				//根据类型id查询出商品
				List<String> scoreGoods=new ArrayList<String>();
				//删除该类型下面的所有商品
//				for (String type : typeId) {
//					System.out.println("进来了");
//					scoreGoods=dao.queryGoodsByType(Integer.parseInt(type));
//					Object[] strings = scoreGoods.toArray();
//					String [] aStrings=new String[strings.length];
//					for (int i = 0; i < strings.length; i++) {
//						aStrings[i]=strings[i].toString();
//					}
//					System.out.println(aStrings+"集合");
//					int res3= goodsDao.deleteGoodsByGoodsId(aStrings);
//					int res1= numDao.deleteGoodsNumByGoodsId(aStrings);
//					int res2=imgDao.deleteGoodsImgByGoodsId(aStrings);
//					System.out.println(res3+"aaa");
//					System.out.println(res1+"bbb");
//					System.out.println(res2+"ccc");
//				}
					map.put("code", 0);
					map.put("msg", "删除成功");
				} 
		return map;
			}

	 
}
