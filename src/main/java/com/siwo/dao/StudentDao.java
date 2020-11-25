package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.MyClass;
import com.siwo.model.Student;
import com.siwo.model.StudentAndGuardian;
import com.siwo.model.StudentExcel;
import com.siwo.model.Teacher;

@Mapper
public interface StudentDao {
	
	/**
	 * 添加学生
	 * @param student
	 * @return
	 */
	@Insert("insert into student (student_name,student_age,student_sex,student_phone,count,school_id,student_creator,student_creation_time,fate,grade,schoolTitle) "
			+ "values (#{studentName},#{studentAge},#{studentSex},#{studentPhone},#{count},#{schoolId},#{studentCreator},#{studentCreationTime},#{fate},#{grade},#{schoolTitle})")
	@Options(useGeneratedKeys = true,keyColumn = "student_id",keyProperty = "studentId")
	public Integer addStudent(Student student);

	/**
	 * 	绑定学生班级
	 * @param studentClassId
	 * @param studentId
	 * @return
	 */
	@Insert("insert into class_student (student_class_id,student_id) values (#{studentClassId},#{studentId})")
	public Integer addStudentClass(Integer studentClassId,Integer studentId);
	
	/**
	 * 	添加学生的监护人电话
	 * @param studentAndGuardian
	 * @return
	 */
	@Insert("insert into student_and_guardian_phone (student_id,guardian_phone) values (#{studentId},#{guardianPhone})")
	public Integer addStudentAndGuardian(StudentAndGuardian studentAndGuardian);
	
	/**
	 * 	查询学生监护人电话
	 * @param studentId
	 * @return
	 */
	@Select("select * from student_and_guardian_phone where student_id = #{studentId}")
	public List<StudentAndGuardian> queryStudentAndGuardian(Integer studentId);
	
	/**
	 * 	查找老师是否在此班级
	 * @param classId
	 * @param studentId
	 * @return
	 */
	@Select("select student_id from class_student where student_class_id = #{classId} and student_id = #{studentId}")
	public Integer queryStudentByClassIdAndStudentId(Integer classId,Integer studentId);
	
	/**
	 * 	查找班级所有学生
	 * @param classId
	 * @return
	 */
	@Select("select * from student s left join class_student cs on s.student_id = cs.student_id where cs.student_class_id = #{classId}")
	public List<Student> queryStudentByClassId(Integer classId);
	
	/**
	 * 	查找班级中未打卡的学生
	 * @param classId
	 * @return
	 */
	@Select("SELECT * FROM student WHERE " +
			"student_id IN ( SELECT student_id FROM class_student WHERE student_class_id = #{classId}  AND student_id NOT IN " +
			"( SELECT student_id FROM attendance WHERE class_id = #{classId} and task_id = #{taskId}))")
	public List<Student> queryNotSubmittedStudentByClassId(Integer classId,Integer taskId);
	
	
	@Select("SELECT * FROM student WHERE " +
			"student_id IN ( SELECT student_id FROM class_student WHERE student_class_id = #{classId}  AND student_id NOT IN " +
			"( SELECT student_id FROM attendance WHERE class_id = #{classId} and task_id = #{taskId} AND attendance_time > #{startTime} AND attendance_time < #{endTime}))")
	public List<Student> queryNotSubmittedStudentByClassIdAndLongTask(Integer classId,Integer taskId,String startTime,String endTime);
	/**
	 * 	解除学生和班级绑定的信息
	 * @param classId
	 * @param studentId
	 * @return
	 */
	@Delete("delete from class_student where student_class_id =#{classId} and student_id = #{studentId}")
	public Integer deleteStudentAndClass(Integer classId,Integer studentId);
	
	/**
	 * 修改学生信息
	 * @param student
	 * @return
	 */
	public Integer update(Student student);
	
	/**
	 * 	修改学生监护人电话
	 * @param studentAndGuardian
	 * @return
	 */
	@Update("update student_and_guardian_phone set guardian_phone = #{guardianPhone} where student_id = #{studentId}")
	public Integer updateStudentAndGuardian(StudentAndGuardian studentAndGuardian);
	
	/**
	 * 	删除学生监护人电话
	 * @param studentId
	 * @return
	 */
	@Delete("delete from student_and_guardian_phone where student_id = #{studentId}")
	public Integer deleteStudentAndGuardian(Integer studentId);
	
	/**
	 * 根据学生id查询
	 */
	@Select("select * from student s left join class_student cs on s.student_id = cs.student_id where s.student_id = #{id} and s.school_id = #{schoolId} GROUP BY s.student_id")
	public Student queryByIdAndSchoolId(Integer id,Integer schoolId);
	
	@Select("select * from student s left join class_student cs on s.student_id = cs.student_id where s.student_id = #{id} GROUP BY s.student_id")
	public Student queryById(Integer id);
	
	/**
	 * 	更新打卡记录
	 * @param count
	 * @return
	 */
	@Update("update student set count = #{count} where student_id = #{studentId}")
	public Boolean updateCount(Integer count,Integer studentId);
	
	/**
	 * 	根据id删除
	 * @param studentId
	 * @return
	 */
	@Delete("delete from student where student_id = #{studentId}")
	public Integer delete(Integer studentId);
	
	@Delete("delete from class_student where student_id = #{studentId}")
	public Integer deleteClass(Integer studentId);
	
