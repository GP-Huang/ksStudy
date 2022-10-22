package com.hgp.dao.user;

import com.hgp.dao.BaseDao;
import com.hgp.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {


    public User getLoginUser(Connection conn, String userCode) throws SQLException {
        PreparedStatement stmt = null;
        User user = null;
        ResultSet rs = null;

        String sql = "select * from smbms_user where userCode =?";
        Object[] params = {userCode};

        if (conn != null) {
            rs = BaseDao.execute(conn, stmt, rs, sql, params);

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRoleName(rs.getString("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getDate("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
            }

            BaseDao.closeAll(null, stmt, rs);
        }


        return user;
    }


    public int updatePwd(Connection conn, String userCode, String pwd) {
        PreparedStatement pstm = null;
        String sql = "update smbms_user set userPassword = ? where userCode = ?;";
        Object[] params = {pwd, userCode};

        int rows = 0;

        if (conn != null) {
            try {
                rows = BaseDao.execute(conn, pstm, sql, params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            return rows;
        }

        return rows;
    }
}
