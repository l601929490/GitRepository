package com.siwo.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siwo.commons.StudentUtil;
import com.siwo.dao.ClientDao;
import com.siwo.dao.GuardianDao;
import com.siwo.dao.MyClassDao;
import com.siwo.dao.StudentDao;
import com.siwo.dao.TeacherDao;
import com.siwo.model.Client;
import com.siwo.model.Guardian;
import com.siwo.model.MyClass;
import com.siwo.model.Student;
import com.siwo.model.Teacher;
import com.siwo.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientDao dao;
	
	@Autowired
	private StudentDao stuDao;
	
	@Autowired
	private TeacherDao teaDao;
	
	@Autowired
	private GuardianDao guarDao;
	
	@Autowired
	private MyClassDao classDao;
	
	@Override
	public Map<String, Object> limitClientsAccomplish( Integer companyId,Integer page,Integer limit,Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (companyId == null) {
			map.put("code", 1);
			map.put("msg", "companyId不能为空");
			return map;
		}
		
		PageHelper.startPage(page, limit);
		List<Client> clients = dao.limitClientsAccomplish(companyId);
		PageInfo<Client> info = new PageInfo<Client>(clients);
		
		for (Client client : clients) {
			affirmId(client, schoolId);
		}
		
		if (clients !=null && clients.size()>0) {
			map.put("code", 0);
			map.put("data", clients);
			map.put("pageCount", info.getTotal());
		}
		return map;
	}

	@Override
	public Map<String, Object> limitClientsAccomplishByClassId(Integer companyId,Integer schoolId,Integer classId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (companyId == null) {
			map.put("code", 1);
			map.put("msg", "companyId不能为空");
			return map;
		}
		
		List<Client> clients = dao.limitClientsAccomplish(companyId);
		List<Client> clients2 = new ArrayList<Client>();
		for (Client client : clients) {
			Integer iden = affirmId(client, schoolId);
			List<MyClass> myClasses = new ArrayList<MyClass>();
			if (iden == 1) {
				myClasses.addAll(client.getStudent().get(0).getMyClasses()); 
			}
			if (iden == 3) {
				List<Student> students = client.getGuardian().getStudents();
				for (Student student : students) {
					myClasses.addAll(student.getMyClasses());
				}
			}
			for (MyClass myClass : myClasses) {
				if (myClass.getClassId() == classId) {
					clients2.add(client);
				}
			}
		}
		
		if (clients !=null && clients.size()>0) {
			map.put("code", 0);
			map.put("data", clients2);
		}
		return map;
	}
	
	@Override
	public Map<String, Object> limitClientsFail(Integer companyId,Integer page,Integer limit,Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (companyId == null) {
			map.put("code", 1);
			map.put("msg", "companyId不能为空");
			return map;
		}
		try {
			Integer start = (page - 1)*limit;
			List<Client> clients = dao.limitClientsFail(companyId,start,limit);
			
			Integer count = dao.getClientCountFail(companyId);
			map.put("code", 0);
			map.put("data", clients);
			map.put("pageCount", count);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return map;
	}

	@Override
	public Map<String, Object> limitClients(Integer companyId,Integer page,Integer limit,Integer schoolId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (companyId == null) {
			map.put("code", 1);
			map.put("msg", "companyId不能为空");
			return map;
		}
		
		Integer start = (page - 1)*limit;
		List<Client> clients = dao.limitClients(companyId,start,limit);
		for(int i = 0; i < clients.size(); i++) {
			Integer id = affirmId(clients.get(i), schoolId);
			if (id == 2) {
				clients.remove(i);
				i--;
			}
		}
		
		Integer count = dao.getClientCount(companyId);
		if (clients !=null && clients.size()>0) {
			map.put("code", 0);
			map.put("data", clients);
			map.put("pageCount", count);
		}
		return map;
	}

	public Integer affirmId(Client client,Integer schoolId) {
		List<Integer> list = dao.queryUserIdentity(client.getOpenId());
		client.setIdentitys(list);
		Student student = stuDao.getStudentByPhoneAndSchoolId(client.getUserPhone(), schoolId);
		if (student != null) {
			List<MyClass> myClasses = classDao.queryMyClassByStudentId(student.getStudentId(),schoolId);
			student.setMyClasses(myClasses);
			Integer state = StudentUtil.queryStudentState(student);
			student.setState(state);
			List<Student> list2 = new ArrayList<Student>();
			list2.add(student);
			client.setStudent(list2);
			return 1;
		}
		
		Guardian guardian = guarDao.queryGuardianByPhone(client.getUserPhone(), schoolId);
		if (guardian != null) {
			List<Student> students = guarDao.queryStudentByGuardianId(guardian.getGuardianId());
			guardian.setStudents(students);
			for (Student student2 : students) {
				List<MyClass> myClasses = classDao.queryMyClassByStudentId(student2.getStudentId(),schoolId);
				student2.setMyClasses(myClasses);
			}
			client.setGuardian(guardian);
			return 3;
		}
		
		Teacher teachers = teaDao.queryTeacherByPhone(client.getUserPhone(), schoolId);
		if (teachers != null) {
			client.setTeacher(teachers);
			return 2;
		}
		return 4;
	}
	
}
