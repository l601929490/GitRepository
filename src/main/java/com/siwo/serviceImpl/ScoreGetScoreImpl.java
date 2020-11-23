package com.siwo.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.siwo.dao.AttendanceDao;
import com.siwo.dao.DynamicConditionDao;
import com.siwo.dao.RecipientDao;
import com.siwo.dao.ScoreDisableDao;
import com.siwo.dao.ScoreGetScoreDao;
import com.siwo.dao.ScoreGoodsDetailDao;
import com.siwo.dao.ScoreSumDao;
import com.siwo.dao.StudentDao;
import com.siwo.model.Attendance;
import com.siwo.model.DynamicCondition;
import com.siwo.model.ScoreDisable;
import com.siwo.model.ScoreGetScore;
import com.siwo.model.ScoreGoodsDetail;
import com.siwo.model.ScoreRecipient;
import com.siwo.model.ScoreSum;
import com.siwo.model.Student;
import com.siwo.service.ScoreGetScoreService;

import io.lettuce.core.output.SocketAddressOutput;

@Service
public class ScoreGetScoreImpl implements ScoreGetScoreService {

	@Autowired
	private ScoreGetScoreDao dao;

	@Autowired
	private StudentDao stuDao;

	@Autowired
	private DynamicConditionDao dConditionDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private ScoreSumDao scoreSumDao;
	@Autowired
	private ScoreGoodsDetailDao detailDao;

	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private RecipientDao recipientDao;
	@Autowired
	private DynamicConditionDao dynamicConditionDao;
	@Autowired
	private ScoreDisableDao disableDao;
	

	@Override
	public Map<String, Object> addScore(ScoreGetScore score) {
		Map<String, Object> map = new HashMap<String, Object>();
		score.setCreateTime(new Date());
		int res = dao.addScore(score);    
		if (res > 0) {
			map.put("code", 0);
			map.put("msg", "增加成功");
		} else {
			map.put("code", 1);
			map.put("msg", "增加失败");
		}
		return map;
	}

