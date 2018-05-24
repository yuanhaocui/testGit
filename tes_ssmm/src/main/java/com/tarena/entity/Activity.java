package com.tarena.entity;

import java.sql.Timestamp;

public class Activity {

	private String id;
	private String title;
	private Timestamp date;
	private double longitude;
	private double latitude;
	private String location;
	private int persons;
	private double cost;
	private String picture;
	private String details;
	private String ispass;
	private String userName;
	private String courseName;
	private User user;
	private Course course;
	
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
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPersons() {
		return persons;
	}
	public void setPersons(int persons) {
		this.persons = persons;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getIspass() {
		return ispass;
	}
	public void setIspass(String ispass) {
		this.ispass = ispass;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", title=" + title + ", date=" + date + ", longitude=" + longitude + ", latitude="
				+ latitude + ", location=" + location + ", persons=" + persons + ", cost=" + cost + ", picture="
				+ picture + ", details=" + details + ", ispass=" + ispass + ", userName=" + userName + ", courseName="
				+ courseName + ", user=" + user + ", course=" + course + "]";
	}
	
	
	
}
