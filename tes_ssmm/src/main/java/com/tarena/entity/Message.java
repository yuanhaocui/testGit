package com.tarena.entity;

import java.sql.Timestamp;

public class Message {
	
	
	private String id;
	private String titles;
	private String content;
	private Timestamp time;
	private String nickName;
	private String checkedName;
	
	private User user;			// association(联想，关联)user的resultMap
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitles() {
		return titles;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCheckedName() {
		return checkedName;
	}
	public void setCheckedName(String checkedName) {
		this.checkedName = checkedName;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", titles=" + titles + ", content=" + content + ", time=" + time + ", nickName="
				+ nickName + ", checkedName=" + checkedName + ", user=" + user + "]";
	}
	
	
	
}
