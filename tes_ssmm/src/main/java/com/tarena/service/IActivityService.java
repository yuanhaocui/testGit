package com.tarena.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.Activity;
import com.tarena.entity.Course;
import com.tarena.entity.Message;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface IActivityService {

	/**
	 *	新活动
	 */
	public List<Message> findActivityByChecked();
	
	public Page findActivitysByPage(Page page);
	
	public Result deleteActivity(String userId);
	
	public Result addActivity(Activity activity,
						  MultipartFile file,
						  HttpServletRequest request,
						  HttpServletResponse response);
	
	public Result updateActivity(Activity activity,
			  MultipartFile file,
			  HttpServletRequest request,
			  HttpServletResponse response);
	
	public List<Course> findCourseName();
	
	public boolean isPass(String activity_id);
	
	public int countNumber();
}
