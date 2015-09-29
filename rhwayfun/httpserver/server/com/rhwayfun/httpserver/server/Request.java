package com.rhwayfun.httpserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Title:Request</p>
 * <p>Description:封装request</p>
 * @author rhwayfun
 * @date Sep 17, 2015 2:37:36 PM
 * @version 1.0
 */
public class Request {

	//请求方式
	private String method;
	//请求路径
	private String url;
	//请求参数
	private Map<String,List<String>> paramsMapValues;
	//输入流
	private InputStream in;
	//请求信息
	private String requestInfo;
	
	public Request(){
		this.method = "";
		this.url = "";
		this.paramsMapValues = new HashMap<String, List<String>>();
		this.requestInfo = "";
	}
	
	public Request(InputStream in){
		this();
		this.in = in;
		try {
			byte[] data = new byte[10240];
			int len = in.read(data);
			requestInfo = new String(data,0,len);
		} catch (IOException e) {
			return;
		}
		//分析请求信息
		parseRequestInfo();
	}
	
	//解析
	public void parseRequestInfo(){
		String paramsStr = "";
		//第一行请求信息
		String firstLine = requestInfo.substring(0, requestInfo.indexOf("\r\n")).trim();
		//请求方法
		int biasIndex = firstLine.indexOf('/');
		method = firstLine.substring(0, biasIndex).trim();
		//请求路径
		String url = firstLine.substring(biasIndex, firstLine.indexOf("HTTP/")).trim();
		//根据请求方式获取不同请求路径
		if(method.equalsIgnoreCase("post")){
			this.url = url;
			paramsStr = requestInfo.substring(requestInfo.lastIndexOf("\r\n"));
		}else if(method.equalsIgnoreCase("get")){
			if(url.contains("?")){
				String[] arr = url.split("\\?");
				this.url = arr[0];
				paramsStr = arr[1];
			}else{
				this.url = url;
			}
		}
		
		if(!paramsStr.equals("")){
			parseParamsStr(paramsStr);
		}
	}

	//解析请求参数
	private void parseParamsStr(String paramsStr) {
		//1、根据&分割
		String[] splitKeyValues = paramsStr.split("&");
		for (String splitKeyValue : splitKeyValues) {
			String[] keyValues = splitKeyValue.split("=");
			//2、判断长度
			if(keyValues.length == 1){
				//只有key，value无
				keyValues = Arrays.copyOf(keyValues, 2);
				keyValues[1] = null;
			}
			//把keyvalue放入map集合中
			String key = keyValues[0].trim();
			String value = null == keyValues[1]?null:decode(keyValues[1],"utf-8");
			if(!paramsMapValues.containsKey(key)){
				paramsMapValues.put(key, new ArrayList<String>());
			}
			List<String> values = paramsMapValues.get(key);
			values.add(value);
		}
	}
	
	private String decode(String value, String code) {
		try {
			return URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getParameter(String key){
		String[] values = getParameters(key);
		if(values == null){
			return null;
		}else{
			return values[0];
		}
	}
	
	public String[] getParameters(String key){
		List<String> values = paramsMapValues.get(key);
		if(values.size() == 0 || values == null){
			return null;
		}
		return values.toArray(new String[0]);
	}
	
	public String getURL() {
		return this.url;
	}
}
