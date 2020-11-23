package com.siwo.commons;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.validation.BindingResult;

import com.siwo.model.Task;
import com.siwo.service.TaskService;

public class TimedTaskUntil {

	private static Integer threadName = 0;
	
	//	老师发布作业定时
	public static Map<String, Object> executeTask(Task task,TaskService service,BindingResult result) {
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		try {
			
			new Timer("timer - " + threadName).schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("开始添加");
					Map<String, Object> map = service.addTask(task, result);
					System.out.println(map);
				}
			}, task.getTimingTime());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "服务器异常");
			return map;
		}
		String time = format.format(task.getTimingTime());
		map.put("code", 0);
		map.put("msg", "发布成功，作业将在"+time+"发布");
		threadName++;
		return map;
	}
	
}
