package com.siwo.commons;

import java.util.List;

import com.siwo.model.MyClass;
import com.siwo.model.Student;

public class StudentUtil {

	public static Integer queryStudentState(Student student) {
		
		List<MyClass> myClasses = student.getMyClasses();
		
		if (myClasses == null || myClasses.size() == 0) {
			student.setState(1);
			//	待分班
			return 1;
		}

		for (MyClass myClass : myClasses) {
			Integer state = MyClassUtil.queryClassStatus(myClass);
			if (state != 3) {
				student.setState(2);
				//	已分班
				return 2;
			}
		}
		
		//	已结业 
		student.setState(3);
		return 3;
	}
	
}
