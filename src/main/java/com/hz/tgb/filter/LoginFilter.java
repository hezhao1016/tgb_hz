package com.hz.tgb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		
		servletRequest.setCharacterEncoding("UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
		servletResponse.setContentType("text/html;charset=UTF-8");
//		Map<Object, Object> map = servletRequest.getParameterMap();
//		Set<Object> set = map.keySet();
//		Iterator<Object> it = set.iterator();
//		while(it.hasNext()){
//			Object[] obj = (Object[]) map.get(it.next().toString());
//			logger.info(obj[0].toString());
//		}
//		logger.info(servletRequest.getRequestURL().toString());
		
		
		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();
//		System.out.println(path);
//		System.out.println(path.indexOf("/login"));        
		 // 从session里取用户名
		String username = (String) session.getAttribute("username");
		String[] filter = {"/sys/login","/resources","/sys","/unionpay","/pay","/inf","/bishop/product/uploadProdImgs","/bishop/activity/uploadActImgs"};
		for(int i=0;i<filter.length;i++){
			if(path.indexOf(filter[i])>-1){
				chain.doFilter(servletRequest, servletResponse);
				 return;
			}
		}
		 // 登陆页面无需过滤
		/*if(path.indexOf("/sys/login") > -1 || path.indexOf("/resources") > -1) {
			 
		}*/
		if(username==null || "".equals(username)){
			servletResponse.sendRedirect("/bishop/sys/login");
		}else{
			 // 已经登陆,继续此次请求
			 chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
