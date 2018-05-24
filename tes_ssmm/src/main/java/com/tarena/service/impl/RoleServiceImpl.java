package com.tarena.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.RoleMapper;
import com.tarena.entity.Role;
import com.tarena.service.IRoleService;
import com.tarena.util.PageUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
@Service("roleService")
public class RoleServiceImpl implements IRoleService {

	@Resource(name="roleMapper")
	private RoleMapper roleMapper;
	@Resource(name="pageUtil")
	private PageUtil pageUtil;
	
	
	public Page findRolesByPage(Page page) {
		page.setRoleKeyword("undefined".equals(page.getRoleKeyword())||page.getRoleKeyword()==null? "%%":"%"+page.getRoleKeyword()+"%");
		//page.setBegin((page.getCurrentPage()-1)*page.getPageSize());
		page.setPageSize(pageUtil.getPageSize());
		int totalCount=roleMapper.getCount(page);
		page.setTotalCount(totalCount);
		int totalPage=page.getTotalPage();
		
		if(page.getCurrentPage()==1){
			page.setPreviousPage(page.getCurrentPage());
		}else{
			page.setPreviousPage(page.getCurrentPage()-1);
		}
		if(page.getCurrentPage()==totalPage){
			page.setNextPage(page.getCurrentPage());
		}else{
			page.setNextPage(page.getCurrentPage()+1);
		}
		
		page.setData(roleMapper.getRoles(page));
		page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));
		return page;
	}

	
	public Result addRole(Role role) {
		role.setId(UUIDUtil.getUUID());
		this.roleMapper.addRole(role);
		Result result=new Result();
		result.setStatus(0);
		result.setMessage("添加成功");
		return result;
	}

	
	public Result deleteRole(String roleId) {
		this.roleMapper.deleteRole(roleId);
		Result result=new Result();
		result.setStatus(0);
		result.setMessage("删除成功");
		return result;
	}

	
	public Result updateRole(Role role) {
		this.roleMapper.updateRole(role);
		Result result=new Result();
		result.setStatus(0);
		result.setMessage("修改角色成功");
		return result;
	}

}
