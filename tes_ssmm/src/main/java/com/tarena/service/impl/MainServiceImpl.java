package com.tarena.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.entity.Course;
import com.tarena.entity.Main;
import com.tarena.entity.Role;
import com.tarena.dao.CourseMapper;
import com.tarena.dao.MainMapper;
import com.tarena.dao.RoleMapper;
import com.tarena.dao.UserMapper;
import com.tarena.dao.VideoMapper;
import com.tarena.service.IMainService;

@Service("mainService")
public class MainServiceImpl implements IMainService{
	@Resource(name="mainMapper")
	private MainMapper mainMapper;
	
	@Resource(name="userMapper")
	private UserMapper userMapper;
	
	@Resource(name="roleMapper")
	private RoleMapper roleMapper;
	
	@Resource(name="courseMapper")
	private CourseMapper courseMapper;
	
	@Resource(name="videoMapper")
	private VideoMapper videoMapper;
	
	/*今日动态*/
	/**
	 * 注册用户
	 */
	
	public int getUserCount() {
		
		int count = this.mainMapper.getUserCount();
		
		return count;
	}
	
	/**
	 * 上传视频
	 */
	
	public int uploadVideo() {
		
		int count = this.mainMapper.uploadVideo();
		
		return count;
	}

	/**
	 * 收藏视频
	 */
	
	public int collectVideo() {
		
		int count = this.mainMapper.collectVideo();
		
		return count;
	}

	/**
	 * 购买视频
	 */
	
	public int buyVideo() {
		
		int count = this.mainMapper.buyVideo();
		
		return count;
	}
	
	/**
	 * 最新活动
	 */
	
	public int newActivity() {
		
		int count = this.mainMapper.newActivity();
		
		return count;
	}
	
	/**
	 * 最新评论
	 */
	
	public int newComment() {
		int count = this.mainMapper.newComment();
		
		return count;
	}
	
	
	
	
	
	/*用户统计图*/
	//查询所有角色名字
	
	public List<Role> findAllRoleName() {
		/*视频统计图*/
		//查询所有课程名字
		List<Role> roles=null;
		
		roles=this.roleMapper.findAllRoleName();
		
		return roles;
	}
	
	//根据角色名字查询用户个数
	
	public int findUserCountByRoleName(String roleName) {
		
		int count=0;
		
		count=this.userMapper.findUserCountByRoleName(roleName);
		
		return count;
	}
	
	/*视频统计图*/
	//查询所有课程名字
	
	public List<Course> findAllCourseName() {
		
		List<Course> courses=null;
		
		courses=this.courseMapper.findAllCourseName();
		
		return courses;
	}
	//根据课程名字查询视频个数
	
	public int findVideoCountByCourseName(String courseName) {
		
		int count=0;
		
		count=this.videoMapper.findVideoCountByCourseName(courseName);
		
		return count;
	}

	
	
	
	
	/*用户增长率*/
	/**
	 * 月环比
	 */
	public int userMonthBasis(){
		
		int count = this.mainMapper.userMonthBasis();
		
		return count;
	}
	/**
	 * 月同比
	 */
	public int userSameMonth(){
		
		int count = this.mainMapper.userSameMonth();
		
		return count;
	}
	/**
	 * 季环比
	 */
	public int userQuarterBasis(){
		
		int count = this.mainMapper.userQuarterBasis();
		
		return count;
	}
	/**
	 * 季同比
	 */
	public int userSameQuarter(){
		
		int count = this.mainMapper.userSameQuarter();
		
		return count;
	}
	/*视频增长率*/
	/**
	 * 月环比
	 */
	public int videoMonthBasis(){
		
		int count = this.mainMapper.videoMonthBasis();
		
		return count;
	}
	/**
	 * 月同比
	 */
	public int videoSameMonth(){
		
		int count = this.mainMapper.videoSameMonth();
		
		return count;
	}
	/**
	 * 季环比
	 */
	public int videoQuarterBasis(){
		
		int count = this.mainMapper.videoQuarterBasis();
		
		return count;
	}
	/**
	 * 季同比
	 */
	public int videoSameQuarter(){
		
		int count = this.mainMapper.videoSameQuarter();
		
		return count;
	}
	
	
	
	
	
	/**
	 * 视频收藏排行
	 */
	
	public List<Main> collectionVideoOrder() {
		
		List<Main> mains = this.mainMapper.collectionVideoOrder();
		
		return mains;
	}

	/**
	 * 视频购买排行
	 */
	
	public List<Main> purchasedVideoOrder() {
		
		List<Main> mains = this.mainMapper.purchasedVideoOrder();
		
		return mains;
	}




}