	// 签到
	@Override
	public Map<String, Object> scoreService(Integer uid, Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 监护人
		if (flag == 3) {
			// 查找监护人的孩子
			List<Student> students = stuDao.getGuardianStudent(uid);
			
			
			// 查找孩子今天是否签过到
			for (Student student : students) {
				int qd = dao.quertCount(student.getStudentId(), "qd");
				if (qd == 0) {// 没签到
					// 查询积分
					DynamicCondition dynamicCondition = dConditionDao.queryDynamicConditionBySchoolId(student.getSchoolId());
					ScoreDisable disable= disableDao.queryScoreDisable(student.getSchoolId());
					if (dynamicCondition != null && dynamicCondition.getLoginDynamicCondition() != null) {
						if (disable.getDisableFlag()==0) {
							
						
						ScoreGetScore score = new ScoreGetScore();
						score.setStudentId(student.getStudentId());
						score.setRuleName("qd");
						score.setRuleScore(dynamicCondition.getLoginDynamicCondition());
						score.setCreateTime(new Date());
						dao.addScore(score);
						// 给积分总表累加
						ScoreSum scoreSum = scoreSumDao.queryScoreSumBystudentId(student.getStudentId());
						if (scoreSum != null) {
							scoreSumDao.updateScoreSumByStudentId(student.getStudentId(), score.getRuleScore());
						} else {
							//
							ScoreSum scoreSum2 = new ScoreSum();
							scoreSum2.setStudentId(student.getStudentId());
							scoreSum2.setSum(score.getRuleScore());
							scoreSum2.setRemain(score.getRuleScore());
							scoreSum2.setConsumption(0);
							scoreSumDao.addScoreSumByStudentId(scoreSum2);
						}
						// 给明细表加入数据
						ScoreGoodsDetail detail = new ScoreGoodsDetail();
						detail.setStudentId(student.getStudentId());
						detail.setDetailFlag(0);
						detail.setDetailName("签到获取积分");
						detail.setDetailTypeName("qd");
						detail.setDetailScore(score.getRuleScore());
						detail.setDetailCreateTime(new Date());
					 
						detailDao.addScoreGoodsDetail(detail);
							map.put("code", "0");
							map.put("msg", "签到成功");
						}else {
							map.put("code", "1");
							map.put("msg", "此机构积分暂时关闭");
						}
					} else {
						map.put("code", "1");
						map.put("msg", "此机构没有签到获取积分哟");
					}
				} else {
					map.put("code", "1");
					map.put("msg", "今日你孩子已经签到过了");
				}
			}
		}
		// 学生
		if (flag == 1) {
			// 查询今天是否签到
			int qd = dao.quertCount(uid, "qd");
			// 根据用户id查询出 所在校区
			Student student = studentDao.queryStudentById(uid);

			if (qd == 0) {// 没签到
				DynamicCondition dynamicCondition = dConditionDao
						.queryDynamicConditionBySchoolId(student.getSchoolId());
				ScoreDisable disable= disableDao.queryScoreDisable(student.getSchoolId());
				if (dynamicCondition != null && dynamicCondition.getLoginDynamicCondition() != null) {
					if (disable.getDisableFlag()==0) {
						
					
					ScoreGetScore score = new ScoreGetScore();
					score.setStudentId(uid);
					score.setRuleName("qd");
					score.setCreateTime(new Date());
					score.setRuleScore(dynamicCondition.getLoginDynamicCondition());
					dao.addScore(score);

					// 给积分总表累加
					ScoreSum scoreSum = scoreSumDao.queryScoreSumBystudentId(uid);

					if (scoreSum != null) {
						scoreSumDao.updateScoreSumByStudentId(uid, score.getRuleScore());
					} else {
						//
						ScoreSum scoreSum2 = new ScoreSum();
						scoreSum2.setStudentId(uid);
						scoreSum2.setSum(score.getRuleScore());
						scoreSum2.setRemain(score.getRuleScore());
						scoreSum2.setConsumption(0);
						scoreSumDao.addScoreSumByStudentId(scoreSum2);
					}
					// 给明细表加入数据
					ScoreGoodsDetail detail = new ScoreGoodsDetail();
					detail.setStudentId(uid);
					detail.setDetailFlag(0);
					detail.setDetailName("签到获取积分");
					detail.setDetailTypeName("qd");
					detail.setDetailScore(score.getRuleScore());
					detail.setDetailCreateTime(new Date());
					detailDao.addScoreGoodsDetail(detail);
					map.put("code", "0");
					map.put("msg", "签到成功");
					}else {
						map.put("code", "1");
						map.put("msg", "此机构没有签到获取积分哟");
					}

				} else {
					map.put("code", "1");
					map.put("msg", "此机构没有签到获取积分哟");
				}
			} else {
				map.put("code", "1");
				map.put("msg", "今日你已经签到过了");
			}

		}
		return map;
	}

