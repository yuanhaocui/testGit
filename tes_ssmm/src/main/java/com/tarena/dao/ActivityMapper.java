package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Activity;
import com.tarena.entity.Course;
import com.tarena.entity.Message;
import com.tarena.vo.Page;

public interface ActivityMapper {
	
	//新活动
	public List<Message> findActivityByChecked();
	//根据用户id查询出所有活动id
	public List<String> findActivityIds(String userId);
	//删除指定用户创建的活动
	public int deleteActivityByUserId(String userId);
	
	public int getCount(Page page);
	
	
	public List<Activity> getActivitys(Page page);
	
	
	public int deleteActivity(String activityId);
	
	
	public int addActivity(Activity activity);
	
	public int updateActivity(Activity activity);
	
	public List<Course> findCourseName();
	
	public String getUserId(String userName);
	public String getCourseId(String courseName);
	
	public int isPass(String activity_id);
	
	public int countNumber();
}