	@Delete("delete from class_student where student_class_id = #{classId}")
	public Integer deleteStudent(Integer classId);
	
	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
//	@ResultMap("stu_3")
	@Select("select * from student where school_id =#{schoolId} ORDER BY student_creation_time desc")
	public List<Student> limitStudent(Integer schoolId);
	
	/**
	 * 	根据手机号后查询
	 * @param phone
	 * @return
	 */
	@Select("select * from student s LEFT JOIN class_student cs ON s.student_id = cs.student_id LEFT JOIN class c ON cs.student_class_id = c.class_id where student_phone = #{phone} and s.school_id = #{schoolId}")
	@ResultMap("stu_3")
	public Student queryStudentByPhone(String phone,Integer schoolId);
	
	@Select("select * from student where student_phone = #{phone}")
	public List<Student> queryStudentsByPhone(String phone);
	
	
	@Select("select * from student where student_phone = #{phone} and school_id = #{schoolId}")
	public Student getStudentByPhoneAndSchoolId(String phone,Integer schoolId);
	/**
	 * 	根据id查询
	 * @param studentId
	 * @return
	 */
	@Select("select * from student where student_id = #{studentId}")
	public Student queryStudentById(Integer studentId);
	
	/**
	 * 	根据classId查询全班人数
	 * @param classId
	 * @return
	 */
	@Select("select count(*) from class_student where student_class_id = #{classId}")
	public Integer queryAllClassStudentCountByClassId(Integer classId);
	
	/**
	 * 	根据classId查询全班人
	 * @param classId
	 * @return
	 */
	@Select("select * from student s left join class_student cs on s.student_id = cs.student_id LEFT JOIN class c on cs.student_class_id = c.class_id where cs.student_class_id = #{classId}")
	@ResultMap("stu_3")
	public List<Student> queryAllClassStudentByClassId(Integer classId);
	
	/**
	 * 	查询全班学生信息
	 * @param classId
	 * @return
	 */
	@Select("select * from student s left join class_student cs on s.student_id = cs.student_id where cs.student_class_id = #{classId} and school_id = #{schoolId}")
	public List<Student> queryStudentByClassIdAndSchoolId(Integer classId,Integer schoolId);

	
	/**
	 * 	获取所有学生信息
	 * @return
	 */
	@Select("select * from student where school_id = #{schoolId}")
	public List<Student> getStudentBySchoolId(Integer schoolId);
	
	@Select("select * from student where school_id = #{schoolId}")
	public List<StudentExcel> getStudentExcelBySchoolId(Integer schoolId);
	
	/**
	 * 	查询学生总数
	 * @return
	 */
	@Select("select count(*) from student where school_id = #{schoolId}")
	public Integer getStudentCount(Integer schoolId);
	
	/**
	 * 	添加多个学生
	 * @param students
	 * @return
	 */
	public Integer addManyStudents(@Param(value = "students")List<Student> students);
	
	public Integer addManyStudentsClass(@Param(value = "students")List<Student> students);
	
	/**
	 * 	按照学生姓名查询
	 * @return
	 */
	@Select("select * from student where school_id = #{schoolId} and student_name LIKE '%${studentName}%'")
	public List<Student> queryStudentByName(String studentName,Integer schoolId);
	
	/**
	 * 	按手机号和学校id
	 * @param phone
	 * @param schoolId
	 * @return
	 */
	@Select("select * from student where student_phone like #{phone} and school_id = #{schoolId}")
	public List<Student> queryStudentByPhoneAndSchoolId(String phone,Integer schoolId);
	
	/**
	 * 	删除多个
	 * @param studentIds
	 * @return
	 */
	public Integer deleteManyStudent(String[] studentIds);
	
	public Integer deleteManyStudentClass(String[] studentIds);
	
	@Delete("delete from class_student where student_class_id = #{classId} and student_id = #{studentId}")
	public Integer deleteClassStudent(Integer classId,Integer studentId);
	
	/**
	 * 	修改坚持天数
	 * @param studentId
	 * @param fate
	 * @return
	 */
	@Update("update student set fate = #{fate} where student_id = #{studentId}")
	public Integer updateStudentFate(Integer studentId,Integer fate);
	
	/**
	 * 	获取监护人监护的学生信息
	 * @param guardianId
	 * @return
	 */
	@Select("select * from student s left join guardian_student gs on s.student_id = gs.guardian_student_id where gs.guardian_id = #{guardianId}")
	@ResultMap("stu_3")
	public List<Student> getGuardianStudent(Integer guardianId);
	
	/**
	 * 	按照学生id查所有班级id
	 * @param studentId
	 * @return
	 */
	@Select("SELECT student_class_id from class_student where student_id = #{studentId}")
	public List<Integer> queryStudentClassIdByStudentId(Integer studentId);
	
	/**
	 * 	未分配的学生
	 * @param schoolId
	 * @return
	 */
	@Select("select * from student where student_id not in(select student_id from class_student) and school_id = #{schoolId}")
	public List<Student> queryAllocatedStudent(Integer schoolId);

	/**
	 * 	已分配的学生
	 * @param schoolId
	 * @return
	 */
	@Select("select * from student where student_id in(select student_id from class_student) and school_id = #{schoolId}")
	public List<Student> queryUndistributedStudent(Integer schoolId);
	
	/**
	 * 	查询学生绑定的班级
	 * @param studentId
	 * @return
	 */
	@Select("select student_class_id from class_student where student_id = #{studentId}")
	public List<Integer> queryStudentBindingClass(Integer studentId);
}
