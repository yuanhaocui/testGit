package com.tarena.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarena.entity.Comment;
import com.tarena.service.ICommentService;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource(name="commentService")
	private ICommentService commentService;
	private String[] s=new String[]{
			"差","骗","垃圾","他妈","她妈","烂","滥","SB","TM"
	};
	
	@RequestMapping(value="/showtime",method=RequestMethod.POST)
	@ResponseBody
	public Result showtime(@RequestParam(name="currentPage") String currentPage){
		Result result=new Result();
		List<Comment> comments=this.commentService.getTimeShowComment(Integer.parseInt(currentPage), 1, "%%");
		for(Comment comment:comments){
			for(int j=0;j<s.length;j++){
				if(comment.getComment_content().contains(s[j])){
					comment.setComment_content(comment.getComment_content().replace(s[j], "*"));
					//page.getData().remove(i);
				}
			}
		}
		if(comments!=null){
			result.setStatus(0);
			result.setData(comments);
		}else{
			result.setStatus(1);
			result.setMessage("数据刷完");
		}
		return result;
	}
	
	@RequestMapping(value="/commentFindByCourseId",method=RequestMethod.POST)
	@ResponseBody
	public Result commentFindByCourseId(@RequestParam(name="currentPage") String currentPage,
								 @RequestParam(name="courseName") String courseName,
								 @RequestParam(name="value") String value){
		Result result=new Result();
		Page page=new Page();
		String isPass="";
		if(value.length()==5){
			isPass=value.substring(2,3);
			page=this.commentService.findCommentByPage1(Integer.parseInt(currentPage),5, courseName,isPass);
		}else if(value.length()==9){
//			isPass1=isPasss.substring(2,3);
//			isPass2=isPasss.substring(6,7);
			page=this.commentService.findCommentByPage(Integer.parseInt(currentPage),5, courseName);
		}
		for(int i=0;i<page.getData().size();i++){
			Comment comment=(Comment)page.getData().get(i);
			for(int j=0;j<s.length;j++){
				if(comment.getComment_content().contains(s[j])){
					comment.setComment_content(comment.getComment_content().replace(s[j], "*"));
					//page.getData().remove(i);
				}
			}
		}
		if(page!=null){
			result.setStatus(0);
			result.setData(page);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到角色");
		}
//		System.out.println("commentFindByCourseId-->"+result);
		return result;
	}
	
	@RequestMapping(value="/commentFindByPage",method=RequestMethod.POST)
	@ResponseBody
	public Result commentFindByPage(@RequestParam(name="currentPage") String currentPage,
							 @RequestParam(name="commentKeyWord") String keyword,
							 @RequestParam(name="value") String value){
		Result result=new Result();
		Page page=new Page();
		String commentKeyWord=("undefined".equals(keyword) || keyword==null)? "":keyword;
		String isPass="";
//		System.out.println(value);
		if(value.length()==5){
			isPass=value.substring(2,3);
			page=this.commentService.findCommentByPage1(Integer.parseInt(currentPage),5, commentKeyWord,isPass);
		}else if(value.length()==9){
//			isPass1=isPasss.substring(2,3);
//			isPass2=isPasss.substring(6,7);
			page=this.commentService.findCommentByPage(Integer.parseInt(currentPage),5, commentKeyWord);
		}
//		System.out.println(page);
		for(int i=0;i<page.getData().size();i++){
			Comment comment=(Comment)page.getData().get(i);
			for(int j=0;j<s.length;j++){
				if(comment.getComment_content().contains(s[j])){
					comment.setComment_content(comment.getComment_content().replace(s[j], "*"));
					//page.getData().remove(i);
				}
			}
		}
		if(page!=null){
			result.setStatus(0);
			result.setData(page);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到角色");
		}
//		System.out.println("commentFindByPage-->"+result);
		return result;
	}
	
	
	@RequestMapping(value="/isposs",method=RequestMethod.POST)
	@ResponseBody
	public Result isPass(@RequestParam(name="comment_id") String id){
		Result result=new Result();
		boolean flag=this.commentService.updateComment(id);
		if(flag){
			result.setStatus(0);
			result.setMessage("修改通过");
		}else{
			result.setStatus(1);
			result.setMessage("修改失败");
		}
		return result;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	@ResponseBody
	public Result deleteComment(@RequestParam(name="comment_id") String id){
		Result result=new Result();
		boolean flag=this.commentService.deleteComment(id);
		if(flag){
			result.setStatus(0);
			result.setMessage("拒绝成功");
		}else{
			result.setStatus(1);
			result.setMessage("拒绝失败");
		}
		return result;
	}
	
	@RequestMapping(value="/ispass_all",method=RequestMethod.POST)
	@ResponseBody
	public Result ispass_all(@RequestParam(name="type") String type){
		Result result=new Result();
		boolean flag=false;
		if("全部通过".equals(type)){
			flag=this.commentService.updateCommentIsPass();
			if(flag){
				result.setStatus(0);
				result.setMessage("全部通过成功");
			}else{
				result.setStatus(1);
				result.setMessage("全部通过失败");
			}
		}else if("全部拒绝".equals(type)){
			flag=this.commentService.updateCommentNotPass();
			if(flag){
				result.setStatus(0);
				result.setMessage("全部拒绝成功");
			}else{
				result.setStatus(1);
				result.setMessage("全部拒绝失败");
			}
		}
		
		return result;
	}
	
}
