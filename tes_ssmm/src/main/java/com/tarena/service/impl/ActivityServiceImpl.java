package com.tarena.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tarena.dao.ActivityMapper;
import com.tarena.entity.Activity;
import com.tarena.entity.Course;
import com.tarena.entity.Message;
import com.tarena.service.IActivityService;
import com.tarena.util.CommonValue;
import com.tarena.util.PageUtil;
import com.tarena.util.PrintWriterUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.util.UploadUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
@Service("activityService")
public class ActivityServiceImpl implements IActivityService {

	@Resource(name="activityMapper")
	private ActivityMapper activityMapper;
	@Resource(name="pageUtil")
	private PageUtil pageUtil;
	/**
	 * 新活动
	 */
	 
	public List<Message> findActivityByChecked(){
		
		return this.activityMapper.findActivityByChecked();
		
	}
	 
	public Page findActivitysByPage(Page page) {
		page.setActivityKeyword("undefined".equals(page.getActivityKeyword())||page.getActivityKeyword()==null? "%%":"%"+page.getActivityKeyword()+"%");
		page.setPageSize(pageUtil.getPageSize());
			int totalCount=activityMapper.getCount(page);
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
			page.setData(activityMapper.getActivitys(page));
			page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));
		
		return page;
	}
	 
	public Result deleteActivity(String activityId) {
		this.activityMapper.deleteActivity(activityId);
		Result result=new Result();
		result.setStatus(0);
		result.setMessage("删除成功");
		return result;
	}
	 
	public Result addActivity(Activity activity,
						  MultipartFile file,
						  HttpServletRequest request,
						  HttpServletResponse response) {
		Result result=new Result();
		String imageFileName=null;
		//处理上传文件
		String originalFileName=null;
		String activityId=UUIDUtil.getUUID();
		activity.setIspass("否");
		activity.setId(activityId);
		String realPath=request.getServletContext().getRealPath("/images");
		if(file==null || file.isEmpty()){
			activity.setPicture("default.png");
		}else{
			String contextType=file.getContentType();
			long size=file.getSize();
			if(!CommonValue.contentTypes.contains(contextType)){
				PrintWriterUtil.printMessageToClient(response, "图片类型不匹配");
				System.out.println(2);
				return null;
			}else if(size>4194304){
				PrintWriterUtil.printMessageToClient(response, "图片文件太大");
				System.out.println(3);
				return null;
			}else{
				//开始上传文件
				boolean flag=UploadUtil.uploadImage(file, activityId, true, 64, realPath);
				if(!flag){
					PrintWriterUtil.printMessageToClient(response, "图片上传失败");
					System.out.println(4);
					return null;
				}
				originalFileName=file.getOriginalFilename();
				String originalExtendName=originalFileName.substring(originalFileName.lastIndexOf(".")+1);
				imageFileName=activityId+"."+originalExtendName;
				activity.setPicture(imageFileName);
			}
		}
		
		try {
			activity.setUserName(this.activityMapper.getUserId(activity.getUserName()));
			activity.setCourseName(this.activityMapper.getCourseId(activity.getCourseName()));
			System.out.println(activity);
			this.activityMapper.addActivity(activity);
			PrintWriterUtil.printMessageToClient(response, "活动添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			File files=new File(realPath+File.pathSeparator+imageFileName);
			if(files.exists()){
				files.delete();
			}
			return null;
		}
		return result;
	}
	 
	public Result updateActivity(Activity activity, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Result result=new Result();
		String imageFileName=null;
		//处理上传文件
		String originalFileName=null;
		String realPath=request.getServletContext().getRealPath("/images");
		
		if(file==null || file.isEmpty()){
			
		}else{
			File file_original=new File(realPath+File.pathSeparator+activity.getPicture());
			if(file_original.exists()){
				file_original.delete();
			}
			String contextType=file.getContentType();
			long size=file.getSize();
			if(!CommonValue.contentTypes.contains(contextType)){
				PrintWriterUtil.printMessageToClient(response, "图片类型不匹配");
				System.out.println(2);
				return null;
			}else if(size>4194304){
				PrintWriterUtil.printMessageToClient(response, "图片文件太大");
				System.out.println(3);
				return null;
			}else{
				//开始上传文件
				boolean flag=UploadUtil.uploadImage(file, activity.getId(), true, 64, realPath);
				if(!flag){
					PrintWriterUtil.printMessageToClient(response, "图片上传失败");
					System.out.println(4);
					return null;
				}
				originalFileName=file.getOriginalFilename();
				String originalExtendName=originalFileName.substring(originalFileName.lastIndexOf(".")+1);
				imageFileName=activity.getId()+"."+originalExtendName;
				activity.setPicture(imageFileName);
			}
		}
		try {
			activity.setUserName(this.activityMapper.getUserId(activity.getUserName()));
			activity.setCourseName(this.activityMapper.getCourseId(activity.getCourseName()));
			this.activityMapper.updateActivity(activity);
			PrintWriterUtil.printMessageToClient(response, "活动修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			File files=new File(realPath+File.pathSeparator+imageFileName);
			if(files.exists()){
				files.delete();
			}
			return null;
		}
		return result;
	}
	 
	public List<Course> findCourseName() {
		
		return this.activityMapper.findCourseName();
	}
	 
	public boolean isPass(String activity_id) {
		boolean flag=false;
		int rowAffect=this.activityMapper.isPass(activity_id);
		if(rowAffect==1){
			flag=true;
		}
		return flag;
	}
	 
	public int countNumber() {
		return this.activityMapper.countNumber();
	}

}