	// 老师点评给学生加积分
	public Map<String, Object> addGoodsReview(Integer attendanceId, Integer affirm) {
		// 根据attendanceId查询学生id
		Attendance attendance = attendanceDao.queryAttendanceByAttendanceId(attendanceId);
		Integer studentId = attendance.getStudentId();// 要增加积分的学生id
		//打卡不为空
		ScoreGetScore dzCount = dao.quertCountDz(studentId, "dk", attendanceId);
		// 根据用户id查询出 所在校区
		Student student = studentDao.queryStudentById(studentId);
		ScoreDisable disable= disableDao.queryScoreDisable(student.getSchoolId());
		if (affirm != 0&&disable.getDisableFlag()==0) {//
		
			if (dzCount != null&&student!=null) {//
				// 增加积分
				// 获取学生所在的校区
				DynamicCondition dynamicCondition = dConditionDao.queryDynamicConditionBySchoolId(student.getSchoolId());
				if (dynamicCondition != null && dynamicCondition.getGoodDynamicCondition() != null&&dynamicCondition.getReviewDynamicCondition()!=null) {
					ScoreGetScore score = new ScoreGetScore();
					ScoreGetScore score1 = new ScoreGetScore();
					ScoreGetScore getScore= dao.quertCountDz(studentId,"tg",attendance.getAttendanceId());
					score.setStudentId(studentId);
					score.setAttendanceId(attendanceId);
					if (affirm == 1) {//通过作业
						score.setRuleScore(dynamicCondition.getReviewDynamicCondition());
						score.setRuleName("tg");
					}
					if (affirm == 2) {//优秀作业
						score.setRuleScore(dynamicCondition.getGoodDynamicCondition());
						score.setRuleName("yx");
						//查询有没有通过作业 没有则 通过作业 ，有的话 只评优
					
						if (getScore==null) {
							
						
						
						score1.setStudentId(studentId);
						score1.setAttendanceId(attendanceId);
						
						//也通过作业了
						score1.setRuleScore(dynamicCondition.getReviewDynamicCondition());
						score1.setRuleName("tg");
						score1.setCreateTime(new Date());
						dao.addScore(score1);
						}
					}
					
					score.setCreateTime(new Date());
					dao.addScore(score);
			 				
					
						// 给总积分表加积分
						ScoreSum scoreSum = scoreSumDao.queryScoreSumBystudentId(studentId);
						
						if (affirm==1) {
							if (scoreSum != null) {
								scoreSumDao.updateScoreSumByStudentId(studentId, score.getRuleScore());
							} else {
								//
								ScoreSum scoreSum2 = new ScoreSum();
								scoreSum2.setStudentId(studentId);
								scoreSum2.setSum(score.getRuleScore());
								scoreSum2.setRemain(score.getRuleScore());
								scoreSum2.setConsumption(0);
								scoreSumDao.addScoreSumByStudentId(scoreSum2);
							}
						}
						if (affirm==2) {
							//没有通过打卡
							if (getScore==null) {
								if (scoreSum != null) {
									scoreSumDao.updateScoreSumByStudentId(studentId, score.getRuleScore()+score1.getRuleScore());
								}else {
										
									ScoreSum scoreSum2 = new ScoreSum();
									scoreSum2.setStudentId(studentId);
									scoreSum2.setSum(score.getRuleScore()+score1.getRuleScore());
									scoreSum2.setRemain(score.getRuleScore()+score1.getRuleScore());
									scoreSum2.setConsumption(0);
									scoreSumDao.addScoreSumByStudentId(scoreSum2);
								}
									
							
							}else {
								//通过打卡了
								if (scoreSum != null) {
									scoreSumDao.updateScoreSumByStudentId(studentId, score.getRuleScore());
								}else {
										
									ScoreSum scoreSum2 = new ScoreSum();
									scoreSum2.setStudentId(studentId);
									scoreSum2.setSum(score.getRuleScore());
									scoreSum2.setRemain(score.getRuleScore());
									scoreSum2.setConsumption(0);
									scoreSumDao.addScoreSumByStudentId(scoreSum2);
								}
							}	
							}
 
					// 给明细表加入数据
					ScoreGoodsDetail detail = new ScoreGoodsDetail();
					ScoreGoodsDetail detail1 = new ScoreGoodsDetail();
					detail.setStudentId(studentId);
					detail.setDetailFlag(0);
					if (affirm == 1) {
						detail.setDetailName("打卡通过获取积分");
						detail.setDetailTypeName("tg");
					}
					if (affirm == 2) {
						detail.setDetailName("打卡评优获取积分");
						detail.setDetailTypeName("yx");
						
						if (getScore==null) {
						detail1.setStudentId(studentId);
						detail1.setDetailFlag(0);
						detail1.setDetailName("打卡通过获取积分");
						detail1.setDetailTypeName("tg");
						detail1.setDetailScore(dynamicCondition.getReviewDynamicCondition());
						detail1.setDetailAttendance(attendanceId);
						detail1.setDetailCreateTime(new Date());
						
						detailDao.addScoreGoodsDetail(detail1);
						}
					}
					detail.setDetailScore(score.getRuleScore());
					detail.setDetailAttendance(attendanceId);
					detail.setDetailCreateTime(new Date());
					detailDao.addScoreGoodsDetail(detail);
					
				} 
			} 
			 
		}
		// 取消评优或通过打卡
		if (affirm == 0&&disable.getDisableFlag()==0) {
			
//			int ress=dao
			//如果数据库里有评优或者通过评优的数据？？？
			
			
			ScoreGetScore getScore= dao.quertCountDz(studentId,"tg",attendance.getAttendanceId());
			ScoreGetScore getScore1= dao.quertCountDz(studentId,"yx",attendance.getAttendanceId());
			//有评优过 
			if (getScore1!=null) {
				//删除评优和通过打卡的 数据  
				dao.deleteScore(studentId, attendanceId, "tg");
				dao.deleteScore(studentId, attendanceId, "yx");
				//修改总计分
				Integer sco=getScore.getRuleScore()+getScore1.getRuleScore();
				scoreSumDao.cutXGScoreSumByStudentId(studentId, sco);
				//删除积分详情数据
				detailDao.deleteScoreDetail(studentId, attendanceId, "tg");
				detailDao.deleteScoreDetail(studentId, attendanceId, "yx");
				
			}else  if (getScore!=null&&getScore1==null) {
				//只通过打卡了  删除通过打卡的数据
				dao.deleteScore(studentId, attendanceId, "tg");
				
				//修改总计分
				scoreSumDao.cutXGScoreSumByStudentId(studentId, getScore.getRuleScore());
				//删除积分详情数据
				detailDao.deleteScoreDetail(studentId, attendanceId, "tg");
			} 
			
			
			
//			System.out.println(getScore);
	 
//			int res = dao.deleteScore(studentId, attendanceId, "tg");
//			// 查询出撤回的积分
//
//			if (res > 0) {
//
//				// 修改总积分
//				
//
//				// 修改积分明细
//				detailDao.deleteScoreDetail(studentId, attendanceId, "dp");
//
//			}
		}
		 
		return null;
	}

