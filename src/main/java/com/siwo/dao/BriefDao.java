package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Brief;
import com.siwo.model.MyClass;

@Mapper
public interface BriefDao {

	//	添加课程
	@Insert("insert into brief (brief_content,brief_whether,school_id,creator,creation_time,course_content,brief_img) "
			+ "values (#{briefContent},#{briefWhether},#{schoolId},#{creator},#{creationTime},#{courseContent},#{briefImg})")
	@Options(useGeneratedKeys = true,keyProperty = "briefId")
	public Integer addBrief(Brief brief);
	
	//	绑定班级信息
	@Insert("INSERT INTO `class_brief`(`class_id`, `brief_id`) VALUES (#{classId}, #{briefId})")
	public Integer addBriefClass(Integer classId,Integer briefId);
	
	//	取消绑定班级
	@Delete("delete from class_brief where brief_id = #{briefId}")
	public Integer deleteBriefClass(Integer briefId);
	
	//	查询课程绑定的班级
	@Select("select * from class_brief cb left join class c on cb.class_id = c.class_id where cb.brief_id = #{briefId}")
	public List<MyClass> queryMyClassByBriefId(Integer briefId);
	
	@Select("select video_id from video_brief where brief_id = #{briefId}")
	public List<Integer> queryBriefBindingVideo(Integer briefId);
	
	//	删除课程
	public Integer deleteBrief(String[] briefIds);
	
	//	修改课程信息
	public Integer updateBrief(Brief brief);
	
	//	查找班级课程
	@Select("select * from brief where class_id = #{classId}")
	public List<Brief> queryBriefByClassId(Integer classId );
	
	@Select("select * from brief where brief_id IN (SELECT brief_id from class_brief where class_id = #{classId}) and brief_whether = #{briefWhether} and brief_content like #{briefContent}")
	public List<Brief> getBriefByClassId(Integer classId,String briefWhether,String briefContent);
	
	//	查询所有课程
	@Select("SELECT " + 
			"* " + 
			"FROM " + 
			"brief b " + 
			"where school_id = #{schoolId}")
	@ResultMap("getAllBrief")
	public List<Brief> queryAllBrief(Integer schoolId);
	
	//	查询单个课程
	@Select("select * from brief b left join video_brief vb on b.brief_id = vb.brief_id left join video v on vb.video_id = v.video_id where b.brief_id = #{briefId}")
	@ResultMap("getAllBrief")
	public Brief queryBriefByBriefId(Integer briefId);
	
	//	分页查询
	@Select("SELECT " + 
			"* " + 
			"FROM " + 
			"brief b " + 
			"where school_id = #{schoolId} limit #{page},#{limit}")
	public List<Brief> limiBriefs(Integer page, Integer limit,Integer schoolId);
	
	//	课程数量
	@Select("select count(*) from brief where school_id = #{schoolId}")
	public Integer getBriefCount(Integer schoolId);
	
	//	查询教学课
	@Select("SELECT " + 
			"* " + 
			"FROM " + 
			"brief b " + 
			"LEFT JOIN class_brief cb ON b.brief_id = cb.brief_id where b.school_id = #{schoolId} and brief_whether = #{isChoiceness} and cb.class_id = #{classId}")
	@ResultMap("getAllBrief")
	public List<Brief> queryBriefsBySchoolIdAndIsChoiceness(Integer schoolId,String isChoiceness,Integer classId);
	
	//	查询公开课
	@Select("SELECT " + 
			"* " + 
			"FROM " + 
			"brief b " + 
			"LEFT JOIN video_brief vb ON b.brief_id = vb.brief_id where b.school_id = #{schoolId} and brief_whether != #{isChoiceness}")
	@ResultMap("getAllBrief")
	public List<Brief> queryBriefsBySchoolIdAndIsChoiceness02(Integer schoolId,String isChoiceness);
	
	//	模糊查询课程标题
	@Select("SELECT " + 
			"* " + 
			"FROM " + 
			"brief b " + 
			"LEFT JOIN video_brief vb ON b.brief_id = vb.brief_id " +
			"where b.school_id = #{schoolId} and brief_whether != #{briefWhether} and brief_content like #{briefContent}")
	@ResultMap("getAllBrief")
	public List<Brief> queryBriefsBySchoolIdAndLikeSelect(Integer schoolId,String briefContent,String briefWhether);
	
	//	后台系统按课程标题模糊查询课程
	@Select("SELECT * FROM brief where school_id = #{schoolId} and brief_content like #{briefContent}")
	public List<Brief> queryBriefsByBriefContent(String briefContent,Integer schoolId);
}
