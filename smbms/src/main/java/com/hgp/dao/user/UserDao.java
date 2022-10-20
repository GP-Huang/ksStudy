package com.hgp.dao.user;

import com.hgp.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {

    public User getLoginUser(Connection conn, String userCode) throws SQLException;

}
