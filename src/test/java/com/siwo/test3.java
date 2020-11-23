package com.siwo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.siwo.dao.CompanyDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.SchoolDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.Company;
import com.siwo.model.Guardian;
import com.siwo.model.School;
import com.siwo.model.Teacher;

@SpringBootTest(classes = EduCloudApplication.class)
class test3 {

	@Autowired
	private TeacherDao dao;
	
	@Autowired
	private GuardianDao dao1;
	
	@Autowired
	private MyClassDao classdao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Test
	void test() {
		School school = new School();
		
		school.setSchoolAddress("123");
		
		schoolDao.addSchool(school);
	}


}
