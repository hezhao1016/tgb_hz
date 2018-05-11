package com.hz.tgb.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author hezhao Xss过滤器
 */
public class XssFilter implements Filter {

    FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

}
