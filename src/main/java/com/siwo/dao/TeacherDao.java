package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Teacher;

@Mapper
public interface TeacherDao {

	/**
	 * 添加老师
	 * @param teacher
	 * @return
	 */
	@Insert("insert into teacher (teacher_name,teacher_age,teacher_sex,teacher_phone,school_id,teacher_img,teacher_intro,teacher_creator,teacher_creation_time,teacher_college,teacher_work_year,status) "
			+ "values (#{teacherName},#{teacherAge},#{teacherSex},#{teacherPhone},#{schoolId},#{teacherImg},#{teacherIntro},#{teacherCreator},#{teacherCreationTime},#{teacherCollege},#{teacherWorkYear},#{status})")
	@Options(useGeneratedKeys = true,keyColumn = "teacher_id",keyProperty = "teacherId")
	public Integer addTeacher(Teacher teacher);
	
	/**
	 * 绑定老师班级
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	@Insert("insert into class_teacher (class_id,teacher_id) values (#{classId},#{teacherId})")
	public Integer addTeacherClass(Integer classId,Integer teacherId);
	
	/**
	 * 	解除老师绑定班级
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	@Delete("delete from class_teacher where class_id =#{classId} and teacher_id = #{teacherId}")
	public Integer deleteTeacherAndClass(Integer classId,Integer teacherId);
	
	/**
	 * 	修改信息
	 */
	public Integer updateTeacher(Teacher teacher);
	
	/**
	 * 根据手机号查询
	 * @param phone
	 * @return
	 */
	@Select("SELECT * from teacher t LEFT join class_teacher ct on t.teacher_id = ct.teacher_id LEFT JOIN class c on ct.class_id = c.class_id WHERE t.teacher_phone = #{phone} and t.school_id = #{schoolId}")
	@ResultMap("tea_1")
	public Teacher queryTeacherByPhone(String phone,Integer schoolId);
	
	@Select("SELECT * from teacher WHERE teacher_phone = #{phone}")
	public List<Teacher> queryTeachersByPhone(String phone);
	
	@Select("SELECT * from teacher WHERE teacher_phone = #{phone} and school_id = #{schoolId}")
	public Teacher getTeacherByPhoneAndSchoolId(String phone,Integer schoolId);
	
	/**
	 * 根据id查
	 * @param teacherId
	 * @return
	 */
	@Select("select * from teacher where teacher_id = #{teacherId}")
	public Teacher queryTeacherById(Integer teacherId);
	
	/**
	 * 获取所有老师
	 * @return
	 */
	@Select("select * from teacher where school_id = #{schoolId}")
	public List<Teacher> getAllTeacher(Integer schoolId);
	
	/**
	 * 按照老师Id删除老师
	 * @param teacherId
	 * @return
	 */
	@Delete("delete from teacher where teacher_id = #{teacherId}")
	public Integer deleteTeacher(Integer teacherId);
	
	@Delete("delete from class_teacher where teacher_id = #{teacherId}")
	public Integer deleteTeacherClass(Integer teacherId);
	/**
	 * 批量删除
	 */
	public Integer deleteManyTeacher(String[] teacherIds);
	
	public Integer deleteManyTeacherClass(String[] teacherIds);
	
	/**
	 * 分页查询
	 */
	@Select("select * from teacher t left join admin a on t.teacher_phone = a.admin_account where school_id = #{schoolId} and a.show_school_id = #{schoolId} order by status asc")
	public List<Teacher> limitTeacher(Integer schoolId);
	
	/**
	 * 查询老师总记录
	 * @return
	 */
	@Select("select count(*) from teacher where school_id = #{schoolId}")
	public Integer getTeacherCount(Integer schoolId);
	
	@Select("select * from teacher where teacher_show = 1 and school_id = #{schoolId}")
	public List<Teacher> queryShowTeacherBySchoolId(Integer schoolId);
	
	/**
	 * 	修改是否展示在小程序
	 * @param teacherId
	 * @param teacherShow
	 * @return
	 */
	@Update("update teacher set teacher_show = #{teacherShow} where teacher_id = #{teacherId}")
	public Integer updateTeacherShowByTeacherId(Integer teacherId,String teacherShow);
	
	@Select("select * from teacher where school_id = #{schoolId} and teacher_show = #{teacherShow}")
	public List<Teacher> queryShowTeacher(Integer schoolId,String teacherShow);
	
	/**
	 * 	查找老师是否在此班级
	 * @param classId
	 * @param teacherId
	 * @return
	 */
	@Select("select teacher_id from class_teacher where class_id = #{classId} and teacher_id = #{teacherId}")
	public Integer queryTeacherByClassIdAndTeacherId(Integer classId,Integer teacherId);
	
	/**
	 * 	查找班级所有老师
	 * @param classId
	 * @return
	 */
	@Select("select * from teacher t left join class_teacher ct on ct.teacher_id =  t.teacher_id where ct.class_id = #{classId}")
	public List<Teacher> queryTeacherByClassId(Integer classId);
	
	/**
	 * 	删除这个班所有的老师信息
	 * @param classId
	 * @return
	 */
	@Delete("delete from class_teacher where class_id = #{classId}")
	public Integer deleteClassTeacher(Integer classId);
	
	/**
	 * 	查询老师有没有绑定班级
	 * @param teacherId
	 * @return
	 */
	@Select("select class_id from class_teacher where teacher_id = #{teacherId}")
	public List<Integer> queryClassByTeacherId(Integer teacherId);
	
	/**
	 * 	按老师名字模糊查询
	 * @param teacherName
	 * @param schoolId
	 * @return
	 */
	@Select("select * from teacher where school_id = #{schoolId} and teacher_name like #{teacherName}")
	public List<Teacher> queryTeacherByName(String teacherName,Integer schoolId);
	
	/**
	 * 	修改老师状态
	 * @param teacherId
	 * @param status
	 * @return
	 */
	@Update("update teacher set status = #{status} where teacher_id = #{teacherId}")
	public Integer updateTeacherStatus(Integer teacherId,String status);
	
}
