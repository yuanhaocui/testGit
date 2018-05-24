package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Role;
import com.tarena.vo.Page;

public interface RoleMapper {

	public int getCount(Page page);
	
	public List<Role> getRoles(Page page);
	
	public int addRole(Role role);
	
	public int deleteRole(String roleId);
	
	public int updateRole(Role role);
	//查询指定角色名字在数据库中是否存在
	public int findRoleName(String roleName);
	
	public List<Role> findAllRoleName();
}
