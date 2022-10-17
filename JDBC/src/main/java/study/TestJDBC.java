package study;

import java.sql.*;


public class TestJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //配置信息
        String url = "jdbc:mysql://localhost:3306/selc?useUnicode=true&characterEncoding=utf-8";
        String userName = "root";
        String passWord = "hgp123";
        String db = "selc.course";

        //1、加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        //2、连接数据库
        Connection connection = DriverManager.getConnection(url, userName, passWord);

        //3、向数据库发送Sql，Statement
        Statement statement = connection.createStatement();

        //4、查询数据
        String sql = "SELECT * FROM " + db;
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            System.out.println(resultSet.getObject("Cid"));
            System.out.println("TestJDBC");
            System.out.println(resultSet.getObject("Cname"));
            System.out.println(resultSet.getObject("Type"));
        }

        System.out.println(resultSet.toString());

        //5、关闭连接
        resultSet.close();
        statement.close();
        connection.close();


    }

}
