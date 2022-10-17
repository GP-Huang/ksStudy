package study;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJunit66 {

    @Test
    public void test(){
        System.out.println("test");
    }

    @Test
    public void test2() throws Exception {
        String url = "jdbc:mysql://localhost:3306/selc?useUnicode=true&characterEncoding=utf-8";
        String userName = "root";
        String passWord = "hgp123";

        //1、加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        //2、连接数据库
        Connection conn = DriverManager.getConnection(url, userName, passWord);

        //3、创建StateMent对象，或PrepareMent对象
        Statement statement = conn.createStatement();

        //4、创建Sql，
        String sql = "update account set money = money - 1 where name = 'A'";

        try {
            //开启事务
            conn.setAutoCommit(false);

            //执行第一个SQL
            statement.executeUpdate(sql);

            System.out.println("----------");
            //添加一个错误
            System.out.println(1/0);

            //执行第二个SQL
            statement.executeUpdate(sql);

            //两条SQL通过，则执行提交
            conn.commit();
            System.out.println("success");
        }catch(Exception e){
            //如果捕获到异常，则回滚事务
            conn.rollback();

        }finally{

            conn.close();

        }



    }

    @Test
    public void testSql() throws Exception {
        String url = "jdbc:mysql://localhost:3306/selc?useUnicode=true&characterEncoding=utf-8";
        String userName = "root";
        String passWord = "hgp123";

        //1、加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        //2、连接数据库
        Connection conn = DriverManager.getConnection(url, userName, passWord);

        //3、创建StateMent对象，或PrepareMent对象
        Statement statement = conn.createStatement();

        //4、创建Sql，
        String sql = "update account set money = money - 1 where name = 'A'";

        ResultSet resultSet = conn.createStatement().executeQuery("select * from selc.account;");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }


    }

}
