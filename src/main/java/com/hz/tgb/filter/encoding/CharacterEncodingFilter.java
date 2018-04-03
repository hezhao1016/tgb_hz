package com.hz.tgb.filter.encoding;

import javax.servlet.*;
import java.io.IOException;

/**
 *  解决Spring MVC乱码
 * @author user
 *
 */
public class CharacterEncodingFilter implements Filter {
	 
    private String characterEncoding; //编码方式配置在web.xml文件中
    private boolean enabled;   //是否启用此Filter，配置在web.xml中

    @Override
    public void destroy() {
       characterEncoding = null;
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response,
                        FilterChain chain) throws IOException, ServletException {
       if (enabled || characterEncoding != null) {
           request.setCharacterEncoding(characterEncoding);
           response.setCharacterEncoding(characterEncoding);
       }
       chain.doFilter(request, response);
   }

   @Override
   public void init(FilterConfig config) throws ServletException {
       characterEncoding = config.getInitParameter("characterEncoding");
       enabled = "true".equalsIgnoreCase(config.getInitParameter("enabled").trim());
   }
}
