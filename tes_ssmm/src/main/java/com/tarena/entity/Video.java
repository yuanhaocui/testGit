package com.tarena.entity;

import java.sql.Timestamp;

public class Video {

	private String id;
	private String title;
	private String picture;
	private Timestamp ontime;
	private String introduction;
	private String filename;
	private Course course;
	private String courseName;
	private User user;
	private String userNickname;
	private int special;
	private int difficulty;
	private String click_count;
	private String forsale;
	private String state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Timestamp getOntime() {
		return ontime;
	}
	public void setOntime(Timestamp ontime) {
		this.ontime = ontime;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getClick_count() {
		return click_count;
	}
	public void setClick_count(String click_count) {
		this.click_count = click_count;
	}
	public String getForsale() {
		return forsale;
	}
	public void setForsale(String forsale) {
		this.forsale = forsale;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	@Override
	public String toString() {
		return "Video [id=" + id + ", title=" + title + ", picture=" + picture + ", ontime=" + ontime
				+ ", introduction=" + introduction + ", filename=" + filename + ", course=" + course + ", courseName="
				+ courseName + ", user=" + user + ", userNickname=" + userNickname + ", special=" + special
				+ ", difficulty=" + difficulty + ", click_count=" + click_count + ", forsale=" + forsale + "]";
	}
	
}
