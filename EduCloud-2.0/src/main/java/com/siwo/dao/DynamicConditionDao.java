package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.siwo.model.DynamicCondition;

@Mapper
public interface DynamicConditionDao {
	
	/**
	 * 查询每个机构下面的校区规则配置
	 */
	@Select("select * from dynamic_condition " + 
			"where school_id in (select school_id from school where company_id=#{companyId}) ")
	public List<DynamicCondition> queryDynamicConditionByCompanyId(Integer companyId);
 
	/**
	 * 查询每个校区的规则配置
	 */
	@Select("select * from dynamic_condition where school_id =#{schoolId}")
	public DynamicCondition queryDynamicConditionBySchoolId(Integer schoolId);
	/**
	 * 给校区增加配置规则(之前校区没有配置规则)
	 */
	@Insert("INSERT INTO `dynamic_condition`(`dynamic_condition_id`, `login_dynamic_condition`, `clock_dynamic_condition`, `video_dynamic_condition`, `share_dynamic_condition`, `like_dynamic_condition`, `review_dynamic_condition`,`good_dynamic_condition`, `school_id`) "
			+ "VALUES (null,#{loginDynamicCondition} , #{clockDynamicCondition},#{videoDynamicCondition} , #{shareDynamicCondition}, #{likeDynamicCondition}, #{reviewDynamicCondition},#{goodDynamicCondition},#{schoolId});")
	public int addDynamicCondition(DynamicCondition dynamicCondition);
	/**
	 * 添加,修改,删除配置规则(在已有的配置中加)
	 */
	
	public int changeDynamicCondition(DynamicCondition dynamicCondition);
	@Select("select * from dynamic_condition " + 
			"where school_id = (select school_id from student " + 
			"where student_id =#{studentId})")
	public DynamicCondition queryDynamicConditionByStudentId(Integer studentId);
	
	
}
