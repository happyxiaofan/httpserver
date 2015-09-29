package com.rhwayfun.httpserver.servlets;

import com.rhwayfun.httpserver.server.Request;
import com.rhwayfun.httpserver.server.Response;

public abstract class GenericServlet {

	public void service(Request request,Response response) {
		this.doGet(request, response);
		this.doPost(request, response);
	}
	
	public abstract void doGet(Request request,Response response);
	public abstract void doPost(Request request,Response response);
}
