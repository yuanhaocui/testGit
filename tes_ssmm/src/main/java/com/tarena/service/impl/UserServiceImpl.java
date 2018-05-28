package com.tarena.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tarena.dao.ActivityMapper;
import com.tarena.dao.CommentMapper;
import com.tarena.dao.ParticipationMapper;
import com.tarena.dao.UserMapper;
import com.tarena.dao.VideoMapper;
import com.tarena.entity.Role;
import com.tarena.entity.User;
import com.tarena.entity.UserRole;
import com.tarena.service.IUserService;
import com.tarena.util.CommonValue;
import com.tarena.util.ExcelUtil;
import com.tarena.util.PageUtil;
import com.tarena.util.PrintWriterUtil;
import com.tarena.util.UUIDUtil;
import com.tarena.util.UploadUtil;
import com.tarena.vo.Page;
import com.tarena.vo.Result;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource(name = "userMapper")
	private UserMapper userMapper;
	@Resource(name = "pageUtil")
	private PageUtil pageUtil;
	@Resource(name = "participationMapper")
	private ParticipationMapper participationMapper;
	@Resource(name = "activityMapper")
	private ActivityMapper activityMapper;
	@Resource(name = "commentMapper")
	private CommentMapper commentMapper;
	@Resource(name = "videoMapper")
	private VideoMapper videoMapper;

	public boolean login(User user) {
		boolean flag = false;
		String userId = this.userMapper.login(user);
		if (userId != null) {
			flag = true;
		}
		return flag;
	}

	public Page findUsersByPage(Page page) {
		page.setUserKeyword("undefined".equals(page.getUserKeyword()) || page.getUserKeyword() == null ? "%%"
				: "%" + page.getUserKeyword() + "%");
		page.setPageSize(pageUtil.getPageSize());
		if ("%all%".equals(page.getRoleType())) {
			int totalCount = userMapper.getCount(page);
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
			page.setData(userMapper.getUsers(page));
			page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));
		} else {
			int totalCount = userMapper.getCount_roleType(page);
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
			page.setData(userMapper.getUsers_roleType(page));
			page.setaNum(pageUtil.getFenYe_a_Num(page.getCurrentPage(), page.getPageSize(), totalCount, totalPage));
		}
		return page;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

	public Result deleteUser(String userId) {
		this.userMapper.deleteUserRole(userId);
		// 删除跟活动相关的所有数据必须先删除参与表数据
		// 先删除用户参与的活动的参与表中数据
		this.participationMapper.deleteParticipationByUserId(userId);
		// 根据用户id查询出所有的活动id
		List<String> activityIds = this.activityMapper.findActivityIds(userId);
		for (String activityId : activityIds) {
			this.participationMapper.deleteParticipationByActivityId(activityId);
		}
		// 活动参与表删除完毕后在删除指定用户id的所有活动
		this.activityMapper.deleteActivityByUserId(userId);
		// 删除评论
		this.commentMapper.deleteCommentByUserId(userId);
		// 查询指定用户的id的所有视频id
		List<String> videoIds = this.videoMapper.findVideoByUserId(userId);
		for (String videoId : videoIds) {
			this.videoMapper.deleteHistoryCacheByVideoId(videoId);
			this.commentMapper.deleteCommentByVideoId(videoId);
		}
		// 删除历史缓冲表中的信息
		this.userMapper.deleteHistoryCacheByUserId(userId);
		this.videoMapper.deleteVideoByUserId(userId);
		// 删除用户
		this.userMapper.deleteUser(userId);
		Result result = new Result();
		result.setStatus(0);
		result.setMessage("删除成功");
		return result;
	}

	public Result getRoleName() {
		Result result = new Result();
		List<Role> roles = this.userMapper.getRoleName();
		if (roles != null) {
			result.setStatus(0);
			result.setData(roles);
		} else {
			result.setStatus(1);
			result.setMessage("没有找到角色");
		}
		// sSystem.out.println(roles);
		return result;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

	public Result addUser(User user, String roleName, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		String roleId = this.userMapper.getRoleIdByRoleName(roleName);
		// 处理上传文件
		String originalFileName = null;
		String userId = UUIDUtil.getUUID();
		user.setId(userId);
		String realPath = request.getServletContext().getRealPath("/head");
		if (file == null || file.isEmpty()) {
			user.setHead("default.png");
			// PrintWriterUtil.printMessageToClient(response, "请选择上传文件");
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
				boolean flag = UploadUtil.uploadImage(file, userId, true, 64, realPath);
				if (!flag) {
					PrintWriterUtil.printMessageToClient(response, "图片上传失败");
					System.out.println(4);
					return null;
				}
			}
		}
		String imageFileName = null;
		try {
			// 调用添加用户数据库操作
			originalFileName = file.getOriginalFilename();
			String originalExtendName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
			imageFileName = userId + "." + originalExtendName;
			user.setHead(imageFileName);
			if (user.getLoginName().contains("@")) {
				user.setLoginType("邮箱");
			} else if (user.getLoginType().matches("[0-9]{11}")) {
				user.setLoginType("手机号");
			}
			this.userMapper.addUser(user);
			UserRole ur = new UserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			this.userMapper.addUserRole(ur);
			PrintWriterUtil.printMessageToClient(response, "用户添加成功");
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

	public Result updateUser(User user, String roleName, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = new Result();
		String imageFileName = null;
		// System.out.println(roleName);
		// System.out.println(user.getId());
		String roleId = this.userMapper.getRoleIdByRoleName(roleName);
		// 处理上传文件
		String originalFileName = null;
		String realPath = request.getServletContext().getRealPath("/head");

		if (file == null || file.isEmpty()) {
			// PrintWriterUtil.printMessageToClient(response, "请选择上传文件");
		} else {
			File file_original = new File(realPath + File.pathSeparator + user.getHead());
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
				boolean flag = UploadUtil.uploadImage(file, user.getId(), true, 64, realPath);
				if (!flag) {
					PrintWriterUtil.printMessageToClient(response, "图片上传失败");
					System.out.println(4);
					return null;
				}
			}
		}
		try {
			// 调用添加用户数据库操作
			// System.out.println(user.getLoginName());
			if (user.getLoginName().contains("@")) {
				user.setLoginType("邮箱");
			} else if (user.getLoginType().matches("[0-9]{11}")) {
				user.setLoginType("手机号");
			}
			this.userMapper.updateUser(user);
			UserRole ur = new UserRole();
			ur.setUserId(user.getId());
			this.userMapper.deleteUserRole(user.getId());
			if (roleId != null) {
				ur.setRoleId(roleId);
				this.userMapper.addUserRole(ur);
			}
			PrintWriterUtil.printMessageToClient(response, "用户修改成功");
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

	public byte[] exportUser() {
		byte[] data = null;
		// 查询要导出到excel中的数据
		List<User> users = this.userMapper.findAllUsers();
		if (users != null && users.size() > 0) {
			data = ExcelUtil.write2Excel(users);
		}
		return data;
	}

	/**
	 * 修改密码
	 */

	public boolean editPassword(User user) {
		boolean flag = false;

		int rowAffect = this.userMapper.editPassword(user);
		if (rowAffect == 1) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean findUsersById(String userId) {
		System.out.println("userName = " + userId);
		User user = this.userMapper.findUserById(userId);
		if (user != null) {
			List<Role> userRoles = user.getRoles();
			if (userRoles != null) {
				for (Role role : userRoles) {
					if ("讲师".equals(role.getName())) {
						return true;
					}
				}
			}
		}
		System.out.println("role : " + user + "; return false");
		return false;
	}

}
