package com.tarena.controller;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.Activity;
import com.tarena.entity.Course;
import com.tarena.service.IActivityService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("/activity")
public class ActivityController {

	@Resource(name="activityService")
	private IActivityService activityService;
	
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public Result findActivitysByPage(Page page){
		Result result=new Result();
		page=activityService.findActivitysByPage(page);
		if(page!=null && page.getData()!=null){
			result.setStatus(0);
			result.setData(page);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到活动");
		}
		return result;
	}
	
	@RequestMapping(value="/show_courseName",method=RequestMethod.GET)
	@ResponseBody
	public Result findCourseName(){
		Result result=new Result();
		List<Course> courses=activityService.findCourseName();
		//System.out.println(courses);
		if(courses!=null && courses.size()>0){
			result.setStatus(0);
			result.setData(courses);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到活动");
		}
		return result;
	}
	
	@RequestMapping(value="/del/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteUser(@PathVariable("id") String id){
		Result result=null;
		result=activityService.deleteActivity(id);
		return result;
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result addActivity(Activity activity,
						  @RequestParam(name="time") String date,
						  HttpServletRequest request,
						  HttpServletResponse response,
						  @RequestParam(value = "fileName", required = false) MultipartFile file){
		Result result=null;
		activity.setDate(Timestamp.valueOf(date+" 00:00:00"));
		result=this.activityService.addActivity(activity, file,request,response);
		return result;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void updateUser(Activity activity,
						  @RequestParam(name="time") String date,
						  HttpServletRequest request,
						  HttpServletResponse response,
						  @RequestParam(value = "fileName", required = false) MultipartFile file){
		activity.setDate(Timestamp.valueOf(date+" 00:00:00"));
		this.activityService.updateActivity(activity,file,request,response);
	}
	
	@RequestMapping(value="/ispass",method=RequestMethod.POST)
	@ResponseBody
	public Result ispass(@RequestParam(name="activity_id") String activity_id){
		Result result=new Result();
		boolean flag=this.activityService.isPass(activity_id);
		if(flag){
			result.setStatus(0);
			result.setMessage("修改通过");
		}else{
			result.setStatus(1);
			result.setMessage("修改失败");
		}
		return result;
	}
	
	@RequestMapping(value="/delete_act",method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteActivity(@RequestParam(name="activity_id") String id){
		Result result=null;
		result=this.activityService.deleteActivity(id);
		result.setStatus(0);
		result.setMessage("拒绝成功");
		return result;
	}
}
