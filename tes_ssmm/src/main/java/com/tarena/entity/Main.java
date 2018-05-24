package com.tarena.entity;

public class Main {
	
	/*统计图*/
	private int value;
	private String color;
	private String hightlight;
	private String label;

	/*用户增长率*/
	private int userMonthBasis;					//月环比
	private int userSameMonth;					//月同比
	private int userQuarterBasis;				//季环比
	private int userSameQuarter;				//季同比
	
	/*视频增长率*/
	private int videoMonthBasis;				//月环比
	private int videoSameMonth;					//月同比
	private int videoQuarterBasis;				//季环比
	private int videoSameQuarter;				//季同比
	
	/*视频排行*/
	private long number;		//视频排行个数
	private String name;		//视频排行名字
	
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getHightlight() {
		return hightlight;
	}
	public void setHightlight(String hightlight) {
		this.hightlight = hightlight;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getVideoMonthBasis() {
		return videoMonthBasis;
	}
	public void setVideoMonthBasis(int videoMonthBasis) {
		this.videoMonthBasis = videoMonthBasis;
	}
	public int getVideoSameMonth() {
		return videoSameMonth;
	}
	public void setVideoSameMonth(int videoSameMonth) {
		this.videoSameMonth = videoSameMonth;
	}
	public int getVideoQuarterBasis() {
		return videoQuarterBasis;
	}
	public void setVideoQuarterBasis(int videoQuarterBasis) {
		this.videoQuarterBasis = videoQuarterBasis;
	}
	public int getVideoSameQuarter() {
		return videoSameQuarter;
	}
	public void setVideoSameQuarter(int videoSameQuarter) {
		this.videoSameQuarter = videoSameQuarter;
	}
	public int getUserMonthBasis() {
		return userMonthBasis;
	}
	public void setUserMonthBasis(int userMonthBasis) {
		this.userMonthBasis = userMonthBasis;
	}
	public int getUserSameMonth() {
		return userSameMonth;
	}
	public void setUserSameMonth(int userSameMonth) {
		this.userSameMonth = userSameMonth;
	}
	public int getUserQuarterBasis() {
		return userQuarterBasis;
	}
	public void setUserQuarterBasis(int userQuarterBasis) {
		this.userQuarterBasis = userQuarterBasis;
	}
	public int getUserSameQuarter() {
		return userSameQuarter;
	}
	public void setUserSameQuarter(int userSameQuarter) {
		this.userSameQuarter = userSameQuarter;
	}

	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
