package com.siwo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Course;

@Mapper
public interface CourseDao {

	@Insert("insert into course (course_content,course_img,brief_id) values "
			+ "(#{courseContent},#{courseImg},#{briefId})")
	public Integer addCourse(Course course);
	
	@Select("select * from course where brief_id = #{briefId}")
	public Course queryCourseById(Integer briefId);
}
