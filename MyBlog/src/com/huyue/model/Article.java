package com.huyue.model;

import com.huyue.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: HHH.Y
 * Date: 2020-07-13
 */
public class Article {
    // 这种用 DateFormat 是错误的, 因为:
    // 1. DateFormat 不是线程安全的
    // 2. 我们的 publish 实际上是运行在多线程环境下的
    /*static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void publish(int id, String title, String content) {
        Date date = new Date();
        String publishedAt = format.format(date);
    }*/

    public static void publish(int useId, String title, String content) throws SQLException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String publishedAt = format.format(date);

        try(Connection connection = DBUtil.getConnection()) {
            String sql = "insert into article (user_id, title, content, published_at) values (?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, useId);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, content);
                preparedStatement.setString(4, publishedAt);

                preparedStatement.executeUpdate();
            }
        }
    }
}
