package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.siwo.commons.SchoolUntil;
import com.siwo.dao.CompanyDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.Admin;
import com.siwo.model.Company;
import com.siwo.model.School;
import com.siwo.model.SchoolHonor;
import com.siwo.model.SchoolSlideshow;
import com.siwo.model.Teacher;
import com.siwo.service.SchoolService;
import com.siwo.service.ScoreGetScoreService;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolDao dao;

	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private CompanyDao comDao;
	
	@Autowired
	private SchoolUntil until;
	@Autowired
	private ScoreGetScoreService service;
	
	@Override
	public Map<String, Object> addSchool(School school, BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put("code", 1);
				map.put("msg", fieldError.getDefaultMessage());
			}
			return map;
		}

		Company company = comDao.queryCompanyByCompanyId(school.getCompanyId());
		List<School> schools = dao.querySchoolByCompanyId(company.getCompanyId());
		if (company.getCompanyCampus() > schools.size()) {
			Integer row = dao.addSchool(school);
			if (row > 0) {
				//添加积分配置 默认都是0  ，禁用状态
				service.addDynamicCondition(school.getSchoolId());
				map.put("code", 0);
				map.put("msg", "添加成功");
			} else {
				map.put("code", 1);
				map.put("msg", "添加失败");
			}
		}else {
			map.put("code", 1);
			map.put("msg", "已超过添加数量");
		}

		return map;
	}

	@Override
	public Map<String, Object> limitSchool(Integer page, Integer limit,Integer companyId) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<School> schools = dao.limitSchoolByCompanyId(start, limit, companyId);

		int count = dao.getCompanySchoolCount(companyId);

		if (schools != null) {
			map.put("code", 0);
			map.put("data", schools);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> addSchoolSlideshow(String slideshowImg, Integer schoolId,Date slideshowCreationTime,String slideshowCreator) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (slideshowImg != null && schoolId != null) {
			Integer row = dao.addSchoolSlideshow(slideshowImg, schoolId,slideshowCreationTime,slideshowCreator);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "添加成功");
			} else {
				map.put("code", 1);
				map.put("msg", "添加失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> addSchoolHonor(String honorImg, Integer schoolId,String honorCreator,Date honorCreationTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (honorImg != null && schoolId != null) {
			Integer row = dao.addSchoolHonor(honorImg, schoolId,honorCreator,honorCreationTime);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "添加成功");
			} else {
				map.put("code", 1);
				map.put("msg", "添加失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> limitSchoolSlideshow(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<SchoolSlideshow> schools = dao.limitSchoolSlideshow(start, limit, schoolId);
		
		int count = dao.getSchoolSlideshowCount(schoolId);

		if (schools != null) {
			map.put("code", 0);
			map.put("data", schools);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> limitSchoolHonor(Integer page, Integer limit, Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<SchoolHonor> schools = dao.limitSchoolHonor(start, limit, schoolId);

		int count = dao.getSchoolHonorCount(schoolId);

		if (schools != null) {
			map.put("code", 0);
			map.put("data", schools);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> updateSchoolSlideshow(SchoolSlideshow slideshow) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (slideshow != null) {
			Integer row = dao.updateSchoolSlideshow(slideshow);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "修改成功");
			} else {
				map.put("code", 1);
				map.put("msg", "修改失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> updateSchoolHonor(SchoolHonor honor) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (honor != null) {
			Integer row = dao.updateSchoolHonor(honor);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "修改成功");
			} else {
				map.put("code", 1);
				map.put("msg", "修改失败");
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteSchoolSlideshowById(Integer schoolSlideshowId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer row = dao.deleteSchoolSlideshowById(schoolSlideshowId);
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		}else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteManySchoolSlideshow(String[] schoolSlideshowId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(schoolSlideshowId != null) {
			Integer row = dao.deleteManySchoolSlideshow(schoolSlideshowId);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}else {
			map.put("code", 1);
			map.put("msg", "学生id不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteSchoolSchoolHonorId(Integer schoolHonorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer row = dao.deleteSchoolSchoolHonorId(schoolHonorId);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "删除成功");
		}else {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteManySchoolHonor(String[] SchoolHonorId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(SchoolHonorId != null) {
			Integer row = dao.deleteManySchoolHonor(SchoolHonorId);
			if (row > 0) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "删除失败");
			}
		}else {
			map.put("code", 1);
			map.put("msg", "学生id不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> querySchoolBySchoolId(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (schoolId != null) {
			School school = dao.querySchoolBySchoolId(schoolId);
			if (school != null) {
				
				List<Teacher> teachers = teacherDao.queryShowTeacherBySchoolId(schoolId);
				
				map.put("code", 0);
				map.put("data", school);
				map.put("teachers", teachers);
				
			}else {
				map.put("code", 1);
				map.put("data", "查询失败");
			}
		}else {
			map.put("code", 0);
			map.put("msg", "schoolId不能为空");
		}
		return map;
	}

	@Override
	public Map<String, Object> queryCompanyBySchoolId(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (schoolId == null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
			return map;
		}
		Company company = comDao.queryCompanyBySchoolId(schoolId);
		
		if (company == null) {
			map.put("code", 1);
			map.put("msg", "该机构不存在");
			return map;
		}
		
		List<School>schools = dao.querySchoolByCompanyId(company.getCompanyId());
		if (schools == null || schools.size() == 0) {
			map.put("code", 1);
			map.put("msg", "该校区不存在");
			return map;
		}
		
		map.put("code", 0);
		map.put("data", schools);
		return map;
	}

	@Override
	public Map<String, Object> querySchoolByCompanyId(Integer companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (companyId == null) {
			map.put("code", 1);
			map.put("msg", "companyId不能为空");
			return map;
		}
		
		List<School> schools = dao.querySchoolByCompanyId(companyId);
		if (schools.size() != 0) {
			map.put("code", 0);
			map.put("data", schools);
		}else {
			map.put("code", 1);
			map.put("data", "暂无分校");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> querySchoolById(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (schoolId == null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
			return map;
		}
		
		School school = dao.querySchoolById(schoolId);
		
		if (school == null) {
			map.put("code", 1);
			map.put("msg", "暂无数据");
			return map;
		}
		
		map.put("code", 0);
		map.put("data", school);
		
		return map;
	}

	@Override
	public Map<String, Object> updateSchool(School school) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (school.getSchoolId() == null) {
			map.put("code", 1);
			map.put("msg", "id不能为空");
			return map;
		}
		
		Integer row = dao.updateSchool(school);
		
		if (row > 0) {
			map.put("code", 0);
			map.put("msg", "修改成功");
		}else {
			map.put("code", 1);
			map.put("msg", "修改失败");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> deleteManySchool(String[] schoolIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dao.deleteManySchool(schoolIds);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		map.put("code", 0);
		map.put("msg", "删除成功");
		return map;
	}

	@Override
	public Map<String, Object> wxQuerySchoolByCompanyId(Integer companyId, String phone,Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<School> schools = until.selectSchool(companyId, phone);
			if (schools.size() == 0) {
				return querySchoolByCompanyId(companyId);
			}else {
				School school = dao.querySchoolBySchoolId(schoolId);
				Set<School>set = new HashSet<School>(schools);
				set.add(school);
				map.put("code", 0);
				map.put("data", set);
			}
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> switchToSchoolId(Integer schoolId, String phone,String openId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> list = until.selectId(schoolId, phone,openId);
			map.put("code", 0);
			map.put("data", list);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> superAdminLimitSchool(Integer page, Integer limit) {

		Map<String, Object> map = new HashMap<String, Object>();

		Integer start = (page - 1) * limit;

		List<School> schools = dao.limitSchool(start, limit);

		int count = dao.getSchoolCount();

		if (schools != null) {
			map.put("code", 0);
			map.put("data", schools);
			map.put("pageCount", count);
		} else {
			map.put("code", 1);
			map.put("data", "暂无数据");
		}

		return map;
	}

	@Override
	public Map<String, Object> querySchoolIdLikeSchoolName(String schoolName,Integer companyId) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(schoolName)) {
			map.put("code", 1);
			map.put("msg", "校区名不能为空");
			return map;
		}
		try {
			schoolName = "%" + schoolName + "%";
			List<School> admins = null;
			if (companyId == null) {
				admins = dao.querySchoolIdLikeSchoolName(schoolName);
			}else {
				admins = dao.querySchoolLikeSchoolNameAndByCompanyId(schoolName, companyId);
			}
			
			map.put("code", 0);
			map.put("data", admins);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "服务器异常");
		}
		return map;
	}

}
