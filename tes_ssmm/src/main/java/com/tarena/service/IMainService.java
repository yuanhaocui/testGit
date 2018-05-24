package com.tarena.service;

import java.util.List;

import com.tarena.entity.Course;
import com.tarena.entity.Main;
import com.tarena.entity.Role;


public interface IMainService {
	
	/*今日动态*/
	/**
	 * 注册用户
	 */
	public int getUserCount();
	/**
	 * 上传视频
	 */
	public int uploadVideo();
	
	/**
	 * 收藏视频
	 */
	public int collectVideo();
	
	/**
	 * 购买视频
	 */
	public int buyVideo();
	
	/**
	 * 最新活动
	 */
	public int newActivity();
	
	/**
	 * 最新评论
	 */
	public int newComment();
	
	
	
	
	
	/*用户统计图*/
	//查询所有角色名字
	public List<Role> findAllRoleName();
	//根据角色名字查询用户个数
	public int findUserCountByRoleName(String roleName);

	/*视频统计图*/
	//查询所有课程名字
	public List<Course> findAllCourseName();
	//根据课程名字查询视频个数
	public int findVideoCountByCourseName(String courseName);
	
	
	
	
	
	/*用户增长率*/
	/**
	 * 月环比
	 */
	public int userMonthBasis();
	/**
	 * 月同比
	 */
	public int userSameMonth();
	/**
	 * 季环比
	 */
	public int userQuarterBasis();
	/**
	 * 季同比
	 */
	public int userSameQuarter();
	/*视频增长率*/
	/**
	 * 月环比
	 */
	public int videoMonthBasis();
	/**
	 * 月同比
	 */
	public int videoSameMonth();
	/**
	 * 季环比
	 */
	public int videoQuarterBasis();
	/**
	 * 季同比
	 */
	public int videoSameQuarter();
	
	
	
	
	
	/**
	 * 视频收藏排行
	 */
	public List<Main> collectionVideoOrder();
	
	/**
	 * 视频购买排行
	 */
	public List<Main> purchasedVideoOrder();

}
