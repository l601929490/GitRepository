package com.siwo.commons;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwo.dao.AttendanceDao;
import com.siwo.model.Attendance;
import com.siwo.model.AttendanceRecording;

@Service
public class AttendanceUntil {

	@Autowired
	private AttendanceDao dao;

	// 添加/更新 作业
	public Integer addAttendanceFile(Attendance attendance, Integer state) {

		try {

			// 添加作业
			if (state == 0) {
				dao.addAttendance(attendance);
			}
			// 作业id
			Integer attendanceId = attendance.getAttendanceId();
			// 添加图片
			List<String> imgs = attendance.getAttendanceImg();
			for (String img : imgs) {
				dao.addAttendanceImg(attendanceId, img);
			}
			// 添加视频
			List<String> videos = attendance.getAttendanceVideo();
			for (String video : videos) {
				dao.addAttendanceVideo(attendanceId, video);
			}
			// 添加录音
			List<AttendanceRecording> recordings = attendance.getRecordings();
			for (AttendanceRecording recording : recordings) {
				recording.setAttendanceId(attendanceId);
				dao.addAttendanceRecording(recording);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	// 查询作业文件
	public void queryAttendanceFile(Attendance attendance) {
		Integer attendanceId = attendance.getAttendanceId();
		List<String> imgs = dao.queryAttendanceImgByTaskId(attendanceId);
		List<String> videos = dao.queryAttendanceVideoByTaskId(attendanceId);
		List<AttendanceRecording> recordings = dao.queryAttendanceRecordingByTaskId(attendanceId);
		attendance.setAttendanceImg(imgs);
		attendance.setAttendanceVideo(videos);
		attendance.setRecordings(recordings);
	}

	// 根据可见性以及身份返回值
	public List<Attendance> queryAttendancesByVisible(Integer visible, Integer isAffirm, Integer taskId,
			Integer idNum,Integer classId) {

		List<Attendance> attendances = new ArrayList<Attendance>();

		// 学生互相可见
		if (visible == 0 || idNum == 2) {
			// 所有学生完成的所有作业
			attendances = dao.queryAttendanceByTaskId(taskId,classId);
		} else if (visible == 1) {
			if (isAffirm == 2) {
				// 优秀学生互相可见
				attendances = dao.queryGoodAttendanceByTaskId(taskId, isAffirm,classId);
			}
		}
		return attendances;
	}
	
	//	删除作业及其文件
	public Integer deleteAttendanceAndFile(Integer attendanceId) {
		try {
			dao.deleteAttendanceById(attendanceId);
			dao.deleteAttendanceImg(attendanceId);
			dao.deleteAttendanceRecording(attendanceId);
			dao.deleteAttendanceVideo(attendanceId);
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

}
