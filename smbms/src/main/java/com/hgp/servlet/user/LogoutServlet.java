package com.hgp.servlet.user;

import com.hgp.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取登录用户的session，再移除session
        req.getSession().removeAttribute(Constants.USER_SESSION);

        //重定向地址，有两种方式
//        resp.sendRedirect(req.getContextPath()+"/login.jsp");
        resp.sendRedirect("/smbms/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
