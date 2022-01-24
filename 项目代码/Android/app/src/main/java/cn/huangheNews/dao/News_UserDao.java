package cn.huangheNews.dao;

import cn.huangheNews.pojo.News_User;
import cn.huangheNews.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

public class News_UserDao {

    //查询最新的20个新闻
    public static List<News_User> selectNews_UserForLatest() throws Exception{
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from news_user order by date desc");
        List<News_User> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new News_User(resultSet.getInt("id"),resultSet.getString("headline"),resultSet.getString("author"),resultSet.getString("content"),resultSet.getDate("date"),resultSet.getString("type"),resultSet.getInt("click"),resultSet.getInt("comment"),resultSet.getInt("first"),resultSet.getString("petname"),resultSet.getString("sex")));
        }
        if (list.size()>20) {
            list = list.subList(0,19);
        }
        return list;
    }

    //查询点击量最多的20个新闻
    public static List<News_User> selectNews_UserForHot() throws Exception{
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from news_user order by click desc");
        List<News_User> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new News_User(resultSet.getInt("id"),resultSet.getString("headline"),resultSet.getString("author"),resultSet.getString("content"),resultSet.getDate("date"),resultSet.getString("type"),resultSet.getInt("click"),resultSet.getInt("comment"),resultSet.getInt("first"),resultSet.getString("petname"),resultSet.getString("sex")));
        }
        if (list.size()>20) {
            list = list.subList(0,19);
        }
        return list;
    }

    //已知id查询新闻
    public static News_User selectNews_UserById(int id) throws Exception{
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from news_user where id='"+id+"'");
        News_User news_user = null;
        while (resultSet.next()) {
            news_user = new News_User(resultSet.getInt("id"),resultSet.getString("headline"),resultSet.getString("author"),resultSet.getString("content"),resultSet.getDate("date"),resultSet.getString("type"),resultSet.getInt("click"),resultSet.getInt("comment"),resultSet.getInt("first"),resultSet.getString("petname"),resultSet.getString("sex"));
        }
        return news_user;
    }

    //查询某板块的全部新闻
    public static List<News_User> selectNews_UserByTypeName(String typeName) throws Exception{
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from news_user where type='" + typeName + "' order by date desc");
        List<News_User> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new News_User(resultSet.getInt("id"),resultSet.getString("headline"),resultSet.getString("author"),resultSet.getString("content"),resultSet.getDate("date"),resultSet.getString("type"),resultSet.getInt("click"),resultSet.getInt("comment"),resultSet.getInt("first"),resultSet.getString("petname"),resultSet.getString("sex")));
        }
        return list;
    }
}
