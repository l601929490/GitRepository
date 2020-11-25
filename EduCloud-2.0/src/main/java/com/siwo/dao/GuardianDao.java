package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Guardian;
import com.siwo.model.Student;

@Mapper
public interface GuardianDao {

	/**
	 * 添加监护人
	 * @param guardian
	 * @return
	 */
	@Insert("insert into guardian (guardian_name,guardian_phone,guardian_creator,guardian_creation_time,school_id,relationship) "
			+ "values (#{guardianName},#{guardianPhone},#{guardianCreator},#{guardianCreationTime},#{schoolId},#{relationship})")
	@Options(useGeneratedKeys = true,keyColumn = "guardian_id",keyProperty = "guardianId")
	public Integer addGuardian(Guardian guardian);
	
	/**
	 * 	绑定学生和监护人
	 * @param guardianId
	 * @param studentId
	 * @param relationship
	 * @return
	 */
	@Insert("insert into guardian_student (guardian_id,guardian_student_id,relationship) values (#{guardianId},#{studentId},#{relationship})")
	public Integer addGuardianStudent(Integer guardianId,Integer studentId,String relationship);
	
	/**
	 * 	解除绑定
	 * @return
	 */
	@Delete("delete from guardian_student where guardian_student_id = #{studentId} and guardian_id = #{guardianId}")
	public Integer deleteGuardianAndStudent(Integer guardianId,Integer studentId);
	
	/**
	 * 	更新监护人信息
	 */
	public Integer updateGuardian(Guardian guardian);
	
	/**
	 * 	按手机号查询监护人
	 * @param guardianPhone
	 * @return
	 */
	@Select("select * from guardian where guardian_phone = #{guardianPhone} and school_id = #{schoolId}")
	public Guardian queryGuardianByPhone(String guardianPhone,Integer schoolId);
	
	@Select("select * from guardian where guardian_phone = #{guardianPhone}")
	public List<Guardian> queryGuardiansByPhone(String guardianPhone);
	
	@Select("select * from guardian g left join guardian_student gs on g.guardian_id = gs.guardian_id left join student s on s.student_id = gs.guardian_student_id where g.guardian_phone = #{guardianPhone} and g.school_id = #{schoolId}")
	@ResultMap("guar_1")
	public Guardian getGuardianByPhone(String guardianPhone,Integer schoolId);
	
	
	/**
	 *	 获取所有监护人信息
	 * @return
	 */
	@Select("select * from guardian where school_id = #{schoolId}")
	public List<Guardian> getAllGuardian(Integer schoolId);
	
	/**
	 * 	获取监护人的总数
	 * @return
	 */
	@Select("select count(*) from guardian where school_id = #{schoolId}")
	public Integer getGuardianCount(Integer schoolId);
	
	/**
	 * 	按照学生id查询监护人信息
	 * @param studentId
	 * @return
	 */
	@Select("select * from guardian g left join guardian_student gs on g.guardian_id = gs.guardian_id where gs.guardian_student_id = #{studentId}")
	public List<Guardian> queryGuardianByStudentId(Integer studentId);
	
	/**
	 * 	小程序获取监护人监护的学生信息
	 * @param guardianId
	 * @return
	 */
	@Select("select * from student s left join guardian_student gs on s.student_id = gs.guardian_student_id where gs.guardian_id = #{guardianId}")
	public List<Student> queryStudentByGuardianId(Integer guardianId);
	
	/**
	 * 	查询学生按姓名和监护人
	 * @param guardianId
	 * @return
	 */
	@Select("select * from student s left join guardian_student gs on s.student_id = gs.guardian_student_id where gs.guardian_id = #{guardianId} and s.student_name = #{studentName}")
	public Student queryStudentByGuardianIdAndStudentName(Integer guardianId,String studentName);
	
//	/**
//	 * 	获取学生的监护人信息
//	 */
//	@Select("select * from guardian g right join guardian_student gs on g.guardian_id = gs.guardian_id right join student s on s.student_id = gs.guardian_student_id where s.student_id = #{studentId}")
//	public List<Guardian> getStudentGuardianByStudentId(Integer studentId);
	
	/**
	 * 	分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
	@Select("select * from guardian where school_id = #{schoolId} limit #{page},#{limit}")
	public List<Guardian> limitGuardian(Integer page,Integer limit,Integer schoolId);
	
	/**
	 * 	删除一个监护人
	 * @param guardianId
	 * @return
	 */
	@Delete("delete from guardian where guardian_id = #{guardianId}")
	public Integer deleteGuardianById(Integer guardianId);
	
	/**
	 * 	删除多个监护人
	 * @param guardianIds
	 * @return
	 */
	public Integer deleteGuardianMany(String[] guardianIds);
	
	/**
	 * 	按id查监护人
	 * @param id
	 * @return
	 */
	@Select("select * from guardian where guardian_id = #{id}")
	public Guardian queryGuardianById(Integer id);
	
	/**
	 * 	查询学生监护人绑定的数量
	 * @param guardianStudentId
	 * @param guardianId
	 * @return
	 */
	@Select("select count(*) from guardian_student where guardian_student_id = #{guardianStudentId} and guardian_id = #{guardianId}")
	public Integer queryGuardianAndStudent(Integer guardianStudentId,Integer guardianId);
	
	@Delete("delete from guardian_student where guardian_id = #{guardianId}")
	public Integer deleteGuardianStudent(Integer guardianId);
	
	@Delete("delete from guardian_student where guardian_student_id = #{guardianStudentId}")
	public Integer deleteStudentGuardian(Integer guardianStudentId);
	
	/**
	 * 	按名字模糊查询
	 * @param guardianName
	 * @param schoolId
	 * @return
	 */
	@Select("select * from guardian where school_id = #{schoolId} and guardian_name like #{guardianName}")
	public List<Guardian> queryGuardianByName(String guardianName,Integer schoolId);
}
