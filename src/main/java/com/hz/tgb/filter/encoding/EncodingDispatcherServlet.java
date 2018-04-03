package com.hz.tgb.filter.encoding;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决Spring MVC乱码
 * 继承自DispatcherServlet类，使它具有修改编码的功能
 * @author user
 *
 */
public class EncodingDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = 2166370507690044441L;
	
	private String encoding;

	public void init(ServletConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
		super.init(config);
	}

	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(encoding);
		super.doService(request, response);
	}
}
