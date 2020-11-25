package com.siwo;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class test5 {

	public static void main(String[] args) {
		Calendar begin = new GregorianCalendar();
		Calendar end = new GregorianCalendar();
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] weeks = dfs.getWeekdays();
		
		begin.set(2020, 4, 18);
		end.set(2020, 8, 15);
		int count = 1;
		while (begin.before(end)) {
//			System.out.println("第" + count + "周       日期：" + new Date(start.getTimeInMillis()) + "       " + weeks[start.get(Calendar.DAY_OF_WEEK)]);

			if (begin.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				System.out.println("第" + count + "周");
				count++;
			}
			begin.add(Calendar.DAY_OF_YEAR, 1);
		}
		
	}
}
