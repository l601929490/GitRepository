package com.siwo.commons;

import java.util.Date;

import com.siwo.model.MyClass;

public class MyClassUtil {

	public static Integer queryClassStatus(MyClass myClass) {
		
		//	开班时间
		Date start= myClass.getPromotionTime();
		//	结束时间
		Date stop = myClass.getGraduationTime();
		//	当前时间
		Date nowTime = new Date();
		
		if (nowTime.getTime() < start.getTime()) {
			//	未开班
			return 1;
		}else if (nowTime.getTime() > stop.getTime()) {
			//	已结业
			return 3;
		}else if(nowTime.getTime() > start.getTime() && nowTime.getTime() <stop.getTime()){
			//	已开班
			return 2;
		}
		return 0;
	}
}
