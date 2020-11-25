package com.siwo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import com.siwo.dao.ApplyDao;
import com.siwo.dao.ApplyMessageDao;
import com.siwo.dao.AttendanceDao;
import com.siwo.dao.CompanyDao;
import com.siwo.dao.ConsultDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.ParamDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TaskDao;
import com.siwo.dao.TeacherDao;
import com.siwo.dao.ClientDao;
import com.siwo.dao.UserIntegralDao;
import com.siwo.model.Guardian;
import com.siwo.model.Student;
import com.siwo.model.Task;
import com.siwo.service.StudentService;

@SpringBootTest(classes = EduCloudApplication.class)
@RunWith(SpringRunner.class)
class test {

	@Autowired
	private TaskDao taskDao;
	
	@Test
	void contextLoads() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = "2020-09-01 10:59:00";
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Timer("timer - " ).schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("执行任务");
			}
		}, date);
	}
	
	public Integer addTask(Task task) {
		taskDao.addTask(task);
		return 0;
	}

}
