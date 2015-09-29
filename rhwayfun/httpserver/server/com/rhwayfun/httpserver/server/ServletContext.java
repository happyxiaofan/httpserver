package com.rhwayfun.httpserver.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <p>Title:ServletContext</p>
 * <p>Description:Servlet容器</p>
 * @author rhwayfun
 * @date Sep 17, 2015 6:55:38 PM
 * @version 1.0
 */
public class ServletContext {

	/**
	 * <servlet> 
	 * <servlet-name>LoginServlet</servlet-name>
	 * <servlet-class>com.stumis.acco.servlet.LoginServlet</servlet-class>
	 * </servlet>
	 * 
	 * <servlet-mapping> 
	 * <servlet-name>LoginServlet</servlet-name>
	 * <url-pattern>/LoginServlet</url-pattern> 
	 * </servlet-mapping>
	 */
	
	//Servelt类映射容器，对应<servlet>标签的映射关系
	private Map<String, String> servletClass;
	//url映射，对应<servlet-mapping>的映射关系
	private Map<String,String> urlMapping;
	
	public ServletContext(){
		this.servletClass = new HashMap<String, String>();
		this.urlMapping = new HashMap<String, String>();
	}

	/**
	 * @return the servletMapping
	 */
	public Map<String, String> getServletClass() {
		return servletClass;
	}

	/**
	 * @param servletMapping the servletMapping to set
	 */
	public void setServletClass(Map<String, String> servletClass) {
		this.servletClass = servletClass;
	}

	/**
	 * @return the urlMapping
	 */
	public Map<String, String> getUrlMapping() {
		return urlMapping;
	}

	/**
	 * @param urlMapping the urlMapping to set
	 */
	public void setUrlMapping(Map<String, String> urlMapping) {
		this.urlMapping = urlMapping;
	}
	
	
}
