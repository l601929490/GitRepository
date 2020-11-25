package com.siwo.service;

import java.util.Map;

import org.springframework.validation.BindingResult;

import com.siwo.model.Task;
import com.siwo.model.TaskComment;

public interface TaskService {

	public Map<String, Object> addTask(Task task,BindingResult result);
	public Map<String, Object> getTaskByTaskId(Integer taskId,String phone,Integer idNum,Integer schoolId,Integer studentId,Integer classId);
	public Map<String, Object> queryTaskByClassId(Integer classId,Integer studentId);
	public Map<String, Object> updateTask(Task task);
	public Map<String, Object> addTaskComment(TaskComment taskComment);
	public Map<String, Object> updateTaskComment(TaskComment taskComment);
	public Map<String, Object> queryTaskCommentById(Integer taskId);
	public Map<String, Object> limitTask(Integer classId,Integer schoolId,Integer page,Integer limit);
	public Map<String, Object> deleteManyTask(String[] ids,Integer classId);
	public Map<String, Object> deleteTaskByTaskId(Integer taskId,Integer classId);
}
