package com.tarena.entity;

import java.sql.Timestamp;

public class Course {
	private int semester; // 学期 ： 1=第一学期；2=第二学期
	private String semsterString;
	private String durantionString;
	private String typeString;
	private int duration; // 时间 ： 1=前八周； 2=后八周； 3=全学期
	private int type; // 类型： 1=必修； 2=选修
	private float score; // 学分
	private String userName;
	private String id;
	private String name;
	private String picture;
	private int order;
	private String desc;
	private Timestamp regDate;

	
	public String getSemsterString() {
		return semsterString;
	}

	public void setSemsterString() {
		if(this.semester == 1) {
			this.semsterString = "第一学期";
		}else if(this.semester ==2) {
			this.semsterString = "第二学期";
		}else if(this.semester ==3) {
			this.semsterString = "第三学期";
		}else if(this.semester ==4) {
			this.semsterString = "第四学期";
		}else if(this.semester ==5) {
			this.semsterString = "第五学期";
		}else if(this.semester ==6) {
			this.semsterString = "第六学期";
		}else if(this.semester ==7) {
			this.semsterString = "第七学期";
		}else if(this.semester ==8) {
			this.semsterString = "第八学期";
		}
	}

	public String getDurantionString() {
		return durantionString;
	}

	public void setDurantionString() {
		if(this.duration == 1) {
			this.durantionString = "前八周";
		}else if(this.duration == 2) {
			this.durantionString = "后八周";
		}else if(this.duration == 3) {
			this.durantionString = "全学期";
		}
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString() {
		if(this.type == 1) {
			this.typeString = "必修";
		}else if(this.type == 2) {
			this.typeString = "选修";
		}
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Course [semester=" + semester + ", semsterString=" + semsterString + ", durantionString="
				+ durantionString + ", typeString=" + typeString + ", duration=" + duration + ", type=" + type
				+ ", score=" + score + ", userName=" + userName + ", id=" + id + ", name=" + name + ", picture="
				+ picture + ", order=" + order + ", desc=" + desc + ", regDate=" + regDate + "]";
	}


}
