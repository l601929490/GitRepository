package com.siwo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.siwo.dao.ScoreOrderAndGoodsDao;
import com.siwo.model.Receive;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreOrder;
import com.siwo.model.ScoreOrderPage;
import com.siwo.service.ScoreGoodsOrderService;
import net.sf.json.JSONObject;

@RestController
public class ScoreOrderController {

	@Autowired
	private ScoreGoodsOrderService service;
	@Autowired
	private ScoreOrderAndGoodsDao scoreOrderAndGoodsDao;

	@PostMapping("/applet/addOrder")
	public Map<String, Object> addOrder(@RequestBody ScoreOrder order) {
		return service.addOrder(order);
	}

	/**
	 * 查询学生兑换详情
	 * 
	 * @param studentId
	 * @return
	 */
	@GetMapping("/applet/queryOrderByStudentId")
	public Map<String, Object> queryOrderByStudentId(Integer studentId, Integer flag) {
		return service.queryOrderByStudentId(studentId, flag);
	}

	/**
	 * 根据订单号查询商品详情
	 * 
	 * @param orderNum
	 * @return
	 */
	@GetMapping("/applet/queryOrderByOrderNum")
	public Map<String, Object> appletqueryOrderByOrderNum(String orderNum) {

		return service.queryOrderByOrderNum(orderNum);
	}

	@GetMapping("/queryOrderByOrderNum")
	public Map<String, Object> queryOrderByOrderNum(String orderNum) {

		return service.queryOrderByOrderNum(orderNum);
	}

	/**
	 * 测试
	 * 
	 * @return
	 */
	@GetMapping("/test1")
	public Long testOrder() {
//		List<ScoreGoods> scoreGoods = scoreOrderAndGoodsDao.queryByOrderNum("2020091402083121645");
//		String stringBuffer = "";
//		for (ScoreGoods scoreGoods2 : scoreGoods) {
//			stringBuffer += scoreGoods2.getGoodsName() + "、";
//			
//		}
//		System.out.println(stringBuffer+"");
//		String str= stringBuffer.substring(0,stringBuffer.length()-1);
		return System.currentTimeMillis();

	}

	/**
	 * 生成二维码
	 * 
	 * @param uid
	 * @param type
	 * @param req
	 * @return
	 */
	@GetMapping("applet/getOrderImage")
	public Map<String, Object> code(String uid, Integer type, HttpServletRequest req) {
		Map<String, Object> mapz = new HashMap<>();
		String path = null;
		String codeName = null;
		String imageType = null;
		String content = null;
		List<Integer> list = new ArrayList();
		try {
			// Map <String,Object> map= orderService.getAllOrderByOpenID(openId);
			// List<PGoodsOrder> orders=(List<PGoodsOrder>) map.get("data");
			Receive receive = new Receive();
			receive.setUid(uid);
			receive.setFlag(type);
			JSONObject json = JSONObject.fromObject(receive);
			String jsonString = json.toString();
			// 根据openId查询出所有订单信息订单信息
			path = "C:\\myFile\\upload";// 二维码保存的路径
			codeName = UUID.randomUUID().toString();// 二维码的图片名
			imageType = "jpg";// 图片类型
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(jsonString, BarcodeFormat.QR_CODE, 400, 400, hints);
			File file1 = new File(path, codeName + "." + imageType);
			MatrixToImageWriter.writeToFile(bitMatrix, imageType, file1);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		String savePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
				+ req.getContextPath();
		mapz.put("img", savePath + "//" + codeName + "." + imageType);
		return mapz;
	}

	/**
	 * 查询机构下面每个校区的订单信息
	 */
	@PostMapping("/queryOrderBySchoolId")
	public Map<String, Object> queryOrderBySchoolId(@RequestBody ScoreOrderPage orderPage) {

		Integer schoolId = orderPage.getSchoolId();// 校区
		String phone = orderPage.getPhone();// 类型
		Integer pageNo = orderPage.getPageNo();// 页数
		Integer flag = orderPage.getFlag();// 状态
		Integer pageNum = 10;
		Page page = PageHelper.startPage(pageNo, pageNum);
		Map<String, Object> map = new HashMap<>();
		map = service.queryOrderBySchoolId(schoolId, phone, flag);
		List<ScoreOrder> goodsList = (List<ScoreOrder>) map.get("data");
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
//		            map.put("ps",pageSize);
			map.put("pages", pages);
			map.put("total", total);
			map.put("pageNum", pageNum);
		}
		return map;
	};

	// 确认收货
	@GetMapping("/applet/queryOrderByCompanyId")
	public Map<String, Object> confirmReceipt(String orderNum, String flag, Integer teacherId) {
		return service.confirmReceipt(orderNum, flag, teacherId);
	};

	// 查询订单已完成和待收货的数量
	@GetMapping("getOrderNum")
	public Map<String, Object> getOrderNum(Integer schoolId) {
		return service.getOrderNum(schoolId);
	};

	// 查询已完成或者待收货信息
	@GetMapping("queryOrderYwcAndDshNum")
	public Map<String, Object> queryOrderYwcAndDshNum(Integer schoolId, Integer flag) {
		return service.queryOrderYwcAndDshNum(schoolId, flag);
	};

}
