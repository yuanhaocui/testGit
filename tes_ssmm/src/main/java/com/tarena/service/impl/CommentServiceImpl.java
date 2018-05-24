package com.tarena.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tarena.dao.CommentMapper;
import com.tarena.entity.Comment;
import com.tarena.service.ICommentService;
import com.tarena.util.PageUtil;
import com.tarena.vo.Page;
@Service("commentService")
public class CommentServiceImpl implements ICommentService {

	@Resource(name="commentMapper")
	private CommentMapper commentMapper;
	@Resource(name="pageUtil")
	private PageUtil pageUtil;
	
	
	public Page findCommentByPage(int currentPage, int pageSize, String commentKeyWord) {
		Page page=new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setCommentKeyword("%"+commentKeyWord+"%");
		int totalCount=this.commentMapper.getCount("%"+commentKeyWord+"%");
//		System.out.println("page-->"+totalCount);
		page.setTotalCount(totalCount);
		int totalPage=(totalCount%pageSize==0)? (totalCount/pageSize):(totalCount/pageSize)+1;
		page.setTotalPage(totalPage);
		if(currentPage==1){
			page.setPreviousPage(currentPage);
		}else{
			page.setPreviousPage(currentPage-1);
		}
		if(currentPage==totalCount){
			page.setNextPage(currentPage);
		}else{
			page.setNextPage(currentPage+1);
		}
//		System.out.println("page="+page);
		page.setData(this.commentMapper.findCommentByPage(page));
//		System.out.println("page_old="+page);
		page.setaNum(this.pageUtil.getFenYe_a_Num(currentPage, pageSize, totalCount, totalPage));
		return page;
	}

	
	public Page findCommentByPage1(int currentPage, int pageSize, String commentKeyWord, String isPass) {
		Page page=new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setCommentKeyword("%"+commentKeyWord+"%");
		page.setIsPass(isPass);
		int totalCount=this.commentMapper.getCountCourseName(page);
//		System.out.println("page1-->"+totalCount);
		page.setTotalCount(totalCount);
		int totalPage=(totalCount%pageSize==0)? (totalCount/pageSize):(totalCount/pageSize)+1;
		page.setTotalPage(totalPage);
		if(currentPage==1){
			page.setPreviousPage(currentPage);
		}else{
			page.setPreviousPage(currentPage-1);
		}
		if(currentPage==totalCount){
			page.setNextPage(currentPage);
		}else{
			page.setNextPage(currentPage+1);
		}
		page.setData(this.commentMapper.findCommentByCourseName(page));
//		System.out.println("111"+page.getData()+"   ispass"+page.getIsPass()+"   commentKeyword"+page.getCommentKeyword());
		page.setaNum(this.pageUtil.getFenYe_a_Num(currentPage, pageSize, totalCount, totalPage));
		return page;
	}

	
	public List<Comment> getTimeShowComment(int currentPage, int pageSize, String KeyWord) {
		Page page=new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		return this.commentMapper.getTimeShowComment(page);
	}

	
	public boolean addComment(Comment comment) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean deleteComment(String id) {
		boolean flag=false;
		int rowAffect=this.commentMapper.deleteComment(id);
		if(rowAffect==1){
			flag=true;
		}
		return flag;
	}

	
	public boolean updateComment(String comment_id) {
		boolean flag=false;
		int rowAffect=this.commentMapper.updateComment(comment_id);
		if(rowAffect==1){
			flag=true;
		}
		return flag;
	}

	
	public boolean updateCommentIsPass() {
		boolean flag=false;
		int rowAffect=this.commentMapper.updateCommentIsPass();
		if(rowAffect>=1){
			flag=true;
		}
		return flag;
	}

	
	public boolean updateCommentNotPass() {
		boolean flag=false;
		int rowAffect=this.commentMapper.updateCommentNotPass();
		if(rowAffect>=1){
			flag=true;
		}
		return flag;
	}

}
