package com.tarena.vo;

public class MyCourseDTO {
	private Page page;
	private String temp;
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public String getCourseKeyword() {
		return page.getCourseKeyword();
	}
	
	public int getBegin() {
		return page.getBegin();
	}
	public int getPageSize() {
		return page.getPageSize();
	}
	
}
