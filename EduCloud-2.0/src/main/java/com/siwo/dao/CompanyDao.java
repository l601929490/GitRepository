package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Company;

@Mapper
public interface CompanyDao {

	@Insert("INSERT INTO `company`( `company_name`, `company_campus`, `company_creation_time`, `company_charge`, `company_principal`, `company_principal_phone`, company_creator,company_editor,company_edit_session,company_app_id,company_app_secret,wx_show_school_id,company_qrcode,start_time,end_time,company_img) "
			+ "VALUES ( #{companyName}, #{companyCampus}, #{companyCreationTime}, #{companyCharge}, #{companyPrincipal}, #{companyPrincipalPhone},#{companyCreator},#{companyEditor},#{companyEditSession},#{companyAppId},#{companyAppSecret},#{wxShowSchoolId},#{companyQrcode},#{startTime},#{endTime},#{companyImg});")
	@Options(useGeneratedKeys = true,keyColumn = "company_id",keyProperty = "companyId")
	public Integer addCompany(Company company);
	
	@Select("select * from company")
	public List<Company> getAllCompany();
	
	@Select("select * from company limit #{page},#{limit}")
	public List<Company> limitCompany(Integer page,Integer limit);
	
	@Select("select count(*) from company")
	public int getCompanyCount();
	
	public Integer updateCompany(Company company);
	
	@Delete("delete from company where company_id = #{companyId}")
	public Integer deleteCompanyById(Integer compantId);
	
	public Integer deleteManyCompany(String[] compantIds);
	
//	@Select("SELECT c.show_school_id from admin_and_company ac LEFT JOIN company c on ac.company_id = c.company_id WHERE ac.admin_id = #{adminId} ")
//	public Integer queryShowSchoolId(Integer adminId);
	
	@Select("SELECT * from company where company_app_id = #{appId}")
	public Company queryCompanyByAppId(String appId);
	
	@Select("SELECT * from company where company_id = #{companyId}")
	public Company queryCompanyByCompanyId(Integer companyId);
	
	@Select("select company_qrcode from company where company_id = (select company_id from school where school_id = #{schoolId})")
	public String queryCompanyQrcodeBySchoolId(Integer schoolId);
	
	@Select("select * from company where company_id = (SELECT company_id from school where school_id = #{schoolId})")
	public Company queryCompanyBySchoolId(Integer schoolId);
	
	//	模糊查询机构
	@Select("select * from company where company_name like #{companyName}")
	public List<Company> queryCompanyLikeCompanyName(String companyName);
}
