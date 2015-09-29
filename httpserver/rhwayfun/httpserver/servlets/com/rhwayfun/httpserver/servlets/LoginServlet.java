package com.rhwayfun.httpserver.servlets;

import com.rhwayfun.httpserver.server.Request;
import com.rhwayfun.httpserver.server.Response;

public class LoginServlet extends GenericServlet {

	public void doGet(Request request,Response response) {
		//doPost(request, response);
	}

	@Override
	public void doPost(Request request,Response response) {
		response.print("<html><head><title>登录</title></head><body>");
		response.print(request.getParameter("usr"));
		response.print(" Welcome to Server</body></html>");
	}

}
