package com.tarena.dao;

import java.util.List;

import com.tarena.entity.Comment;
import com.tarena.vo.Page;


public interface CommentMapper {

	//根据用户id删除评论
	public int deleteCommentByUserId(String userId);
	//根据视频的id删除评论
	public int deleteCommentByVideoId(String videoId);
	//推送刷数据
	public List<Comment> getTimeShowComment(Page page);
	
	public int getCount(String commentKeyword);
	
	public int getCountCourseName(Page page);
	
	public List<Comment> findCommentByPage(Page page);
	
	public List<Comment> findCommentByCourseName(Page page);
	
	public int updateComment(String comment_id);
	
	public int deleteComment(String comment_id);
	
	public int updateCommentIsPass();
	
	public int updateCommentNotPass();
}
