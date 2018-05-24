package com.tarena.controller;

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

import com.tarena.entity.Course;
import com.tarena.service.ICourseService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("/course")
public class CourseController {

	@Resource(name="courseService")
	private ICourseService courseService;
	
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public Result findUsersByPage(Page page){
		Result result=new Result();
		page=courseService.findCoursesByPage(page);
		if(page!=null && page.getData()!=null){
			result.setStatus(0);
			result.setData(page);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到角色");
		}
		return result;
	}
	
	@RequestMapping(value="/del/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteUser(@PathVariable("id") String id){
		Result result=null;
		result=courseService.deleteCourse(id);
		return result;
	}
	
	
	@RequestMapping(value="/new",method=RequestMethod.POST)
	public Result addUser(Course course,
						  HttpServletRequest request,
						  HttpServletResponse response,
						  @RequestParam(value = "fileName", required = false) MultipartFile file){
		Result result=null;
		result=this.courseService.addCourse(course, file,request,response);
		return result;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Result updateUser(Course course,
						  HttpServletRequest request,
						  HttpServletResponse response,
						  @RequestParam(value = "fileName", required = false) MultipartFile file){
		Result result=null;
		result=this.courseService.updateCourse(course,file,request,response);
		return result;
	}
	
}
