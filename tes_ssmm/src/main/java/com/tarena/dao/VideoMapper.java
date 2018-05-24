package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Course;
import com.tarena.entity.Message;
import com.tarena.entity.User;
import com.tarena.entity.Video;
import com.tarena.vo.Page;

public interface VideoMapper {

	public List<String> findVideoByUserId(String userId);
	//根据用户id删除视频
	public int deleteVideoByUserId(String userId);
	//根据视频id删除历史缓冲表
	public int deleteHistoryCacheByVideoId(String videoId);
	//删除视频
	public int deleteVideo(String videoId);
	public Video findVideoById(String videoId);
	
	public List<Video> getVideos(Page page);
	
	public int getCount(Page page);
	
	public int findVideoCountByCourseName(String courseName);
	//新视频
		public List<Message> findVideoByChecked();
		
		public int deleteVideoById(String videoId);
		
		public int deleteVideoByCommentId(String videoId);
		
		public List<Course> showCourseName();
		
		public List<User> showUserName();
		
		public int updateVideo(Video video);
		
		public String getCourseId(String courseName);
		
		public String getUserId(String userName);
		
		public int updateState(String id);
		
		public int countNumber();
}
