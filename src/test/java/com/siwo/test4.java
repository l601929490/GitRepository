package com.siwo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class test4 {

	public static void main(String[] args) throws IOException, ParseException {
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String str = "2020-11-19";
        Date date = dateFormatter.parse(str);
        dateFormatter.applyPattern("D");//
        System.out.println("一年中的第几天：" + dateFormatter.format(date));

        dateFormatter.applyPattern("d");
        System.out.println("一个月中的第几天：" + dateFormatter.format(date));

        dateFormatter.applyPattern("w");
        System.out.println("一年中的第几周：" + dateFormatter.format(date));

        dateFormatter.applyPattern("W");
        System.out.println("一个月中的第几周：" + dateFormatter.format(date));

        dateFormatter.applyPattern("E");
        System.out.println("一个星期中的天数：" + dateFormatter.format(date));

	}
}
