package com.hgp.servlet.user;

import com.hgp.pojo.User;
import com.hgp.service.user.UserServiceImpl;
import com.hgp.util.Constants;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String pGetAttribute = (String) req.getAttribute("password");

        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userName, password);

        if (user != null) {
            if (user.getPassword().equals(password)) {//密码正确，进入内部页面
                req.getSession().setAttribute(Constants.USER_SESSION, user);
                resp.sendRedirect("jsp/frame.jsp");
            } else {//密码错误，回到登录页面，
                req.setAttribute("error", "密码错误");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "用户不存在");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
