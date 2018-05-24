package com.tarena.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import com.tarena.dao.VideoMapper;
import com.tarena.entity.Course;
import com.tarena.entity.Message;
import com.tarena.entity.User;
import com.tarena.entity.Video;
import com.tarena.service.IVideoService;
import com.tarena.util.CommonValue;
import com.tarena.util.PageUtil;
import com.tarena.util.PrintWriterUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.util.UploadUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;
@Service("videoService")
public class VideoServiceImpl implements IVideoService {

	@Resource(name="videoMapper")
	private VideoMapper videoMapper;
	@Resource(name="pageUtil")
	private PageUtil pageUtil;
	
	
	/**
	 * 新视频
	 */
	
	public List<Message> findVideoByChecked() {
		
		return this.videoMapper.findVideoByChecked();
		
	}
	
	public Page findVideosByPage(Page page) {
		page.setVideoKeyword("undefined".equals(page.getVideoKeyword())||page.getVideoKeyword()==null? "%%":"%"+page.getVideoKeyword().toLowerCase()+"%");
		page.setPageSize(4);
		System.out.println(page);
			int totalCount=videoMapper.getCount(page);
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
			page.setData(videoMapper.getVideos(page));
			page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));
		
		return page;
	}
	
	public Result addVideo(Video video,
						  MultipartFile file,
						  HttpServletRequest request,
						  HttpServletResponse response) {
		Result result=new Result();
		String imageFileName=null;
		//处理上传文件
		String originalFileName=null;
		String activityId=UUIDUtil.getUUID();
//		activity.setIspass("否");
//		activity.setId(activityId);
		String realPath=request.getServletContext().getRealPath("/images");
		if(file==null || file.isEmpty()){
//			activity.setPicture("default.png");
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
//				activity.setPicture(imageFileName);
			}
		}
		
		try {
//			activity.setUserName(this.activityMapper.getUserId(activity.getUserName()));
//			activity.setCourseName(this.activityMapper.getCourseId(activity.getCourseName()));
//			//System.out.println(activity);
//			this.activityMapper.addActivity(activity);
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
	
	public Result updateVideo(Video video, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Result result=new Result();
		String imageFileName=null;
		//处理上传文件
		String originalFileName=null;
		String realPath=request.getServletContext().getRealPath("/images");
		
		if(file==null || file.isEmpty()){
			
		}else{
////			File file_original=new File(realPath+File.pathSeparator+activity.getPicture());
//			if(file_original.exists()){
//				file_original.delete();
//			}
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
//				boolean flag=UploadUtil.uploadImage(file, activity.getId(), true, 64, realPath);
//				if(!flag){
//					PrintWriterUtil.printMessageToClient(response, "图片上传失败");
//					System.out.println(4);
//					return null;
//				}
				originalFileName=file.getOriginalFilename();
				String originalExtendName=originalFileName.substring(originalFileName.lastIndexOf(".")+1);
//				imageFileName=activity.getId()+"."+originalExtendName;
//				activity.setPicture(imageFileName);
			}
		}
		try {
//			activity.setUserName(this.activityMapper.getUserId(activity.getUserName()));
//			activity.setCourseName(this.activityMapper.getCourseId(activity.getCourseName()));
//			this.activityMapper.updateActivity(activity);
			PrintWriterUtil.printMessageToClient(response, "用户修改成功");
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
	
	public Result findVideoById(String videoId) {
		System.out.println(videoId);
		Video video=this.videoMapper.findVideoById(videoId);
		Result result=new Result();
		if(video!=null){
			result.setStatus(0);
			result.setData(video);
		}else{
			result.setStatus(1);
			result.setMessage("没有查到该视频");
		}
		return result;
	}
	
	public boolean deleteVideo(String videoId) {
		boolean flag=false;
		int rowAffect=this.videoMapper.deleteVideoById(videoId);
		if (rowAffect==1) {
			flag = true;
		}
		return flag;
	}
	
	public List<Course> showCourseName() {
		
		return this.videoMapper.showCourseName();
	}
	
	public List<User> showUserName() {
		
		return this.videoMapper.showUserName();
	}
	
	public boolean updateVideo(Video video) {
		boolean flag=false;
		String courseId=this.videoMapper.getCourseId(video.getCourseName());
		String userId=this.videoMapper.getUserId(video.getUserNickname());
		video.setCourseName(courseId);
		video.setUserNickname(userId);
		int rowAffect=this.videoMapper.updateVideo(video);
		if(rowAffect==1){
			flag=true;
		}
		return flag;
	}
	
	public boolean updateState(String id) {
		boolean flag=false;
		int rowAffect=this.videoMapper.updateState(id);
		if(rowAffect==1){
			flag=true;
		}
		return flag;
	}
	
	public int countNumber() {
		return this.videoMapper.countNumber();
	}

}
