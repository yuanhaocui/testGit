package com.tarena.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarena.entity.Role;
import com.tarena.service.IRoleService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Resource(name="roleService")
	private IRoleService roleService;
	
//	分页查询角色信息
	@RequestMapping(value="/page",method=RequestMethod.GET)
	@ResponseBody
	public Result findRoleByPage(Page page){
		Result result=new Result();
		page=roleService.findRolesByPage(page);
		if(page!=null && page.getData()!=null){
			result.setStatus(0);
			result.setData(page);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到角色");
		}
		return result;
	}
	
	@RequestMapping(value="/new",method=RequestMethod.POST)
	@ResponseBody
//	新增角色
	public Result addRole(Role role){
		Result result=null;
		result=roleService.addRole(role);
		return result;
	}
	
//	删除角色
	@RequestMapping(value="/del/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteRole(@PathVariable("id") String roleId){
		Result result=null;
		result=roleService.deleteRole(roleId);
		return result;
	}
	
//	更改角色信息
	@RequestMapping(value="/update/{id}/{name}",method=RequestMethod.PUT)
	@ResponseBody
	public Result updateRole(@PathVariable("id") String roleId,
							 @PathVariable("name") String roleName){
		Result result=null;
		Role role=new Role();
		role.setId(roleId);
		role.setName(roleName);
		result=roleService.updateRole(role);
		return result;
	}
}
