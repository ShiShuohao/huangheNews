package cn.huangheNews.dao;

import cn.huangheNews.util.DBUtil;

import java.sql.Connection;
import java.sql.Statement;

public class NewsDao {

    //将某新闻的点击量加 1
    public static int updateNewsForClickIncreaseById(int id) throws Exception {
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate("update news set click=click+1 where id='" + id + "'");
        return i;
    }
}
