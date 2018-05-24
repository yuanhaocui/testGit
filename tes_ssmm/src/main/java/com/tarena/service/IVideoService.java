package com.tarena.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.Course;
import com.tarena.entity.Message;
import com.tarena.entity.User;
import com.tarena.entity.Video;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface IVideoService {

	
	public Page findVideosByPage(Page page);
	
	//添加视频
	public Result addVideo(Video video,
						  MultipartFile file,
						  HttpServletRequest request,
						  HttpServletResponse response);
	
	//更新视频
	public Result updateVideo(Video video,
			  MultipartFile file,
			  HttpServletRequest request,
			  HttpServletResponse response);
	
	public boolean updateVideo(Video video);
	
	public boolean deleteVideo(String videoId);
	
	public Result findVideoById(String videoName);/**
	 * 新视频
	 */
	public List<Message> findVideoByChecked();
	
	public List<Course> showCourseName();
	
	public List<User> showUserName();
	
	public boolean updateState(String id);
	
	public int countNumber();

}
