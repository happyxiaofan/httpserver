package com.rhwayfun.httpserver.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.rhwayfun.httpserver.servlets.GenericServlet;

/**
 * 
 * <p>Title:WebApplication</p>
 * <p>Description:Web应用</p>
 * @author rhwayfun
 * @date Sep 17, 2015 6:56:11 PM
 * @version 1.0
 */
public class WebApplication {

	//Servlet容器
	private static ServletContext context;
	//servlet类
	private static String clazz;
	
	/**
	 * 静态初始化，这里只是做测试用，后边还需要使用xml文件获取
	 */
	static{
		//获取Servlet容器
		/*Map<String,String> servletMapping = context.getServletMapping();
		servletMapping.put("login", new LoginServlet());
		servletMapping.put("log", new LoginServlet());
		servletMapping.put("reg", new RegisterServlet());
		
		//获取映射容器
		Map<String,String> urlMapping = context.getUrlMapping();
		urlMapping.put("/login", "login");
		urlMapping.put("/log", "log");
		urlMapping.put("/reg", "reg");*/
		//1、创建SAXParserFactory工厂
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			//2、获取文档解析器
			SAXParser saxParser = saxParserFactory.newSAXParser();
			//3、注册一个文档处理器
			WebApplicationXmlHandler handler = new WebApplicationXmlHandler();
			//解析web.xml文件
			saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("rhwayfun/httpserver/server/WEB-INF/web.xml"), handler);
			//获取ServletEntity集合
			List<ServletEntity> list1 = handler.getServletEntityList();
			
			context = new ServletContext();
			Map<String,String> servletMapping = context.getServletClass();
			for (ServletEntity servletEntity : list1) {
				servletMapping.put(servletEntity.getServletName(), servletEntity.getServletClass());
			}
			
			//获取ServletMapping集合
			List<ServletMapping> list2 = handler.getServletMappingList();
			Map<String,String> urlMapping = context.getUrlMapping();
			for (ServletMapping servletMapping2 : list2) {
				List<String> urlPatterns = servletMapping2.getUrlPatterns();
				for (String urlpattern : urlPatterns) {
					urlMapping.put(urlpattern,servletMapping2.getServeltName());
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * 根据请求的url路径查找对应的Servlet
	 * @Description: TODO
	 * @param url
	 * @return   
	 * @return GenericServlet  
	 * @throws
	 */
	public static GenericServlet getServlet(String url) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> clazz = Class.forName(context.getServletClass().get(context.getUrlMapping().get(url)));
		GenericServlet servlet = (GenericServlet) clazz.newInstance();
		return servlet;
	}
}
