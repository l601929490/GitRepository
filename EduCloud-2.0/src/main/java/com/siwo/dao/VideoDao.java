package com.siwo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.siwo.model.Brief;
import com.siwo.model.Video;

@Mapper
public interface VideoDao {

	//	添加视频
	@Insert("insert into video (video_sum,video_title,video_time,video_address,video_img,video_creator,video_creation_time,school_id,video_msg,recording) "
			+ "values (#{videoSum},#{videoTitle},#{videoTime},#{videoAddress},#{videoImg},#{videoCreator},#{videoCreationTime},#{schoolId},#{videoMsg},#{recording})")
	public Integer addVideo(Video viderAddress);
	
	//	绑定课程
	@Insert("INSERT INTO `video_brief`(`video_id`, `brief_id`) VALUES (#{videoId}, #{briefId})")
	public Integer bindingVideoBrief(Integer videoId,Integer briefId);
	
	//	删除视频绑定的课程
	@Delete("delete from video_brief where video_id = #{videoId}")
	public Integer deleteVideoBindingBrief(Integer videoId);
	
	//	删除课程邦定的视频
	@Delete("delete from video_brief where brief_id = #{briefId}")
	public Integer deleteBriefBindingVideo(Integer briefId);
	
	//	查询视频绑定的课程
	@Select("select * from video_brief vb left join brief b on vb.brief_id = b.brief_id where vb.video_id = #{videoId}")
	public List<Brief> queryVideoBindingBrief(Integer videoId);
	
	//	按课程id查询视频
	@Select("select * from video v right join video_brief vb on v.video_id = vb.video_id where vb.brief_id = #{briefId}")
	public List<Video> querybyBriefId(Integer briefId);
	
	//	按视频标题模糊查询
	@Select("select * from video where school_id = #{schoolId} and video_title like #{videoTitle}")
	public List<Video> queryVideoByVideoTitle(Integer schoolId,String videoTitle);
	
	@Select("select * from video where video_id = #{videoId}")
	public Video queryByVideoId(Integer videoId);
	
	@Select("update video set video_sum = #{videoSum} where video_id = #{videoId}")
	public Integer updateVideoSum(Integer videoSum,Integer videoId);
//	
//	@Select("SELECT * from video where school_id = #{schoolId} AND video_title LIKE #{title}")
//	public Video queryVideoByTitle(String title,Integer schoolId);
	
	@Delete("delete from video where video_id = #{videoId}")
	public Integer deleteVideo(Integer videoId);
	
	public Integer deleteManyVideo(String[] videoIds);
	
	public Integer updateVideo(Video address);
	
	@Select("select * from video where school_id = #{schoolId} limit #{page},#{limit}")
	public List<Video> limitVideo(Integer page, Integer limit,Integer schoolId);

	@Select("select count(*) from video where school_id = #{schoolId}")
	public Integer getVideoCount(Integer schoolId);
}
