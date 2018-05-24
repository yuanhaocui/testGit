package com.tarena.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;


public class OnlineUserListener implements HttpSessionAttributeListener{
	
	public static List<Object> online = new ArrayList<Object>();
	public OnlineUserListener() {
		System.out.println("统计在线用户的监听器生命周期开始");
	}
	
	// session属性有增加时调用
	public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
		System.out.println("是否增加？");
		if ("loginName".equals(httpSessionBindingEvent.getSession().getAttributeNames())) {
			online.add(httpSessionBindingEvent.getSession().getAttribute("loginName"));
		}
	}

	// session属性被移除时调用
	public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
		if ("loginName".equals(httpSessionBindingEvent.getSession().getAttributeNames())) {
			online.remove(httpSessionBindingEvent.getSession().getAttribute("loginName"));
		}
	}

	// session属性被替换时调用
	public void attributeReplaced(HttpSessionBindingEvent arg0) {

	}


}
