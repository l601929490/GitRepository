package com.siwo.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siwo.dao.BulletinBoardDao;
import com.siwo.model.BulletinBoard;
import com.siwo.service.BulletinBoardService;

@Service
public class BulletinBoardServiceImpl implements BulletinBoardService{

	@Autowired
	private BulletinBoardDao dao;
	
	@Override
	public Map<String, Object> addBulletinBoard(BulletinBoard board) {
		Map<String, Object>map = new HashMap<String, Object>();
		try {
			if (board.getChecked() == 1) {
				dao.updateBulletinBoardChecked(2,board.getCompanyId());
			}
			dao.addBulletinBoard(board);
			map.put("code", 0);
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "添加失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteBulletinBoard(String[] bulletinBoardId) {
		Map<String, Object>map = new HashMap<String, Object>();
		try {
			dao.deleteBulletinBoard(bulletinBoardId);
			map.put("code", 0);
			map.put("msg", "删除成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "删除失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> updateBulletinBoard(BulletinBoard board) {
		Map<String, Object>map = new HashMap<String, Object>();
		try {
			dao.updateBulletinBoard(board);
			map.put("code", 0);
			map.put("msg", "修改成功");
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "修改失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> limitBulletinBoard(Integer page,Integer limit,Integer companyId) {
		Map<String, Object>map = new HashMap<String, Object>();
		try {
			PageHelper.startPage(page, limit);
			System.out.println(companyId);
			List<BulletinBoard> boards = dao.queryAllBulletinBoard(companyId);
			PageInfo<BulletinBoard>pageInfo = new PageInfo<BulletinBoard>(boards);
			map.put("code", 0);
			map.put("data", pageInfo.getList());
			map.put("pageCount", pageInfo.getTotal());
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> queryBulletinBoardByCompanyIdAndChecked(Integer companyId) {
		Map<String, Object>map = new HashMap<String, Object>();
		try {
			BulletinBoard board = dao.queryBulletinBoardByCompanyIdAndChecked(companyId);
			map.put("code", 0);
			map.put("data", board);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "查询失败");
			return map;
		}
		return map;
	}

}
