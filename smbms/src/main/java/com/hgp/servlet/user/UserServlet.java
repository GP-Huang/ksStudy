package com.hgp.servlet.user;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hgp.pojo.User;
import com.hgp.service.user.UserServiceImpl;
import com.hgp.util.Constants;
import com.mysql.cj.util.StringUtils;


import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String method = req.getParameter("method");
        if (!StringUtils.isNullOrEmpty(method) && method.equals("savepwd")) {
            update(req, resp);
        }

        if (!StringUtils.isNullOrEmpty(method) && method.equals("pwdmodify")) {
            pwdModify(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) {
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

    public void pwddModify(HttpServletRequest req, HttpServletResponse resp) {
        //从session中获取旧密码
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldPwd = ((User) o).getPassword();

        String oldpassword = req.getParameter("oldpassword");

        //定义返回结果集
        Map<String, String> resultMap = new HashMap<String, String>();

        //判断输入密码的几种类型
        if (o == null) {
            resultMap.put("result", "sessionerror");
        } else if (!StringUtils.isNullOrEmpty(oldPwd)) {
            if (oldPwd.equals(oldpassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        } else {
            resultMap.put("result", "error");
        }

        try {

            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();

            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //验证旧密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) {
        //session中有用户的密码，从里面取旧密码，与前端传递过来的参数做对比
        String oldpassword = req.getParameter("oldpassword");
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);

        //使用map封装数据，一切的东西都可以使用map去保存
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (o == null) {//session过期或者session已经失效
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {//旧密码输入为空
            resultMap.put("result", "error");
        } else {
            if (oldpassword.equals(((User) o).getPassword())) {//旧密码输入正确
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

//        Map<String, String> resultMap1 = new HashMap<String, String>();
//        resultMap1.put("result", "test");
//        try {
//            String jsonString = JSONObject.toJSONString(resultMap1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String result = "{\"result\":\"" + resultMap.get("result") + "\"}";

        try {
            resp.getWriter().write(result);
            System.out.println(resultMap + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("result", "false");
        String jsonString = com.alibaba.fastjson.JSONObject.toJSONString(hashMap);
    }

    public class Student {
        private String name;
        private String address;
        private String age;

        Student(String n, String addr, String a) {
            name = n;
            address = addr;
            age = a;
        }
    }

    @Test
    public void test() {
        Student stu = new Student("公众号编程大道", "m", "2");

        HashMap<String, String> result = new HashMap<String, String>();
        result.put("result", "test");
        //Java对象转换成JSON字符串
        String stuString = JSONObject.toJSONString(stu);
        String stuString2 = JSONObject.toJSONString(result);
        System.out.println("Java对象转换成JSON字符串\n" + stuString);//{"age":2,"name":"公众号编程大道","sex":"m"}
    }


}
