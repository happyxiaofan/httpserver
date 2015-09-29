package com.rhwayfun.httpserver.server;

import java.io.IOException;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.rhwayfun.httpserver.servlets.GenericServlet;

/**
 * 
 * <p>Title:DisPatcher</p>
 * <p>Description:请求分发模型，实际可以理解为每一个单独的客户端连接</p>
 * @author rhwayfun
 * @date Sep 17, 2015 5:40:39 PM
 * @version 1.0
 */
public class ServletDisPatcher implements Runnable{
	
	//响应对象
	private Response response;
	//请求对象
	private Request request;
	//客户端
	private Socket client;
	//状态码
	private int code = 200;

	public ServletDisPatcher(Socket client) {
		try {
			this.client = client;
			this.response = new Response(client);
			this.request = new Request(client.getInputStream());;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		/*Servlet servlet = new Servlet();
		servlet.service(request,response);*/
		try {
			GenericServlet servlet = WebApplication.getServlet(request.getURL());
			if(servlet == null){
				this.code = 404;
			}else{
				servlet.service(request, response);
			}
			response.response(code);
		} catch (Exception e) {
			this.code = 500;
			try {
				response.response(code);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		/*try {
			response.response(code);
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
