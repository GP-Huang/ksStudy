package com.hgp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {

    private static String driver;
    private static String url;
    private static String userName;
    private static String password;

    //一个静态代码块,类加载的时候，就初始化了
    static {
        Properties properties = new Properties();
        //通过类加载器读取资源，得到反射对象，将一个资源，变成流
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            //加载流
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        userName = properties.getProperty("userName");
        password = properties.getProperty("password");
    }

    //获取数据库连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //返回结果集
    public static ResultSet execute(Connection conn, ResultSet rs, String Sql, Object[] params, PreparedStatement preparedStatement) throws SQLException {
        //预编译
        preparedStatement = conn.prepareStatement(Sql);

        if(params!=null){
            for(int i=0;i<params.length;i++){//添加占位符
                preparedStatement.setObject(i+1, params[i]);
            }
        }
        //执行sql语句
        rs = preparedStatement.executeQuery();
        //返回结果集
        return rs;
    }

    //返回结果集
    public static int execute(Connection conn, String Sql, Object[] params, PreparedStatement preparedStatement) throws SQLException {
        //预编译
        preparedStatement = conn.prepareStatement(Sql);

        if(params!=null){
            for(int i=0;i<params.length;i++){//添加占位符
                preparedStatement.setObject(i+1, params[i]);
            }
        }
        //执行sql语句
        int rows = preparedStatement.executeUpdate();
        //返回结果集
        return rows;
    }

    public static boolean closeAll(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        boolean flag = true;
        if(conn != null){
            conn.close();
            conn = null;
        }else {
            flag = false;
        }

        if(preparedStatement != null){
            preparedStatement.close();
            preparedStatement = null;
        }else {
            flag = false;
        }

        if(resultSet != null){
            resultSet.close();
            resultSet = null;
        }else {
            flag = false;
        }

        return flag;
    }

}
