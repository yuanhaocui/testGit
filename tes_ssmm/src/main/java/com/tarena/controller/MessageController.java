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

import com.tarena.entity.Message;
import com.tarena.service.IActivityService;
import com.tarena.service.IVideoService;
import com.tarena.vo.Result;

@Controller
@RequestMapping("/message")
public class MessageController {
	//把业务注入进来
	@Resource(name="videoService")
	private IVideoService videoService;
	
	@Resource(name="activityService")
	private IActivityService activityService;
	
	/*
	 * 用户登录
	 */
	@RequestMapping(value="/findMessage",method=RequestMethod.GET)
	@ResponseBody
	public Result findMessage(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		//0.准备数据
		Result result=new Result();
		
		//1.获取数据
		List<Message> activitymessages = this.activityService.findActivityByChecked();
		List<Message> videomessages = this.videoService.findVideoByChecked();
		
		//2.做相应的业务
		List<Message> messages = new ArrayList<Message>();
		for (int i = 0; i < activitymessages.size(); i++) {
			Message message = activitymessages.get(i);
			message.setTitles("新活动");
			message.setCheckedName("审核活动");
			messages.add(message);
			//System.out.println(activitymessages.size());
		}
		for (int i = 0; i < videomessages.size(); i++) {
			Message message = videomessages.get(i);
			message.setTitles("新视频");
			message.setCheckedName("审核视频");
			messages.add(message);
		}
		if(messages!=null){
			result.setStatus(0);
			result.setData(messages);
		} else {
			result.setStatus(1);
			result.setMessage("没有新消息");
		}

		return result;

	}
	
	@RequestMapping(value="/countNumber",method=RequestMethod.GET)
	@ResponseBody
	public Result countNumber(){
		Result result=new Result();
		int number_activity=this.activityService.countNumber();
		int number_video=this.videoService.countNumber();
		int number=number_activity+number_video;
		if(number>0){
			result.setStatus(0);
			result.setMessage(number+"");
		}else{
			result.setStatus(1);
			result.setMessage("没有未审核的视频或活动");
		}
		return result;

	}
	
}