	// 给学生打分加积分
	public Map<String, Object> addGoodsReviewEvaluation(Integer attendanceId, Integer score1) {
//		// 根据attendanceId查询学生id
//		Attendance attendance = attendanceDao.queryAttendanceByAttendanceId(attendanceId);
//		Integer studentId = attendance.getStudentId();// 要增加积分的学生id
//
//		ScoreGetScore dzCount = dao.quertCountDz(studentId, "df", attendanceId);
//		// 根据用户id查询出 所在校区
//		Student student = studentDao.queryStudentById(studentId);
//		
//		ScoreDisable disable= disableDao.queryScoreDisable(student.getSchoolId());
//		if (disable.getDisableFlag()==0) {
//			
//		
//		if (score1 != null) {// 打分
//			if (dzCount == null&&student!=null) {//
//				// 增加积分
//				// 获取学生所在的校区
//				DynamicCondition dynamicCondition = dConditionDao
//						.queryDynamicConditionBySchoolId(student.getSchoolId());
//				if (dynamicCondition != null && dynamicCondition.getReviewDynamicCondition() != null) {
//					ScoreGetScore score = new ScoreGetScore();
//					score.setStudentId(studentId);
//
//					if (score1 == 0) {//A+
//						score.setRuleScore(dynamicCondition.getReviewDynamicCondition() + 3);
//					}
//					if (score1 == 1) {//A
//						score.setRuleScore(dynamicCondition.getReviewDynamicCondition() + 2);
//					}
//					if (score1 == 2) {//B
//						score.setRuleScore(dynamicCondition.getReviewDynamicCondition() + 1);
//					}
//					if (score1 == 3) {//C
//						score.setRuleScore(dynamicCondition.getReviewDynamicCondition());
//					}
//					score.setAttendanceId(attendanceId);
//					score.setRuleName("df");
//
//					score.setCreateTime(new Date());
//					dao.addScore(score);
//					// 给总积分表加积分
//					ScoreSum scoreSum = scoreSumDao.queryScoreSumBystudentId(studentId);
//					if (scoreSum != null) {
//						scoreSumDao.updateScoreSumByStudentId(studentId, score.getRuleScore());
//					} else {
//						//
//						ScoreSum scoreSum2 = new ScoreSum();
//						scoreSum2.setStudentId(studentId);
//						scoreSum2.setSum(score.getRuleScore());
//						scoreSum2.setRemain(score.getRuleScore());
//						scoreSum2.setConsumption(0);
//						scoreSumDao.addScoreSumByStudentId(scoreSum2);
//					}
//					// 给明细表加入数据
//					ScoreGoodsDetail detail = new ScoreGoodsDetail();
//					detail.setStudentId(studentId);
//					detail.setDetailFlag(0);
//					detail.setDetailName("老师通过打卡获取积分");
//					detail.setDetailTypeName("df");
//					detail.setDetailScore(score.getRuleScore());
//					detail.setDetailAttendance(attendanceId);
//					detail.setDetailCreateTime(new Date());
//					detailDao.addScoreGoodsDetail(detail);
//
//				} else {
//
//				}
//
//			} else {
//				Integer ruleScore = 0;
//				DynamicCondition dynamicCondition = dConditionDao
//						.queryDynamicConditionBySchoolId(student.getSchoolId());
//				if (score1 == 0) {
//					ruleScore = dynamicCondition.getReviewDynamicCondition() + 3;
//				}
//				if (score1 == 1) {
//					ruleScore = dynamicCondition.getReviewDynamicCondition() + 2;
//				}
//				if (score1 == 2) {
//					ruleScore = dynamicCondition.getReviewDynamicCondition() + 1;
//				}
//				if (score1 == 3) {
//					ruleScore = dynamicCondition.getReviewDynamicCondition();
//				}
//				// 修改打卡分数
//				int res = dao.updateScore(dzCount.getGetScoreId(), ruleScore);
//
//				if (res > 0) {
//					Integer count = dzCount.getRuleScore();
//					Integer ct = ruleScore;
//
//					Integer remain = count - ct;
//					// 修改总积分
//					scoreSumDao.cutXGScoreSumByStudentId(studentId, remain);/////
//
//					// 修改积分明细
//					detailDao.updateScoreDetail(studentId, attendanceId, "df", remain);
//
//				}
//			}
//		}
//		}else {
//			//没启用积分
//		}
////		}
		return null;
	}

