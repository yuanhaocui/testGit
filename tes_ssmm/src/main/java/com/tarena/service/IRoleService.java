package com.tarena.service;

import com.tarena.entity.Role;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

public interface IRoleService {

	public Page findRolesByPage(Page page);
	
	public Result addRole(Role role);
	
	public Result deleteRole(String roleId);
	
	public Result updateRole(Role role);
}
