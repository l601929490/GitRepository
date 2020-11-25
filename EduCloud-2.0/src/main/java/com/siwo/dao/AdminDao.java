package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.Admin;
import com.siwo.model.SuperAdmin;

@Mapper
public interface AdminDao {

	/**
	 * 	管理员登陆
	 * @param adminName
	 * @param adminPassword
	 * @return
	 */
	@Select("select * from admin where admin_account = #{adminAccount}")
	public Admin queryAdminByAdminAccount(String adminAccount);
	
	/**
	 * 	修改密码
	 * @param adminAccount
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Update("update admin set admin_password = #{newPassword} where admin_account = #{adminAccount} and admin_password = #{oldPassword}")
	public Integer updateAdminPassword(String adminAccount,String oldPassword,String newPassword);
	
	/**
	 * 	查询管理员权限
	 * @param adminId
	 * @return
	 */
	@Select("select * from permission p left join permission_admin a on p.permission_id = a.permission_id where a.admin_id = #{adminId}")
	public List<String> queryAdminPermissions(Integer adminId);
	
	/**
	 * 	添加管理员
	 * @param admin
	 * @return
	 */
	@Insert("insert into admin (admin_role,admin_name,admin_password,available,is_super_admin,show_school_id,admin_account,creator,creation_time) values (#{adminRole},#{adminName},#{adminPassword},#{available},#{isSuperAdmin},#{showSchoolId},#{adminAccount},#{creator},#{creationTime})")
	@Options(useGeneratedKeys = true,keyColumn = "admin_id",keyProperty = "adminId")
	public Integer addAdmin(Admin admin);
	
	/**
	 * 	添加账号权限
	 * @param adminId
	 * @return
	 */
	@Insert("insert into permission_admin (admin_id,permission_id) values (#{adminId},#{permissionId})")
	public Integer addAdminPermission(Integer adminId,Integer permissionId);
	
	/**
	 * 	账号绑定学校
	 * @param adminId
	 * @param schoolId
	 * @return
	 */
	@Insert("insert into admin_and_school (admin_id,school_id) values (#{adminId},#{schoolId})")
	public Integer addSchoolAdmin(Integer adminId,Integer schoolId);
	
	/**
	 * 	账号绑定机构
	 * @param adminId
	 * @param companyId
	 * @return
	 */
	@Insert("insert into admin_and_company (admin_id,company_id) values (#{adminId},#{companyId})")
	public Integer addCompantAdmin(Integer adminId,Integer companyId);
	/**
	 *  查询超级管理员
	 * @param superAdminName
	 * @return
	 */
	@Select("select * from superadmin where super_admin_name = #{superAdminName}")
	public SuperAdmin querySuperAdminByName(String superAdminName);
	
	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @return
	 */
	@Select("select * from admin a left join admin_and_company ac on a.admin_id = ac.admin_id left join company c on ac.company_id = c.company_id where admin_role != 'teacher' and admin_role != 'superAdmin' limit #{page},#{limit}")
	public List<Admin> limitAdmin(Integer page,Integer limit);
	
	/**
	 * 获取记录总数
	 * @return
	 */
	@Select("select count(*) from admin where admin_role != 'teacher' and admin_role != 'superAdmin'")
	public Integer getAdminCount();
	
	//	查询机构id
	@Select("select company_id from admin_and_company where admin_id = #{adminId}")
	public Integer queryCompantIdByAdminId(Integer adminId);
	
	//	修改要展示的学校id
	@Update("update admin set show_school_id = #{schoolId} where admin_account = #{adminAccount}")
	public Integer updateShowSchoolId(Integer schoolId,String adminAccount);
	
	//	删除老师账号
	@Delete("delete from admin where admin_account = #{account} and show_school_id = #{schoolId}")
	public Integer deleteAdmin(String account,Integer schoolId);
	
	//	超级管理员删除账号
	public Integer superAdminDeleteAdmin(String[] adminIds);
	
	//	按机构名搜索账号
	@Select("select * from admin a left join admin_and_company ac on a.admin_id = ac.admin_id left join company c on ac.company_id = c.company_id where c.company_name like #{companyName}")
	public List<Admin> queryAdminByCompanyName(String companyName);
	
	/**
	 * 	修改账号是否开通
	 * @param admin
	 * @return
	 */
	@Update("UPDATE `admin` SET `available` = #{available} WHERE `admin_id` = #{adminId}")
	public Integer updateAvailable(Admin admin);
}