	// 打卡加积分
	public Map<String, Object> addStudentUserIntegral( Integer attendanceId, String flag,Integer makeup) {
 
		
		// 根据attendanceId查询学生id
		Attendance attendance = attendanceDao.queryAttendanceByAttendanceId(attendanceId);
		Integer studentId = attendance.getStudentId();// 要增加积分的学生id
		ScoreGetScore dzCount = dao.quertCountDz(studentId, "dk", attendanceId);
		// 根据用户id查询出 所在校区
		Student student = studentDao.queryStudentById(studentId);
		ScoreDisable disable= disableDao.queryScoreDisable(student.getSchoolId());
		if (flag.equalsIgnoreCase("y")&&attendanceId!=null&&disable.getDisableFlag()==0) {// 打卡加积分
	 
			if (dzCount == null&&student!=null) {//
				// 增加积分
				// 获取学生所在的校区
				 
				DynamicCondition dynamicCondition = dConditionDao.queryDynamicConditionBySchoolId(student.getSchoolId());
				if (dynamicCondition != null && dynamicCondition.getClockDynamicCondition() != null) {
					ScoreGetScore score = new ScoreGetScore();
					score.setStudentId(studentId);
					score.setAttendanceId(attendanceId);
					if (makeup==1) {
						//补交打卡
						score.setRuleScore(dynamicCondition.getClockDynamicCondition()-1);
					}else {
						score.setRuleScore(dynamicCondition.getClockDynamicCondition());
					}
					score.setRuleName("dk");
					score.setCreateTime(new Date());
					dao.addScore(score);
					// 给总积分表加积分
					ScoreSum scoreSum = scoreSumDao.queryScoreSumBystudentId(studentId);

					if (scoreSum != null) {
						scoreSumDao.updateScoreSumByStudentId(studentId, score.getRuleScore());
					} else {
						//
						ScoreSum scoreSum2 = new ScoreSum();
						scoreSum2.setStudentId(studentId);
						scoreSum2.setSum(score.getRuleScore());
						scoreSum2.setRemain(score.getRuleScore());
						scoreSum2.setConsumption(0);
						scoreSumDao.addScoreSumByStudentId(scoreSum2);
					}
					// 给明细表加入数据
					ScoreGoodsDetail detail = new ScoreGoodsDetail();
					detail.setStudentId(studentId);
					detail.setDetailFlag(0);
					if (makeup==1) {
						detail.setDetailName("补交打卡获取积分");
					}else {
						detail.setDetailName("打卡获取积分");
					}
				
					detail.setDetailTypeName("dk");
					detail.setDetailScore(score.getRuleScore());
					detail.setDetailAttendance(attendanceId);
					detail.setDetailCreateTime(new Date());
					detailDao.addScoreGoodsDetail(detail);

				} else {
					
				}
			} else {
					
			}
		}
		// 取消打卡 
		if (flag.equalsIgnoreCase("n")&&attendanceId!=null&&disable.getDisableFlag()==0) {
			//判断该学生积分是否为0 
			
			// 删除给别人点赞学生的积分
			int res = dao.deleteScore(studentId, attendanceId, "dk");
			// 查询出撤回的积分

			if (res > 0) {
				// 修改总积分
				scoreSumDao.cutXGScoreSumByStudentId(studentId, dzCount.getRuleScore());

				// 修改积分明细
				detailDao.deleteScoreDetail(studentId, attendanceId, "dk");

			}
		}
		return null;
	}

