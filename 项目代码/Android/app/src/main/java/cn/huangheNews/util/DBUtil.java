package cn.huangheNews.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtil {

    public static Connection getConnection() {
        Connection connection = null;
        try {
            //得到数据库连接
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://123.57.172.73:3306/huanghenews", "shidongdong", "1233210456abc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close(); // 关闭资源
            }
            if (statement != null) {
                statement.close(); // 关闭资源
            }
            if (connection != null) {
                connection.close(); // 关闭资源
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
