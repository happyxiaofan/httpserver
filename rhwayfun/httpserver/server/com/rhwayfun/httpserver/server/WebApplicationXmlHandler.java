package com.rhwayfun.httpserver.server;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebApplicationXmlHandler extends DefaultHandler{

	//用于存放servlet实体
	private List<ServletEntity> servletEntityList;
	//用于存放servlet-mapping实体
	private List<ServletMapping> servletMappingList;
	//标记解析的标签
	private String readTag;
	//ServletEntity实体对象
	private ServletEntity servletEntity;
	//ServletMapping映射对象
	private ServletMapping servletMapping;
	//标志是否为ServletMapping
	private boolean isServletMapping = true;
	
	public WebApplicationXmlHandler(){
		this.servletEntityList = new ArrayList<ServletEntity>();
		this.servletMappingList = new ArrayList<ServletMapping>();
	}
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("开始处理文档");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(!qName.equals("") && qName != null){
			if(qName.equals("servlet")){
				servletEntity = new ServletEntity();
				isServletMapping = false;
			}else if(qName.equals("servlet-mapping")){
				servletMapping = new ServletMapping();
				isServletMapping = true;
			}else{
				readTag = qName;
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//System.out.println("值是：" + new String(ch,start,length));
		String str = new String(ch,start,length);
		if(readTag != null){
			if(isServletMapping){
				if(readTag.equals("servlet-name")){
					servletMapping.setServeltName(str);
				}else if(readTag.equals("url-pattern")){
					servletMapping.getUrlPatterns().add(str);
				}
			}else{
				if(readTag.equals("servlet-class")){
					servletEntity.setServletClass(str);
				}else if(readTag.equals("servlet-name")){
					servletEntity.setServletName(str);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//System.out.println("结束处理元素：" + qName);
		if(qName != null && !qName.equals("")){
			if(qName.equals("servlet")){
				servletEntityList.add(servletEntity);
			}else if(qName.equals("servlet-mapping")){
				servletMappingList.add(servletMapping);
			}
		}
		readTag = null;
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("文档处理结束");
	}

	/**
	 * @return the servletEntityList
	 */
	public List<ServletEntity> getServletEntityList() {
		return servletEntityList;
	}

	/**
	 * @return the servletMappingList
	 */
	public List<ServletMapping> getServletMappingList() {
		return servletMappingList;
	}

}
