package com.siwo.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.siwo.commons.ExcelUtil;
import com.siwo.dao.ApplyMessageDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.ScoreGoodsDao;
import com.siwo.dao.ScoreOrderDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.Apply;
import com.siwo.model.ApplyMessageStudent;
import com.siwo.model.Guardian;
import com.siwo.model.School;
import com.siwo.model.ScoreGoods;
import com.siwo.model.ScoreOrder;
import com.siwo.model.Student;
import com.siwo.model.StudentExcel;
import com.siwo.model.Teacher;
import com.siwo.service.ExcelService;
import com.siwo.service.GuardianService;

@Transactional
@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private MyClassDao classDao;

	@Autowired
	private ApplyMessageDao messageDao;

	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private GuardianDao guarDao;
	@Autowired
	private ScoreOrderDao scoreOrderDao;
	
	@Autowired
	private GuardianService guarService;
	
	@Autowired
	private TeacherDao teacherDao;

	/**
	 * 导入学生数据
	 */
	@Override
	public Map<String, Object> importExcul(MultipartFile file, String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Student> students = ExcelUtil.importExcel(file, 0, 2, Student.class);
		for (Student student : students) {
			Integer schoolId = schoolDao.querySchoolIdBySchoolName(student.getSchoolName());

			student.setSchoolId(schoolId);
			student.setStudentCreator(name);
			student.setStudentCreationTime(new Date());
			student.setCount(0);
			student.setFate(0);
			Integer classId = classDao.queryClassIdByName(student.getStudentClassName());
			student.setStudentClassId(classId);
			
			Student student2 = null;
			if (!StringUtils.isEmpty(student.getStudentPhone())) {
				student2 = studentDao.getStudentByPhoneAndSchoolId(student.getStudentPhone(), student.getSchoolId());
			}
			if (student2 == null) {
				student.setStudentCreationTime(new Date());
				student.setCount(0);

				Integer row = studentDao.addStudent(student);
				if (row > 0) {
					List<Guardian> guardians = student.getGuardians();
					if (guardians != null) {
						for (Guardian guardian : guardians) {
							guardian.setGuardianCreator(student.getStudentCreator());
							guardian.setGuardianCreationTime(new Date());
							guardian.setSchoolId(student.getSchoolId());
							Integer guardianId = guarService.addGuardian_2(guardian);
							if (guardianId != null) {
								Integer count = guarDao.queryGuardianAndStudent(student.getStudentId(), guardianId);
								if (count == 0) {
									guarDao.addGuardianStudent(guardianId, student.getStudentId(), guardian.getRelationship());
								}
							}
						}
					}
				} 
			} 
		}
		map.put("code", 0);
		map.put("msg", "导入成功");
		return map;
	}

	/**
	 * 导出学生数据
	 */
	@Override
	public void exportExculStudent(HttpServletResponse response, Integer schoolId) {
		List<StudentExcel> students = studentDao.getStudentExcelBySchoolId(schoolId);
		for (StudentExcel student : students) {
			School school = schoolDao.querySchoolById(schoolId);
			student.setSchoolName(school.getSchoolName());
			List<Guardian> guardians = guarDao.queryGuardianByStudentId(student.getStudentId());
			if (guardians.size() > 4) {
				int length = 4-guardians.size();
				for(int i= 0;i<length;i++) {
					guardians.add(new Guardian());
				}
			}
			try {
				student.setGuardian(guardians.get(0));
				student.setGuardian2(guardians.get(1));
				student.setGuardian3(guardians.get(2));
				student.setGuardian4(guardians.get(3));
			} catch (Exception e) {
				
			}
		}

		ExcelUtil.exportExcel(students, null, "学生列表", StudentExcel.class, "学生列表.xls", response);
	}

	/**
	 * 导出报名信息
	 */
	@Override
	public void exportExculApplyMessage(HttpServletResponse response, Integer schoolId) {
		List<ApplyMessageStudent> messages = messageDao.queryApplyMessageBySchoolId(schoolId);

		for (ApplyMessageStudent applyMessageStudent : messages) {
			Apply apply = messageDao.queryApplyByApplyMessageId(applyMessageStudent.getMessage().getApplyMessageId());
			applyMessageStudent.setApply(apply);
		}

		ExcelUtil.exportExcel(messages, null, "报名信息", ApplyMessageStudent.class, "报名信息.xls", response);
	}

	/**
	 * 导入监护人数据
	 */
	@Override
	public Map<String, Object> importGuardianExcul(MultipartFile file, String name) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Guardian> guardians = ExcelUtil.importExcel(file, 0, 1, Guardian.class);
			
			for (Guardian guardian : guardians) {
				Integer schoolId = schoolDao.querySchoolIdBySchoolName(guardian.getSchoolName());

				guardian.setGuardianCreationTime(new Date());
				guardian.setSchoolId(schoolId);
				guardian.setGuardianCreator(name);

				Guardian guardian2 = guarDao.queryGuardianByPhone(guardian.getGuardianPhone(), schoolId);
				if (guardian2 == null) {
					guarDao.addGuardian(guardian);
				}
			}
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "导入失败");
			return map;
		}

		map.put("code", 0);
		map.put("msg", "导入成功");

		return map;
	}
	 
	@Override
	public void exportExculGuardian(HttpServletResponse response, Integer schoolId) {

		List<Guardian> guardians = guarDao.getAllGuardian(schoolId);
		for (Guardian guardian : guardians) {
			School school = schoolDao.querySchoolById(schoolId);
			guardian.setSchoolName(school.getSchoolName());
		}
		ExcelUtil.exportExcel(guardians, null, "监护人信息", Guardian.class, "监护人信息.xls", response);
	}
	/**
	 * 订单导出
	 */
	@Override
	public void exportExculScoreOrder(HttpServletResponse response, Integer schoolId) {
		
		List<ScoreOrder> orders= scoreOrderDao.queryOrderBySchoolId(schoolId);
		int count=0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
		for (ScoreOrder scoreOrder : orders) {
			count++;
			if (scoreOrder.getOrderFlag()==0) {
				scoreOrder.setStatu("已收货");
				
				String addressTime=simpleDateFormat.format(scoreOrder.getAddressTime());
				
				
				scoreOrder.setAddressTimes(addressTime);
				//根据老师id查询出老师名称
				Teacher teacher=teacherDao.queryTeacherById(scoreOrder.getTeacherId());
				
				scoreOrder.setTeacherName(teacher.getTeacherName());
			}else if (scoreOrder.getOrderFlag()==1) {
				scoreOrder.setStatu("待收货");
				scoreOrder.setAddressTimes("待收货");
				scoreOrder.setTeacherName("待收货");
				
			}
			 
			School school=schoolDao.querySchoolById(scoreOrder.getSchoolId());
			scoreOrder.setSchoolName(school.getSchoolName());
			
			 String orderTime= simpleDateFormat.format(scoreOrder.getOrderTime());
			 scoreOrder.setOrderTimes(orderTime);
			 scoreOrder.setLiushui(count);
		}
		ExcelUtil.exportExcel(orders, null, "订单信息", ScoreOrder.class, "订单导出.xls", response);
	}
 

 

}
