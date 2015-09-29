package com.rhwayfun.httpserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * <p>Title:HttpServer</p>
 * <p>Description:手写http服务器</p>
 * @author rhwayfun
 * @date Sep 16, 2015 6:23:49 PM
 * @version 1.0
 */
public class HttpServer {

	//是否关闭
	private boolean isShutDowm = false; 
	//服务器连接
	private ServerSocket server;
	
	public void start(){
		try {
			//1、创建http server
			start(8888);
			//2、等待客户端连接，由于使用TCP协议，所以这里的客户端就是浏览器
			this.handle();
		} catch (IOException e) {
			try {
				stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void start(int port){
		try {
			//1、创建http server
			server = new ServerSocket(port);
			//2、等待客户端连接，由于使用TCP协议，所以这里的客户端就是浏览器
			while(!isShutDowm){
				this.handle();
			}
		} catch (IOException e) {
			try {
				stop();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//获取连接信息
	private void handle() throws IOException{
		try {
			Socket client = server.accept();
			/*byte[] data = new byte[20480];
			int len = client.getInputStream().read(data);
			String info = new String(data,0,len);
			System.out.println(info.trim());*/
			//对每一个客户端都启动一个线程处理
			new Thread(new ServletDisPatcher(client)).start();
		} catch (IOException e) {
			stop();
		}
	}
	
	//关闭服务器
	public void stop() throws IOException{
		isShutDowm = true;
		server.close();
	}
	
	//使用post提交
	public void doPost() throws IOException{
		//Socket client = server.accept();
		/*//		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
//				StringBuilder sb = new StringBuilder();
//				String msg = null;
//				while((msg = br.readLine()) != null){
//					sb.append(msg);
//					sb.append("\r\n");
//				}
//				System.out.println(sb.toString().trim());
		 * 
		*/	
		/*byte[] data = new byte[20480];
		int len = client.getInputStream().read(data);
		String info = new String(data,0,len);
		System.out.println(info.trim());*/
		
		/*StringBuilder context = new StringBuilder();
		context.append("<html><head><title>test</title></head><body>Welcome to rhwayfun Server</body></html>");
		
		StringBuilder response = new StringBuilder();
		response.append("HTTP/1.1 200 OK").append("\r\n");
		response.append("Date:"+new Date()).append("\r\n");
		response.append("Content-Type: text/html;charset=utf-8").append("\r\n");
		response.append("Content-Length: " + context.toString().getBytes().length).append("\r\n");
		response.append("\r\n");
		response.append(context);
		
		//将信息响应到浏览器中
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		bw.write(response.toString(),0,response.toString().length());
		bw.flush();
		bw.close();*/
		
	}
	
	public static void main(String[] args) {
		new HttpServer().start();
	}
}
