package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Course;
import com.tarena.entity.User;
import com.tarena.vo.Page;

public interface CourseMapper {

	
	public int getCount(Page page);
	
	
	public List<User> getCourses(Page page);
	
	
	public int deleteCourse(String courseId);
	
	
	public int addCourse(Course course);
	
	public int updateCourse(Course course);
	
	
	public List<Course> findAllCourseName();
}
