package com.tarena.service;

import java.util.List;

import com.tarena.entity.Comment;
import com.tarena.vo.Page;

public interface ICommentService {

	public Page findCommentByPage(int currentPage,int pageSize,String commentKeyWord);
	
	public Page findCommentByPage1(int currentPage,int pageSize,String commentKeyWord,String isPass);
	
	public List<Comment> getTimeShowComment(int currentPage,int pageSize,String KeyWord);
	
	public boolean addComment(Comment comment);
	
	public boolean deleteComment(String id);
	
	public boolean updateComment(String comment_id);
	
	public boolean updateCommentIsPass();
	
	public boolean updateCommentNotPass();
}
