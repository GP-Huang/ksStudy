package com.hgp.service.user;

import com.hgp.dao.BaseDao;
import com.hgp.dao.user.UserDao;
import com.hgp.dao.user.UserDaoImpl;
import com.hgp.pojo.User;
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

    @Test
    public void userTest(){
        UserServiceImpl userService = new UserServiceImpl();
        String userName = "admin";
        String pa = "admin";
        User user = userService.login(userName, pa);
        System.out.println(user.getPassword());
        System.out.println(user);
    }


}
