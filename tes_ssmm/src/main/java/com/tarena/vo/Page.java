package com.tarena.vo;

import java.util.List;

import com.tarena.entity.Role;

public class Page {

	private int currentPage;
	private int pageSize;
	private int previousPage;
	private int nextPage;
	private int totalCount;
	private int totalPage;
	private int begin;
	private List<Integer> aNum;
	private String roleKeyword;
	private String userKeyword;
	private String courseKeyword;
	private String activityKeyword;
	private String videoKeyword;
	private String commentKeyword;
	private List data;
	private String roleType;//用户管理中角色类型.讲师,学员,管理员
	private String isPass;
	
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getCommentKeyword() {
		return commentKeyword;
	}
	public void setCommentKeyword(String commentKeyword) {
		this.commentKeyword = commentKeyword;
	}
	public String getVideoKeyword() {
		return videoKeyword;
	}
	public void setVideoKeyword(String videoKeyword) {
		this.videoKeyword = videoKeyword;
	}
	public String getActivityKeyword() {
		return activityKeyword;
	}
	public void setActivityKeyword(String activityKeyword) {
		this.activityKeyword = activityKeyword;
	}
	public String getCourseKeyword() {
		return courseKeyword;
	}
	public void setCourseKeyword(String courseKeyword) {
		this.courseKeyword = courseKeyword;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPreviousPage() {
		return previousPage;
	}
	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		totalPage=(totalCount%pageSize==0)? (totalCount/pageSize):(totalCount/pageSize)+1;
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getBegin() {
		begin=(currentPage-1)*pageSize;
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public List<Integer> getaNum() {
		return aNum;
	}
	public void setaNum(List<Integer> aNum) {
		this.aNum = aNum;
	}
	public String getRoleKeyword() {
		return roleKeyword;
	}
	public void setRoleKeyword(String roleKeyword) {
		this.roleKeyword = roleKeyword;
	}
	public String getUserKeyword() {
		return userKeyword;
	}
	public void setUserKeyword(String userKeyword) {
		this.userKeyword = userKeyword;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public String getRoleType() {
		return "%"+roleType+"%";
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", pageSize=" + pageSize + ", previousPage=" + previousPage
				+ ", nextPage=" + nextPage + ", totalCount=" + totalCount + ", totalPage=" + totalPage + ", begin="
				+ begin + ", aNum=" + aNum + ", roleKeyword=" + roleKeyword + ", userKeyword=" + userKeyword
				+ ", courseKeyword=" + courseKeyword + ", activityKeyword=" + activityKeyword + ", videoKeyword="
				+ videoKeyword + ", commentKeyword=" + commentKeyword + ", data=" + data + ", roleType=" + roleType
				+ ", isPass=" + isPass + "]";
	}
	
	
}
