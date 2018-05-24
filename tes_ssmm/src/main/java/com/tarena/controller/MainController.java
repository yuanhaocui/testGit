package com.tarena.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarena.entity.Course;
import com.tarena.entity.Main;
import com.tarena.entity.Role;
import com.tarena.listener.OnlineUserListener;
import com.tarena.service.IMainService;
import com.tarena.vo.ChartMessage;
import com.tarena.vo.Result;


@Controller
@RequestMapping("/main")
public class MainController {
	//把业务注入进来
	@Resource(name="mainService")
	private IMainService mainService;
	
	/*今日动态*/
	
	/*
	 * 在线用户
	 */
	@RequestMapping(value="/online",method=RequestMethod.GET)
	@ResponseBody
	public int online(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		//1.获取数据
		int online_num = UserController.online.size();
		return online_num;

	}
	
	
	/*
	 * 注册用户
	 */
	@RequestMapping(value="/findAllUserCount",method=RequestMethod.GET)
	@ResponseBody
	public int findAllUserCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		int register_num = this.mainService.getUserCount();

		return register_num;

	}
	
	
	/*
	 * 上传视频
	 */
	@RequestMapping(value="/findAllUploadedVideoCount",method=RequestMethod.GET)
	@ResponseBody
	public int findAllUploadedVideoCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		int uploadvideo_num = this.mainService.uploadVideo();

		return uploadvideo_num;

	}
	
	
	/*
	 * 收藏视频
	 */
	@RequestMapping(value="/findAllCollectionVideoCount",method=RequestMethod.GET)
	@ResponseBody
	public int findAllCollectionVideoCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		int collectvideo_num = this.mainService.collectVideo();

		return collectvideo_num;

	}
	
	
	/*
	 * 购买视频
	 */
	@RequestMapping(value="/findAllPurchasedVideoCount",method=RequestMethod.GET)
	@ResponseBody
	public int findAllPurchasedVideoCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		int buyvideo_num = this.mainService.buyVideo();

		return buyvideo_num;

	}
	
	
	/*
	 * 最新活动
	 */
	@RequestMapping(value="/findAllNewActivityCount",method=RequestMethod.GET)
	@ResponseBody
	public int findAllNewActivityCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		int newactivity_num = this.mainService.newActivity();

		return newactivity_num;

	}
	
	
	/*
	 * 最新评论
	 */
	@RequestMapping(value="/findAllNewCommentCount",method=RequestMethod.GET)
	@ResponseBody
	public int findAllNewCommentCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		int newcomment_num = this.mainService.newComment();

		return newcomment_num;

	}
	
	
	
	
	
	/*用户统计图*/
	@RequestMapping(value="/findUserCount",method=RequestMethod.GET)
	@ResponseBody
	public Result findUserCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		//0.准备数据
		Result result=new Result();
		
		//1.获取数据
		ChartMessage chartMessage=new ChartMessage();
		List<Main> mains=new ArrayList<Main>();
		List<Role> roleNames=this.mainService.findAllRoleName();

		//2.相应业务
		for(Role roleName:roleNames){  
			Main main=new Main();
			main.setValue(this.mainService.findUserCountByRoleName(roleName.getName()));
			main.setColor("#"+chartMessage.getChartMessage());
			main.setHightlight("#"+chartMessage.getChartMessage());
			main.setLabel(roleName.getName());
			
			mains.add(main);
			
		}
		
		result.setData(mains);
		
		return result;
	}
	
	
	/*视频统计图*/
	@RequestMapping(value="/findVideoCount",method=RequestMethod.GET)
	@ResponseBody
	public Result findVideoCount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		//0.准备数据
		Result result=new Result();
		
		//1.获取数据
		ChartMessage chartMessage=new ChartMessage();
		List<Main> mains=new ArrayList<Main>();
		List<Course> courses=this.mainService.findAllCourseName();

		//2.相应业务
		for(Course course:courses){  
			Main main=new Main();
			main.setValue(this.mainService.findVideoCountByCourseName(course.getName()));
			main.setColor("#"+chartMessage.getChartMessage());
			main.setHightlight("#"+chartMessage.getChartMessage());
			main.setLabel(course.getName());
			
			mains.add(main);
			
		}
		
		result.setData(mains);
		
		return result;
	}
	
	
	
	
	
	/*用户增长率 */
	@RequestMapping(value="/userGrowthRate",method=RequestMethod.GET)
	@ResponseBody
	public Main userGrowthRate(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		int userMonthBasis = this.mainService.userMonthBasis();
		int userSameMonth = this.mainService.userSameMonth();
		int userQuarterBasis = this.mainService.userQuarterBasis();
		int userSameQuarter = this.mainService.userSameQuarter();
		
		Main mains = new Main();
		mains.setUserMonthBasis(userMonthBasis);
		mains.setUserSameMonth(userSameMonth);
		mains.setUserQuarterBasis(userQuarterBasis);
		mains.setUserSameQuarter(userSameQuarter);
		
		return mains;
	}
	
	
	/*视频增长率 */
	@RequestMapping(value="/videoGrowthRate",method=RequestMethod.GET)
	@ResponseBody
	public Main videoGrowthRate(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		int videoMonthBasis = this.mainService.videoMonthBasis();
		int videoSameMonth = this.mainService.videoSameMonth();
		int videoQuarterBasis = this.mainService.videoQuarterBasis();
		int videoSameQuarter = this.mainService.videoSameQuarter();
		
		Main mains = new Main();
		mains.setVideoMonthBasis(videoMonthBasis);
		mains.setVideoSameMonth(videoSameMonth);
		mains.setVideoQuarterBasis(videoQuarterBasis);
		mains.setVideoSameQuarter(videoSameQuarter);
		
		return mains;
	}
	
	
	
	
	
	/*视频收藏排行*/
	@RequestMapping(value="/collectionVideoOrder",method=RequestMethod.GET)
	@ResponseBody
	public List<Main> collectionVideoOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		List<Main> mains = this.mainService.collectionVideoOrder();
		
		return mains;
	}
	
	
	/*视频购买排行*/
	@RequestMapping(value="/purchasedVideoOrder",method=RequestMethod.GET)
	@ResponseBody
	public List<Main> purchasedVideoOrder(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		List<Main> mains = this.mainService.purchasedVideoOrder();
		
		return mains;
	}
}
















