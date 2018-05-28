package com.tarena.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.User;
import com.tarena.service.IUserService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name="userService")
	private IUserService userService;
	
	public static List<Object> online = new ArrayList<Object>();
	
	@RequestMapping(value="/login/{name}/{pwd}",method=RequestMethod.GET)
	@ResponseBody
	public Result login(@PathVariable("name") String loginName,
						@PathVariable("pwd") String password,
						HttpSession session){
		Result result=new Result();
		User user=new User();
		user.setLoginName(loginName);
		user.setPassword(password);
		boolean flag=userService.login(user);
		if(flag){
			
			result.setStatus(0);
			session.setAttribute("loginName", loginName);
			online.add(loginName);
		}else{
			result.setStatus(1);
			result.setMessage("用户名或密码错误");
		}
		return result;
	}
	
	@RequestMapping(value="/page/{roleType}",method=RequestMethod.GET)
	@ResponseBody
	public Result findUsersByPage(Page page,@PathVariable("roleType") String roleType){
		Result result=new Result();
		page.setRoleType(roleType);
		page=userService.findUsersByPage(page);
		//System.out.println(page);
		if(page!=null && page.getData()!=null){
			result.setStatus(0);
			result.setData(page);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到角色");
		}
		return result;
	}
	
	@RequestMapping(value="/query",method=RequestMethod.POST)
	@ResponseBody
	public Result findUsersById(HttpServletRequest request){
		Result result=new Result();
		String userId = request.getParameter("userId");
		boolean flag =userService.findUsersById(userId);
		//System.out.println(page);
		if(flag==true){
			result.setStatus(0);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到角色");
		}
		return result;
	}
	
	@RequestMapping(value="/del/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteUser(@PathVariable("id") String userId){
		Result result=null;
		result=userService.deleteUser(userId);
		return result;
	}
	
	@RequestMapping(value="/rolename",method=RequestMethod.GET)
	@ResponseBody
	public Result roleName(){
		Result result=null;
		result=userService.getRoleName();
		return result;
	}
	
	@RequestMapping(value="/new/{name}",method=RequestMethod.POST)
	public Result addUser(User user,
						  HttpServletRequest request,
						  HttpServletResponse response,
						  @PathVariable(name="name") String roleName,
						  @RequestParam(value = "fileName", required = false) MultipartFile file){
		Result result=null;
		result=this.userService.addUser(user, roleName, file,request,response);
		return result;
	}
	
	@RequestMapping(value="/update/{name}",method=RequestMethod.POST)
	public Result updateUser(User user,
						  HttpServletRequest request,
						  HttpServletResponse response,
						  @PathVariable(name="name") String roleName,
						  @RequestParam(value = "fileName", required = false) MultipartFile file){
		Result result=null;
		result=this.userService.updateUser(user, roleName, file,request,response);
		return result;
	}
	
	@RequestMapping(value="/exprotuser",method=RequestMethod.GET)
	public void exprotUser(HttpServletRequest request,HttpServletResponse response){
		try {
			byte[] data=this.userService.exportUser();
			//下载data数据为Excel文件
			response.setContentType("Application/x-msdownload");
			response.setHeader("Content-Disposition","attachment;fileName=allUser.xls");
			response.setContentLength(data.length);
			OutputStream os=response.getOutputStream();
			os.write(data);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 修改密码
	 */
	@RequestMapping(value="/edit_pwd/newPassword/{newPassword}/oldPassword/{oldPassword}",method=RequestMethod.POST)
	@ResponseBody
	public Result edit_pwd(@PathVariable(value="newPassword") String newPassword,
						   @PathVariable(value="oldPassword") String oldPassword,
						   HttpServletRequest request,
						   HttpServletResponse response,
						   HttpSession session){
		//0.准备数据
		Result result = new Result();
		
		//1.获取数据
		session = request.getSession();
		String loginName=(String) session.getAttribute("loginName");
		String password=(String) session.getAttribute("password");

		//2.处理业务
		if (oldPassword.equals(password)){		
			User user = new User();
			user.setPassword(newPassword);
			user.setLoginName(loginName);
			//2.处理修改密码业务
			
			//3.跳转页面
			if (userService.editPassword(user)) {
				result.setStatus(0);
				result.setMessage("修改成功");
			}else {
				result.setStatus(1);
				result.setMessage("修改失败");
			}	
		}else{
			result.setStatus(2);
			result.setMessage("旧密码输入错误，请重新输入");
		}
		return result;
	}
}
