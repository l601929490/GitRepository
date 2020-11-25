package com.siwo.commons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.TaskDao;
import com.siwo.model.Task;
import com.siwo.model.TaskRecording;

@Service
public class TaskUntil {

	@Autowired
	private TaskDao dao;

	// 添加/修改任务
	public Integer addTask(Task task, Integer state) {
		try {
			List<Integer> myClassIds = task.getClassIds();
			// 图
			List<String> imgs = task.getTaskImg();
			// 视频
			List<String> videos = task.getTaskVideo();
			// 录音
			List<TaskRecording> recordings = task.getRecording();

			// 添加任务
			if (state == 1) {
				dao.addTask(task);
			}

			for (String img : imgs) {
				dao.addTaskImg(task.getTaskId(), img);
			}

			for (String video : videos) {
				dao.addTaskVideo(task.getTaskId(), video);
			}

			for (TaskRecording recording : recordings) {
				recording.setTaskId(task.getTaskId());
				dao.addTaskRecording(recording);
			}

			if (state == 1) {
				for (Integer id : myClassIds) {
					// 添加班级
					dao.addTaskClass(task.getTaskId(), id, task.getOkNum());
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	// 查询作业文件
	public void queryTaskFile(Task task) {
		Integer taskId = task.getTaskId();
		List<String> imgs = dao.queryTaskImgByTaskId(taskId);
		List<String> videos = dao.queryTaskVideoByTaskId(taskId);
		List<TaskRecording> recordings = dao.queryTaskRecordingByTaskId(taskId);
		task.setTaskImg(imgs);
		task.setTaskVideo(videos);
		task.setRecording(recordings);
	}

	public Integer deleteTask(Integer id, Integer classId) {
		try {
			dao.deleteClassTask(classId, id);
			Integer row = dao.queryClassTaskCount(id);
			if (row == 0) {
				deleteTaskFile(id);
				dao.deleteTask(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	public void deleteTaskFile(Integer id) {
		try {
			dao.deleteTaskImg(id);
			dao.deleteTaskRecording(id);
			dao.deleteTaskVideo(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
