package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.ScoreRecipientDao;
import com.siwo.model.ScoreRecipient;
import com.siwo.service.ScoreRecipientService;

@Service
public class ScoreRecipientServiceImpl implements ScoreRecipientService {

	@Autowired
	private ScoreRecipientDao dao;

	@Override
	public Map<String, Object> changeScoreRecipient(String[] strs, String flag) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (flag.equalsIgnoreCase("y")) {
			// 绑定核销员
			// 查询是否绑定过

			for (String string : strs) {
				// 根据老师id查询
				ScoreRecipient recipient = dao.queryRecipientByTeacherId(Integer.parseInt(string));
				if (recipient == null) {
					int res = dao.addScoreRecipient(Integer.parseInt(string));
					if (res > 0) {
						map.put("code", 0);
						map.put("msg", "绑定成功");
					} else {
						map.put("code", 1);
						map.put("msg", "绑定失败");
					}
				}else {
					map.put("code", 0);
					map.put("msg", "绑定成功");
				}
			}

		}
		if (flag.equalsIgnoreCase("n")) {
			// 解绑
			for (String string : strs) {
				// 根据老师id查询
				ScoreRecipient recipient = dao.queryRecipientByTeacherId(Integer.parseInt(string));
				if (recipient != null) {
					int res = dao.deleteScoreRecipient(Integer.parseInt(string));
					if (res > 0) {
						map.put("code", 0);
						map.put("msg", "解绑成功");
					} else {
						map.put("code", 1);
						map.put("msg", "解绑失败");
					}
				}else {
					map.put("code", 0);
					map.put("msg", "解绑成功");
				} 

			}
		}
		return map;
	}

	@Override
	public Map<String, Object> queryRecipientByTeacherId(Integer teacherId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (teacherId == null) {
			map.put("code", 1);
			map.put("msg", "teacher不能为空");
		} else {
			// 根据teacherid查询是否是核销员
			ScoreRecipient recipient = dao.queryRecipientByTeacherId(teacherId);
			if (recipient != null) {
				map.put("code", 1);
				map.put("status", "y");
			} else {
				map.put("code", 1);
				map.put("status", "n");
			}
		}
		return map;
	}

}
