package com.hzau.college.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * 全局异常拦截Filter
 */
public class ErrorFilter implements Filter {
    public static final String ERROR_URI = "/handleError";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute(ERROR_URI, e); // 将异常信息存入request域中
            request.getRequestDispatcher(ERROR_URI).forward(request, response); // 转发到错误处理页面
        }
    }
}

