package com.tarena.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrintWriterUtil {

	public static void printMessageToClient(HttpServletResponse response,String message){
		try {
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			out.println(message);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
