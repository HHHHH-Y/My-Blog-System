package com.huyue.model;

import com.huyue.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: HHH.Y
 * Date: 2020-07-13
 */
public class Article {
    public int authorId;
    public String authorUsername;
    public int id;
    public String title;
    public String publishedAt;
    public String content;
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

    // 文章列表的内容需要在数据库中进行查询
    public static List<Article> list() throws SQLException {
        List<Article> articleList = new ArrayList<>();

        try(Connection connection = DBUtil.getConnection()) {
            String sql = "select \n" +
                    "    user.id author_id,\n" +
                    "    user.username author_username,\n" +
                    "    article.id,\n" +
                    "    title,\n" +
                    "    published_at\n" +
                    "from article join user on article.user_id = user.id\n" +
                    "order by published_at desc";

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet r = preparedStatement.executeQuery()) {
                    while (r.next()) {
                        Article article = new Article();
                        article.id = r.getInt("id");
                        article.authorId = r.getInt("author_id");
                        article.title = r.getString("title");
                        article.authorUsername = r.getString("author_username");
                        article.publishedAt = r.getString("published_at");

                        articleList.add(article);
                    }
                }
            }
        }
        return articleList;
    }

    public static Article get(int id) throws SQLException {
        try(Connection connection = DBUtil.getConnection()) {
            String sql = "select title, content from article where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try(ResultSet r = preparedStatement.executeQuery()) {
                    if(!r.next()) {
                        return null;
                    }

                    Article article = new Article();
                    article.title = r.getString("title");
                    article.content = r.getString("content");

                    return article;
                }
            }
        }
    }
}
