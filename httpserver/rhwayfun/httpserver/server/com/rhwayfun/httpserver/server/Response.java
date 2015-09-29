package com.rhwayfun.httpserver.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 
 * <p>Title:Response</p>
 * <p>Description:封装response</p>
 * @author rhwayfun
 * @date Sep 16, 2015 8:04:05 PM
 * @version 1.0
 */
public class Response {

	//响应头
	private StringBuilder respHeader = null;
	//正文长度
	private int len = 0;
	//响应正文
	private StringBuilder content = null;
	//输出流
	private BufferedWriter bw = null;
	
	public Response(){
		respHeader = new StringBuilder();
		content = new StringBuilder();
		len = 0;
	}
	
	public Response(Socket client) throws IOException{
		this();
		bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	}
	
	public Response print(String info){
		content.append(info);
		len += content.toString().getBytes().length;
		return this;
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param status   状态码
	 * @return void  
	 * @throws
	 */
	public void createRespHeader(int status){
		respHeader.append("HTTP/1.1 ");
		switch(status){
			case 200:
				respHeader.append(status).append(" ").append("OK").append("\r\n");
				break;
			case 400:
				respHeader.append(status).append(" ").append("Bad Request").append("\r\n");
				break;
			case 404:
				respHeader.append(status).append(" ").append("Not Found").append("\r\n");
				break;
			case 500:
				respHeader.append(status).append(" ").append("Server Error").append("\r\n");
				break;
		}
		//添加其他响应头信息
		respHeader.append("Date:"+new Date()).append("\r\n");
		respHeader.append("Content-Type: text/html;charset=utf-8").append("\r\n");
		respHeader.append("Content-Length: " + len).append("\r\n");
		respHeader.append("\r\n");
	}
	
	public void response(int status) throws IOException{
		createRespHeader(status);
		bw.append(respHeader.toString());
		bw.append(content.toString());
		bw.flush();
		bw.close();
	}
}