	// 查询孩子今天是否已经签到
	@Override
	public Map<String, Object> querySignAddScore(Integer uid, Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (uid == null) {
			map.put("code", 0);
			map.put("msg", "uid不能为空");
		}
		if (flag == null) {
			map.put("code", 0);
			map.put("msg", "flag不能为空");
		}

		if (flag == 1) {
			// 学生
			Integer sfQd = dao.quertCount(uid, "qd");
			Boolean flBoolean = false;
			if (sfQd == 1) {
				flBoolean = true;
				map.put("code", 0);
				map.put("data", flBoolean);
			}
			if (sfQd == 0) {
				flBoolean = false;
				map.put("code", 0);
				map.put("data", flBoolean);
			}
		}
		if (flag == 3) {
			// 监护人
			// 查找监护人的孩子
			List<Student> students = stuDao.getGuardianStudent(uid);
			for (Student student : students) {
				int qd = dao.quertCount(student.getStudentId(), "qd");
				Boolean flBoolean = false;
				if (qd == 0) {
					// 没签到的孩子
					flBoolean = false;
					map.put("code", 0);
					map.put("data", flBoolean);
				}
				if (qd == 1) {
					flBoolean = true;
					map.put("code", 0);
					map.put("data", flBoolean);
				}

			}

		}
		return map;
	}
	//查询核销员
	@Override
	public Map<String, Object> queryRecipient(Integer teacherId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (teacherId == null) {
			map.put("code", 1);
			map.put("msg", "teacher不能为空");
		}else {
			ScoreRecipient recipient=recipientDao.queryRecipient(teacherId);
			if (recipient!=null) {
				map.put("code", 0);
				map.put("status", 0);
			}else {
				map.put("code", 0);
				map.put("status", 1);
			}	 
		}
		return map;
	}
	//增加积分配置和 是否启用 默认 积分配置都为0  ，禁用状态

	@Override
	public Map<String, Object> addDynamicCondition(Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schoolId == null) {
			map.put("code", 1);
			map.put("msg", "schoolId不能为空");
		}else {
			//增加积分配置
			DynamicCondition dynamicCondition=new DynamicCondition();
			dynamicCondition.setClockDynamicCondition(0);
			dynamicCondition.setLikeDynamicCondition(0);
			dynamicCondition.setLoginDynamicCondition(0);
			dynamicCondition.setReviewDynamicCondition(0);
			dynamicCondition.setShareDynamicCondition(0);
			dynamicCondition.setVideoDynamicCondition(0);
			dynamicCondition.setSchoolId(schoolId);
			
			dynamicConditionDao.addDynamicCondition(dynamicCondition);
			//增加状态
			ScoreDisable disable=new ScoreDisable();
			disable.setDisableFlag(1);
			disable.setDisableSchoolId(schoolId);
			disableDao.insertScoreDisable(disable);
			
			map.put("code", 0);
			map.put("msg","增加成功");
		}
		return map;
	}
	
	 

}
