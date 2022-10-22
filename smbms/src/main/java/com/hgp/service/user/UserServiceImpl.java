package com.hgp.service.user;

import com.hgp.dao.BaseDao;
import com.hgp.dao.user.UserDao;
import com.hgp.dao.user.UserDaoImpl;
import com.hgp.pojo.User;
import com.mysql.cj.util.StringUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    private UserDao userDao2 = new UserDaoImpl();

    public User login(String userCode, String password) {
        Connection conn = null;
        User user = null;

        try {
            //获取数据库连接
            conn = BaseDao.getConnection();
            user = userDao2.getLoginUser(conn, userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                BaseDao.closeAll(conn, null, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user;
    }

    public boolean updatePwd(String userCode, String pwd){
        Connection conn = BaseDao.getConnection();
        int flag = 0;
        if (userCode != null  && !StringUtils.isNullOrEmpty(pwd)){
            flag = userDao.updatePwd(conn, userCode, pwd);
            System.out.println("账号+密码" + userCode +":" + pwd);
        }

        if (flag > 0) {
            return true;
        }else {
            return false;
        }
    }

    @Test
    public void userTest(){
        UserServiceImpl userService = new UserServiceImpl();
        String userName = "admin";
        String pa = "admin";
        User user = userService.login(userName, pa);
        System.out.println(user.getPassword());
        System.out.println(user);
    }

    @Test
    public void updatePwdTest(){
        System.out.println(updatePwd("admin", "666"));
    }

}
