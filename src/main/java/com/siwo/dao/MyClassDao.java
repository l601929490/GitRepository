package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.MyClass;

@Mapper
public interface MyClassDao {

	@Insert("insert into class (class_name,class_category,class_level,class_hour,promotion_time,Graduation_time,class_img,school_id,class_creator,class_create_time) "
			+ "values (#{className},#{classCategory},#{classLevel},#{classHour},#{promotionTime},#{graduationTime},#{classImg},#{schoolId},#{classCreator},#{classCreateTime})")
	public Integer addClass(MyClass myClass);
	
	public Integer updateClass(MyClass myClass);
	
	@ResultMap("class_1")
	@Select("select * from class c left join class_teacher ct on c.class_id = ct.class_id LEFT JOIN teacher t on ct.teacher_id = t.teacher_id where c.class_id = #{id}")
	public MyClass queryById(Integer id);
	
	@Select("select * from class c left join class_student cs on c.class_id = cs.student_class_id left join student s on cs.student_id = s.student_id where s.student_id = #{studentId} and c.school_id = #{schoolId}")
	@ResultMap("class_1")
	public List<MyClass> queryClassByStudentId(Integer studentId,Integer schoolId);
	
	/**
	 * 	查询学生加入的所有班级
	 * @param studentId
	 * @return
	 */
	@Select("select * from class c left join class_student cs on c.class_id = cs.student_class_id where cs.student_id = #{studentId} and school_id = #{schoolId}")
	public List<MyClass> queryMyClassByStudentId(Integer studentId,Integer schoolId);
	
	@Select("select class_name from class")
	public List<String> queryAllClassName();
	
	/**
	 * 	查询老师带的所有班级
	 * @param teacherId
	 * @return
	 */
	@Select("select * from class where class_id in (select class_id from class_teacher where teacher_id = #{teacherId})")
	public List<MyClass> queryByTeacherId(Integer teacherId);
	
	/**
	 * 查询班级已打卡人数
	 */	
	@Select("select count(*) from attendance where task_id = #{taskId} and is_attendance = 1")
	public Integer queryAttendanceCount(Integer taskId);
	
	/**
	 * 	根据班级ID查班级
	 */	
	@Select("select * from class where class_id = #{classId}")
	public MyClass queryClassById(Integer classId);
	
	/**
	 * 	查询一个校区的所有班级信息
	 */
	@Select("select * from class where school_id = #{schoolId}")
	public List<MyClass> getAllMyClass(Integer schoolId);
	
	/**
	 * 	按照班级名称查找班级id
	 * @param className
	 * @return
	 */
	@Select("select class_id from class where class_name = #{className}")
	public Integer queryClassIdByName(String className);
	
	/**
	 * 单个删除
	 */
	@Delete("delete from class where class_id = #{classId}")
	public Integer deleteclassId(Integer classId);
	
	/**
	 * 批量删除
	 */
	public Integer deleteManyClass(String[] classIds);
	
	/**
	 *	分页查询
	 * @param page
	 * @param limit
	 * @param schoolId
	 * @return
	 */
	@Select("SELECT * FROM class WHERE school_id = #{schoolId} LIMIT #{page},#{limit}")
	public List<MyClass> limitClass(Integer page, Integer limit,Integer schoolId);
	
	/**
	 * 	获取总记录数
	 * @param schoolId
	 * @return
	 */
	@Select("select count(*) from class where school_id = #{schoolId}")
	public Integer getMyClassCount(Integer schoolId);
	
	@Select("select school_id from class where class_id = #{classId}")
	public Integer querySchoolIdByClassId(Integer classId);
	
	@Select("select * from class where school_id = #{schoolId} and class_name like #{className}")
	public List<MyClass> queryClassByName(String className,Integer schoolId);
	
}
