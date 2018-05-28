package com.tarena.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tarena.dao.CourseMapper;
import com.tarena.entity.Course;
import com.tarena.service.ICourseService;
import com.tarena.util.CommonValue;
import com.tarena.util.PageUtil;
import com.tarena.util.PrintWriterUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.util.UploadUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Service("courseService")
public class CourseServiceImpl implements ICourseService {

	@Resource(name = "courseMapper")
	private CourseMapper courseMapper;
	@Resource(name = "pageUtil")
	private PageUtil pageUtil;

	public Page findCoursesByPage(Page page) {
		page.setCourseKeyword("undefined".equals(page.getCourseKeyword()) || page.getCourseKeyword() == null ? "%%"
				: "%" + page.getCourseKeyword() + "%");
		page.setPageSize(pageUtil.getPageSize());

		int totalCount = courseMapper.getCount(page);
		page.setTotalCount(totalCount);
		int totalPage = page.getTotalPage();

		if (page.getCurrentPage() == 1) {
			page.setPreviousPage(page.getCurrentPage());
		} else {
			page.setPreviousPage(page.getCurrentPage() - 1);
		}
		if (page.getCurrentPage() == totalPage) {
			page.setNextPage(page.getCurrentPage());
		} else {
			page.setNextPage(page.getCurrentPage() + 1);
		}
		List<Course> courses = courseMapper.getCourses(page);
		for (Course course : courses) {
			course.setSemsterString();
			course.setDurantionString();
			course.setTypeString();
		}
		page.setData(courses);
		page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));
		return page;
	}

	public Result deleteCourse(String courseId) {
		this.courseMapper.deleteCourse(courseId);
		Result result = new Result();
		result.setStatus(0);
		result.setMessage("删除成功");
		return result;
	}

	public Result addCourse(Course course, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		System.out.println(course);
		// 处理上传文件
		String originalFileName = null;
		String courseId = UUIDUtil.getUUID();
		course.setId(courseId);
		String realPath = request.getServletContext().getRealPath("/images");
		if (file == null || file.isEmpty()) {
			course.setPicture("default.png");
		} else {
			String contextType = file.getContentType();
			long size = file.getSize();
			if (!CommonValue.contentTypes.contains(contextType)) {
				PrintWriterUtil.printMessageToClient(response, "图片类型不匹配");
				System.out.println(2);
				return null;
			} else if (size > 4194304) {
				PrintWriterUtil.printMessageToClient(response, "图片文件太大");
				System.out.println(3);
				return null;
			} else {
				// 开始上传文件
				boolean flag = UploadUtil.uploadImage(file, courseId, true, 64, realPath);
				if (!flag) {
					PrintWriterUtil.printMessageToClient(response, "图片上传失败");
					System.out.println(4);
					return null;
				}
			}
		}
		String imageFileName = null;
		try {
			// 调用添加课程数据库操作
			originalFileName = file.getOriginalFilename();
			String originalExtendName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
			imageFileName = courseId + "." + originalExtendName;
			course.setPicture(imageFileName);
			this.courseMapper.addCourse(course);
			PrintWriterUtil.printMessageToClient(response, "课程添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			File files = new File(realPath + File.pathSeparator + imageFileName);
			if (files.exists()) {
				files.delete();
			}
			return null;
		}
		return result;
	}

	public Result updateCourse(Course course, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		course.setDurantionString();
		course.setSemsterString();
		course.setTypeString();
		System.out.println(course);
		String imageFileName = null;
		// 处理上传文件
		String originalFileName = null;
		String realPath = request.getServletContext().getRealPath("/images");

		if (file == null || file.isEmpty()) {

		} else {
			File file_original = new File(realPath + File.pathSeparator + course.getPicture());
			if (file_original.exists()) {
				file_original.delete();
			}
			String contextType = file.getContentType();
			long size = file.getSize();
			if (!CommonValue.contentTypes.contains(contextType)) {
				PrintWriterUtil.printMessageToClient(response, "图片类型不匹配");
				System.out.println(2);
				return null;
			} else if (size > 4194304) {
				PrintWriterUtil.printMessageToClient(response, "图片文件太大");
				System.out.println(3);
				return null;
			} else {
				// 开始上传文件
				boolean flag = UploadUtil.uploadImage(file, course.getId(), true, 64, realPath);
				if (!flag) {
					PrintWriterUtil.printMessageToClient(response, "图片上传失败");
					System.out.println(4);
					return null;
				}
				originalFileName = file.getOriginalFilename();
				String originalExtendName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
				imageFileName = course.getId() + "." + originalExtendName;
				course.setPicture(imageFileName);
			}
		}
		try {
			this.courseMapper.updateCourse(course);
			PrintWriterUtil.printMessageToClient(response, "用户课程成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			File files = new File(realPath + File.pathSeparator + imageFileName);
			if (files.exists()) {
				files.delete();
			}
			return null;
		}
		return null;
	}

	@Override
	public Page findMyCoursesByPage(Page page, String userId) {
		page.setCourseKeyword("undefined".equals(page.getCourseKeyword()) || page.getCourseKeyword() == null ? "%%"
				: "%" + page.getCourseKeyword() + "%");
		page.setPageSize(pageUtil.getPageSize());
		page.setUserKeyword(userId);
		int totalCount = courseMapper.getMyCourseCount(page);
		page.setTotalCount(totalCount);
		int totalPage = page.getTotalPage();

		if (page.getCurrentPage() == 1) {
			page.setPreviousPage(page.getCurrentPage());
		} else {
			page.setPreviousPage(page.getCurrentPage() - 1);
		}
		if (page.getCurrentPage() == totalPage) {
			page.setNextPage(page.getCurrentPage());
		} else {
			page.setNextPage(page.getCurrentPage() + 1);
		}
		List<Course> courses = courseMapper.getMyCourses(page);
		for (Course course : courses) {
			course.setSemsterString();
			course.setDurantionString();
			course.setTypeString();
		}
		page.setData(courses);
		page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));

		return page;

	}

	@Override
	public Result deleteMyCourse(String id) {
		this.courseMapper.deleteMyCourse(id);
		Result result = new Result();
		result.setStatus(0);
		result.setMessage("删除成功");
		return result;
	}

	@Override
	public Result addMyCourses(Course course) {
		this.courseMapper.addMyCourses(course);
		Result result = new Result();
		result.setStatus(0);
		result.setMessage("选课成功");
		return result;
	}

	@Override
	public Result getAllCourse() {
		List<Course> courses = this.courseMapper.getAllCourses();
		for (Course course : courses) {
			course.setSemsterString();
			course.setDurantionString();
			course.setTypeString();
		}
		Result result = new Result();
		if (courses != null) {
			result.setData(courses);
			result.setStatus(0);
			result.setMessage("查询成功");
		}
		return result;
	}

	@Override
	public Result getCourseById(Course course) {
		Course course1 = this.courseMapper.getCourseById(course);
		course1.setSemsterString();
		course1.setDurantionString();
		course1.setTypeString();
		Result result = new Result();
		result.setStatus(0);
		result.setData(course1);
		result.setMessage("查询成功");
		return result;
	}

}
