package com.siwo.commons;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.siwo.model.XqClassDay;

public class XqUntil {

	public static List<XqClassDay> sortDate(List<XqClassDay> xqClassDays,boolean is) {
		
		Collections.sort(xqClassDays, new Comparator<XqClassDay>() {

			@Override
			public int compare(XqClassDay o1, XqClassDay o2) {
				if (is) {
					return (int) (o1.getClassDate().getTime() - o2.getClassDate().getTime());
				}else {
					return (int) (o2.getClassDate().getTime() - o1.getClassDate().getTime());
				}
			}
		});
		return xqClassDays;
	}
}
