package com.rhwayfun.httpserver.servlets;

import com.rhwayfun.httpserver.server.Request;
import com.rhwayfun.httpserver.server.Response;

public class RegisterServlet extends GenericServlet {

	public void doGet(Request request,Response response) {
		//doPost(request, response);
	}

	public void doPost(Request request,Response response) {
		response.print("<html><head><title>注册</title></head><body>");
		response.print(request.getParameter("usr"));
		response.print(" 注册成功</body></html>");
	}

}
