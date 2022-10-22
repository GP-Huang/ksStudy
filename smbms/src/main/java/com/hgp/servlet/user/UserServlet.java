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

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getParameter("method");
        if(!StringUtils.isNullOrEmpty(method)){
            update(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void update(HttpServletRequest req, HttpServletResponse resp){
        Object userSession = req.getSession().getAttribute(Constants.USER_SESSION);

        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("newpassword");
        String rnewPassword = req.getParameter("rnewpassword");

        System.out.println(oldPassword + "---" + "---" + "---");
        System.out.println(((User) userSession).getUserCode() + "    " + ((User) userSession).getId());

        boolean flag = false;
        if (userSession != null && !StringUtils.isNullOrEmpty(newPassword)) {
            UserServiceImpl userService = new UserServiceImpl();

            flag = userService.updatePwd(((User) userSession).getUserCode(), newPassword);
            if (flag) {
                req.setAttribute("message", "密码修改成功，请退出，重新登录");
                req.getSession().removeAttribute(Constants.USER_SESSION);
//                try {//经过测试，转发成功，
//                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            } else {
                req.setAttribute("message", "密码修改失败");
            }
        } else {
            req.setAttribute("message", "新密码不正确");
//            try {
//                req.getRequestDispatcher(req.getContextPath() + "/jsp/pwdmodify.jsp").forward(req, resp);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

}
