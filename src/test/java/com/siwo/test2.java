package com.siwo;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.util.DigestUtils;
import org.xml.sax.InputSource;

import com.siwo.commons.WXXmlToMapUtil;

import net.bytebuddy.asm.Advice.This;

public class test2 {

	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//		System.out.println(format.parse("2020-07-30 07:58:39"));
//		Date date =new Date(1596643200000l);
//		String time = format.format(date);
//		String test = format.format(1593360000000l);
//		System.out.println(test);

//		System.out.println(time);

//		Long nowTime = System.currentTimeMillis();
//		Long tdtc = nowTime - date2.getTime();
//		Long hours =  tdtc / (1000 * 60 * 60);
//		System.out.println(hours);

//		List<Integer> list = new ArrayList<Integer>();
//		list.add(1);
//		System.out.println(list.size());

//		System.out.println(new Date(s));

//		String xxString = "ee9f4421b001820a0fbe874d42eb2778.mp4";
//		
//		String[] cc = xxString.split("\\.");
//		for (String string : cc) {
//			System.out.println("aa");
//			System.out.println(string);
//		}
//		
//		Integer x = 2;
//		Integer m = 1;
//		System.out.println(x+m);

//	for(int i = 0;i<6;i++) {
//		System.out.println(i);
//	}

//		System.out.println("*&^%");

//		List<Integer>list = new ArrayList<Integer>();
//		for (Integer integer : list) {
//			System.out.println("aaa");
//		}

		// MD5加密
		String md5 = DigestUtils.md5DigestAsHex("haha".getBytes());
		System.out.println(md5);
		String md52 = DigestUtils.md5DigestAsHex("haha".getBytes());
		System.out.println(md52);
//		 
//		 Date date = new Date(1555555555555l);
//		 System.out.println(date.compareTo(new Date()));
//		 
//		 List<Integer> tIntegers = new ArrayList<Integer>();
//		 tIntegers.add(0);
//		 tIntegers.add(2);
//		 System.out.println(tIntegers.size());

		// 获取昨天日期
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		
//		Calendar calendar = new GregorianCalendar();
//
//		calendar.setTime(new Date());
//
//		calendar.add(Calendar.DATE,-1);
//
//		String date2= format.format(calendar.getTime());
//
//		System.out.println(date2);

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xml_result><read_word lan=\"en\" type=\"study\" version=\"7.0.0.1020\"><rec_paper><read_word accuracy_score=\"2.078067\" beg_pos=\"0\" content=\"test\" end_pos=\"3688\" except_info=\"28676\" is_rejected=\"true\" reject_type=\"128\" standard_score=\"0.000000\" total_score=\"2.078067\"><sentence beg_pos=\"0\" content=\"test\" end_pos=\"3688\" index=\"0\"><word beg_pos=\"3139\" content=\"test\" dp_message=\"0\" end_pos=\"3280\" global_index=\"0\" index=\"0\" no_plo_word=\"0\" pitch=\"  803.38  803.38  798.45  802.94  812.89  814.84  823.10  835.35  848.71  853.06  845.69  829.95  821.28  818.08  830.44  835.14  832.22  822.18  818.05  821.37  824.68  828.00  831.32  834.64  837.96  841.28  844.60  847.91  859.52  868.51  878.57  878.68  876.35  871.37  866.46  865.03  863.59  862.16  860.72  859.29  857.85  856.42  854.98  853.55  852.11  850.68  849.24  847.81  846.37  844.94  843.50  842.07  840.63  839.20  848.79  863.12  878.36  880.37  872.69  861.31  851.38  844.41  841.38  840.26\" pitch_beg=\"3178\" pitch_end=\"3242\" property=\"0\" total_score=\"2.078067\"><syll beg_pos=\"3139\" content=\"t eh s t\" dur_value=\"1.000000\" end_pos=\"3280\" magnitude_value=\"1.000000\" pitch_value=\"1.000000\" serr_msg=\"1\" syll_accent=\"0\" syll_score=\"2.078067\"><phone beg_pos=\"3139\" content=\"t\" dp_message=\"0\" end_pos=\"3202\" gwpp=\"-3.545684\"></phone><phone beg_pos=\"3202\" content=\"eh\" dp_message=\"0\" end_pos=\"3205\" gwpp=\"-2.362274\"></phone><phone beg_pos=\"3205\" content=\"s\" dp_message=\"0\" end_pos=\"3215\" gwpp=\"-1.703951\"></phone><phone beg_pos=\"3215\" content=\"t\" dp_message=\"0\" end_pos=\"3280\" gwpp=\"-0.364955\"></phone></syll></word></sentence></read_word></rec_paper></read_word></xml_result>";
		readStringXml(xml);
	}

	public static void readStringXml(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Map<String, Object> map = new HashMap<String, Object>();
			getNodes(doc.getRootElement(),map);
			String x = (String) map.get("total_score");
			System.out.println(x);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public static Map<String, Object> getNodes(Element node,Map<String, Object> map){
		System.out.println("--------------------");
		//当前节点的名称、文本内容和属性
		System.out.println("当前节点名称："+node.getName());//当前节点名称
		System.out.println("当前节点的内容："+node.getTextTrim());//当前节点名称
		List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list
		for(Attribute attr:listAttr){//遍历当前节点的所有属性
			String name=attr.getName();//属性名称
			String value=attr.getValue();//属性的值
			System.out.println("属性名称："+name+"属性值："+value);
			if (name.equals("total_score")) {
				map.put("total_score", value);
			}
		}
		
		//递归遍历当前节点所有的子节点
		List<Element> listElement=node.elements();//所有一级子节点的list
		for(Element e:listElement){//遍历所有一级子节点
			getNodes(e,map);//递归
		}
		return map;
	}
}
