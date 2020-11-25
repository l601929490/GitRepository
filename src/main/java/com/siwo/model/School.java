package com.siwo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class School implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer schoolId;
	
	private String schoolName;
	
	private String schoolPhone;
	
	private String schoolPrincipal;
	
	private String schoolPrincipalPhone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date schoolCreateTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date schoolEndTime;
	
	private String schoolAddress;
	
	private String schoolLongitude;
	
	private String schoolLatitude;
	
	private String schoolType;
	
	private String schoolSynopsis;
	
	private Integer companyId;
	
	private Integer adminId;
	
	private String creator;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date creationTime;
	
	private String editor;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date editSession;
	
	private List<SchoolSlideshow> slideshow;
	
	private List<SchoolHonor> honorImg;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		School other = (School) obj;
		if (schoolId == null) {
			if (other.schoolId != null)
				return false;
		} else if (!schoolId.equals(other.schoolId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schoolId == null) ? 0 : schoolId.hashCode());
		return result;
	}
	
	
}
