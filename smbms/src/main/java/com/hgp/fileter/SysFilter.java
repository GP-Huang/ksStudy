package com.hgp.fileter;


import com.hgp.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Object attribute = request.getSession().getAttribute(Constants.USER_SESSION);

        if (attribute == null) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        System.out.println(request.getContextPath());
    }

    public void destroy() {

    }
}
