package com.rhwayfun.httpserver.server;

import java.util.ArrayList;
import java.util.List;

public class ServletMapping {

	private String serveltName;
	private List<String> urlPatterns;
	
	/**
	 * @return the serveltName
	 */
	public String getServeltName() {
		return serveltName;
	}
	/**
	 * @param serveltName the serveltName to set
	 */
	public void setServeltName(String serveltName) {
		this.serveltName = serveltName;
		this.urlPatterns = new ArrayList<String>();
	}
	/**
	 * @return the urlPattern
	 */
	public List<String> getUrlPatterns() {
		return urlPatterns;
	}
	
	
}
