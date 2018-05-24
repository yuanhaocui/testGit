package com.tarena.controller;

import java.sql.Timestamp;
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
import com.tarena.entity.User;
import com.tarena.entity.Video;
import com.tarena.service.IVideoService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("/video")
public class VideoController {

	@Resource(name="videoService")
	private IVideoService videoService;
	
	@RequestMapping(value="/deleteVideo/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteVideo(@PathVariable(name="id") String video_id){
		Result result=new Result();
		System.out.println("id--->"+video_id);
		boolean flag=this.videoService.deleteVideo(video_id);
		System.out.println("是否进入啊------"+flag);
		if (flag) {
			result.setStatus(0);
			result.setMessage("删除成功");
			
		}else {
			result.setStatus(1);
			result.setMessage("删除失败");
		}
		return result;

	}
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public Result findVideosByPage(Page page){
		Result result=new Result();
		page=videoService.findVideosByPage(page);
		if(page!=null && page.getData()!=null){
			result.setStatus(0);
			result.setData(page);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到角色");
		}
		return result;
	}
	
	@RequestMapping(value="/findById",method=RequestMethod.GET)
	@ResponseBody
	public Result fandVideoById(Video video){
		Result result=null;
		result=videoService.findVideoById(video.getId());
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
//		result=this.activityService.addActivity(activity, file,request,response);
		return result;
	}
	

	@RequestMapping(value="/coursename",method=RequestMethod.GET)
	@ResponseBody
	public Result showCourseName(){
		Result result=new Result();
		List<Course> users=videoService.showCourseName();
		if(users!=null){
			result.setStatus(0);
			result.setData(users);
		}else{
			result.setStatus(1);
			result.setMessage("没有课程名字");
		}
		return result;
	}
	
	@RequestMapping(value="/username",method=RequestMethod.GET)
	@ResponseBody
	public Result showUserName(){
		Result result=new Result();
		List<User> users=videoService.showUserName();
		if(users!=null){
			result.setStatus(0);
			result.setData(users);
		}else{
			result.setStatus(1);
			result.setMessage("没有课程名字");
		}
		return result;
	}
	
	@RequestMapping(value="/updateVideo/{time}",method=RequestMethod.POST)
	@ResponseBody
	public Result updateVideo(Video video,
							@PathVariable(name="time") String date){
		Result result=new Result();	
		video.setOntime(Timestamp.valueOf(date+" 00:00:00"));
		boolean flag=videoService.updateVideo(video);
		if(flag){
			result.setStatus(0);
			result.setMessage("修改成功");
		}else{
			result.setStatus(1);
			result.setMessage("修改失败");
		}
		return result;
	}
	
	@RequestMapping(value="/changestate/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Result updateVideo(@PathVariable(name="id") String id){
		Result result=new Result();	
		boolean flag=videoService.updateState(id);
		if(flag){
			result.setStatus(0);
			result.setMessage("通过");
		}else{
			result.setStatus(1);
			result.setMessage("修改失败");
		}
		return result;
	}
}
