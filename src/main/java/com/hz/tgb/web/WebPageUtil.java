/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.hz.tgb.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hz.tgb.common.ValidateUtil;
import com.hz.tgb.common.datetime.DateUtil;



/**
 * @author joshuaxu
 *
 */
public class WebPageUtil {
	public static int listPageSize = 25;
	public static int listTextMaxLen = 50;
	public static String ERROR_PAGE = "/error.jsp";
	
	public static void registListSession(HttpServletRequest request, String webPageName, String searchFlag, Integer pageNo, Map<String, Object>conditions) {
//		boolean getConditions=false;
		boolean setSession=false;

		SessionListInfo sessionListInfo=(SessionListInfo)SessionUtil.getAttribute(request, SessionUtil.SESSION_LIST_INFO);
		if (sessionListInfo ==null || !sessionListInfo.getPageName().equals(webPageName)){
			//第一次进入页面
			pageNo=1;
			setSession=true;
		}else{
			
			if (searchFlag.equals("1")){
				//点击查询按钮的查询
				setSession=true;
			}else if(searchFlag.equals("0")){
				//点击翻页
//				getConditions=true;
				sessionListInfo.setPageNo(pageNo);
			}else{
				//业务处理后回到当前页面
//				//getConditions=true;
				//pageNo=sessionListInfo.getListPageNo().toString();
			}
		}

		if (setSession){
			
			SessionListInfo sessionListInfoNew=new SessionListInfo();
			sessionListInfoNew.setPageName(webPageName);
			sessionListInfoNew.setPageNo(pageNo);
			sessionListInfoNew.setListConditions(conditions);
			SessionUtil.setAttribute(request,SessionUtil.SESSION_LIST_INFO, sessionListInfoNew);
		}
//		if (getConditions){
//			Map<String,String> listConditions=sessionListInfo.getListConditions();
//			for (Iterator<String> iterator = listConditions.keySet().iterator(); iterator.hasNext(); ) {
//				String key=iterator.next();
//				String value=(String)conditions.get(key);
//				value=listConditions.get(key);
//			}
//		}

	}
	
	public static String getString(Object attribut) {
		if (attribut instanceof String)
		{
			if (attribut!=null)
				return (String)attribut;
		}
		else if (attribut instanceof Byte) {
			if (attribut!=null)
				return attribut.toString();
		}
		else if (attribut instanceof Integer) {
			if (attribut!=null)
				return attribut.toString();
		}
		return "";
	}
	
	public static String getSelect(Byte attribut, Byte value) {
		if (attribut==null)
			return "";
		
		if (attribut==value)
			return "selected=\"selected\"";
		
		return "";
	}
	
	public static String getSelect(Integer attribut, Integer value) {
		if (attribut==null)
			return "";
		
		if (attribut.equals(value))
			return "selected=\"selected\"";
		
		return "";
	}
	
	public static String getDateTimeString(Date attribut) {
		if (attribut==null)
			return "";
		
		return DateUtil.formatDateTime(attribut);
	}
	
	public static String getSelect(String attribut, String value) {
		if (ValidateUtil.isEmptyString(attribut))
			return "";
		
		if (attribut.equals(value))
			return "selected=\"selected\"";
		
		return "";
	}
	
}
