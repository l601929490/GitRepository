package com.siwo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRecording implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer taskRecordingId;
	
	private String recording;
	
	private Integer recordingTime;
	
	private Integer taskId;
	
}
