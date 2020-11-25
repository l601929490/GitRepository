package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.siwo.model.BulletinBoard;

@Mapper
public interface BulletinBoardDao {

	@Insert("INSERT INTO `bulletinboard`(`bulletinBoardId`, `content`, checked,`companyId`) VALUES (NULL, #{content},#{checked},#{companyId})")
	public Integer addBulletinBoard(BulletinBoard board);
	
	public Integer deleteBulletinBoard(String[] bulletinBoardId);
	
	public Integer updateBulletinBoard(BulletinBoard board);
	
	@Update("update bulletinboard set checked = #{checked} where companyId = #{companyId}")
	public Integer updateBulletinBoardChecked(Integer checked,Integer companyId);
	
	@Select("select * from bulletinboard where companyId = #{companyId}")
	public List<BulletinBoard> queryAllBulletinBoard(Integer companyId);
	
	@Select("select * from bulletinboard where companyId = #{companyId} and checked = 1")
	public BulletinBoard queryBulletinBoardByCompanyIdAndChecked(Integer companyId);
	
	@Select("select * from bulletinboard where checked = #{checked}")
	public BulletinBoard queryBulletinBoardByChecked(Integer checked);
}
