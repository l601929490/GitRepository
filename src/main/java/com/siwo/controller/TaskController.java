package com.siwo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siwo.commons.TimedTaskUntil;
import com.siwo.model.Task;
import com.siwo.model.TaskComment;
import com.siwo.service.TaskService;

@RestController
public class TaskController {

	@Autowired
	private TaskService service;
	
	@PostMapping("/applet/addTask")
	public Map<String, Object>addTask(@Valid @RequestBody Task task,BindingResult result){
		task.setOkNum(0);
		task.setCreateTime(new Date());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sop = format.format(task.getTaskStopTime());
		if (task.getIsTiming() == 1) {
			return TimedTaskUntil.executeTask(task, service, result);
		}else {
			return service.addTask(task,result);
		}
	}
	
	@GetMapping("/applet/getTaskByTaskId")
	public Map<String, Object>getTaskByTaskId(Integer taskId,String phone,Integer idNum,Integer schoolId,Integer studentId,Integer classId){
		return service.getTaskByTaskId(taskId,phone,idNum,schoolId,studentId,classId);
	}
	
	@GetMapping("/applet/getTaskByClassId")
	public Map<String, Object> queryTaskByClassId(Integer classId,Integer studentId){	
		return service.queryTaskByClassId(classId,studentId);
	}
	
	@PostMapping("/applet/updateTask")
	public Map<String, Object> updateTask(@RequestBody Task task) {
		return service.updateTask(task);
	}
	
	@PostMapping("/applet/addTaskComment")
	public Map<String, Object> addTaskComment(TaskComment taskComment) {
		taskComment.setCommentTime(new Date());
		return service.addTaskComment(taskComment);
	}
	
	@PostMapping("/applet/updateTaskComment")
	public Map<String, Object> updateTaskComment(TaskComment taskComment) {
		return service.updateTaskComment(taskComment);
	}
	
	@GetMapping("/applet/queryTaskCommentById")
	public Map<String, Object> queryTaskCommentById(Integer taskId) {
		return service.queryTaskCommentById(taskId);
	}
	
	@GetMapping("/limitTask")
	public Map<String, Object> limitTask(Integer classId,Integer schoolId,Integer page,Integer limit) {
		return service.limitTask(classId, schoolId, page, limit);
	}
	
	@GetMapping("/deleteManyTask")
	public Map<String, Object> deleteManyTask(String taskIds,Integer classId) {
		String[] ids = taskIds.split(",");
		return service.deleteManyTask(ids, classId);
	}
	
	@PostMapping("/applet/deleteTaskByTaskId")
	public Map<String, Object> deleteTaskByTaskId(Integer taskId, Integer classId) {
		return service.deleteTaskByTaskId(taskId, classId);
	}
}
