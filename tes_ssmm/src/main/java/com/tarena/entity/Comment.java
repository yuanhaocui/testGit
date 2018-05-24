package com.tarena.entity;

import java.sql.Timestamp;

public class Comment {

	private String comment_id;
	private String comment_content;
	private Timestamp comment_timestamp;
	private String user_nickname;
	private String video_title;
	private String course_name;
	private String comment_ispass;
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public Timestamp getComment_timestamp() {
		return comment_timestamp;
	}
	public void setComment_timestamp(Timestamp comment_timestamp) {
		this.comment_timestamp = comment_timestamp;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getVideo_title() {
		return video_title;
	}
	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getComment_ispass() {
		return comment_ispass;
	}
	public void setComment_ispass(String comment_ispass) {
		this.comment_ispass = comment_ispass;
	}
	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", comment_content=" + comment_content + ", comment_timestamp="
				+ comment_timestamp + ", user_nickname=" + user_nickname + ", video_title=" + video_title
				+ ", course_name=" + course_name + ", comment_ispass=" + comment_ispass + "]";
	}
	
	
}
