package com.tarena.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.Course;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface ICourseService {

	
	public Page findCoursesByPage(Page page);
	
	public Result deleteCourse(String userId);
	
	public Result addCourse(Course course,
						  MultipartFile file,
						  HttpServletRequest request,
						  HttpServletResponse response);
	
	public Result updateCourse(Course course,
			  MultipartFile file,
			  HttpServletRequest request,
			  HttpServletResponse response);

	public Page findMyCoursesByPage(Page page, String userId);

	public Result deleteMyCourse(String id);

	public Result getAllCourse();
	public Result addMyCourses(Course course);

	public Result getCourseById(Course course);
}
