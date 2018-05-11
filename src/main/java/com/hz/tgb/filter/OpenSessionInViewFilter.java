package com.hz.tgb.filter;

import com.hz.tgb.data.hibernate.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.*;
import java.io.IOException;

/**
 * OpenSessionInView 经典实现
 *
 * @author hezhao
 * @date 2015-05-16
 */
public class OpenSessionInViewFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		// 请求到达时，打开Session并启动事务
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			// 执行请求处理链
			chain.doFilter(request, response);
			// 返回响应时，提交事务
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			// 关闭Session
			HibernateSessionFactory.closeSession();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	@Override
	public void destroy() {}

}
