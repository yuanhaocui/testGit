package com.tarena.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tarena.entity.User;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface IUserService {

	public boolean login(User user);
	
	public Page findUsersByPage(Page page);
	
	public Result deleteUser(String userId);
	
	public Result getRoleName();
	
	public Result addUser(User user,
						  String roleName,
						  MultipartFile file,
						  HttpServletRequest request,
						  HttpServletResponse response);
	
	public Result updateUser(User user,
			  String roleName,
			  MultipartFile file,
			  HttpServletRequest request,
			  HttpServletResponse response);
	
	public byte[] exportUser();
	/**
	 * 修改密码
	 */
	public boolean editPassword(User user);
	
}
