package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Role;
import com.tarena.entity.User;
import com.tarena.entity.UserRole;
import com.tarena.vo.Page;

public interface UserMapper {

	public String login(User user);
	
	public int getCount(Page page);
	
	public int getCount_roleType(Page page);
	
	public List<User> getUsers(Page page);
	
	public List<User> getUsers_roleType(Page page);
	
	public int deleteUser(String userId);
	
	public List<Role> getRoleName();
	
	public int addUser(User user);
	
	public int deleteUserRole(String userId);
	//删除指定用户id的视频和缓冲
	public int deleteHistoryCacheByUserId(String userId);
	
	public String getRoleIdByRoleName(String roleName);
	
	public int addUserRole(UserRole ur);
	
	public int updateUser(User user);
	
	public List<User> findAllUsers();
	
	public int findUserCountByRoleName(String roleName);

	/*
	 * 修改密码
	 */
	
	public int editPassword(User user);

	public User findUserById(String userId);
}
