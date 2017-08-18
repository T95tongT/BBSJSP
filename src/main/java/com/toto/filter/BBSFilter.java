package com.toto.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/13 0013.
 * 转码过滤器
 */
@WebFilter(filterName = "BBSFilter",urlPatterns = "/*",initParams = {
        @WebInitParam(name="encode",value = "utf-8")
})
public class BBSFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(encode);//所有的请求的都要转换成中文
        chain.doFilter(req, resp);//俩次过滤器
    }
    String encode=null;
    public void init(FilterConfig config) throws ServletException {
        encode=config.getInitParameter("encode");//最先走这里
    }

}
