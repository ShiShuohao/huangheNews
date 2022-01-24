package cn.huangheNews.dao;

import cn.huangheNews.pojo.Type;
import cn.huangheNews.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeDao {

    //查询所有的板块
    public static List<Type> selectType() throws Exception{
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from type order by state asc,news_number desc");
        List<Type> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(new Type(resultSet.getString("name"),resultSet.getInt("news_number"),resultSet.getInt("state")));
        }
        return list;
    }

}
