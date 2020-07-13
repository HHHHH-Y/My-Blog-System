package com.huyue.model;

import com.huyue.util.DBUtil;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 往数据库中添加注册用户信息数据
 * User: HHH.Y
 * Date: 2020-07-13
 */
public class User {
    public int id;
    public String username;

    public static User register(String username, String password) throws SQLException {
        try(Connection c = DBUtil.getConnection()) {
            String sql = "insert into user (username, password) values (?, ?)";
            // 拼接对应的 sql, 并得到主键信息
            try(PreparedStatement preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                preparedStatement.executeUpdate();

                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    // 如果没有下一行
                    if(!resultSet.next()) {
                        return null;
                    }
                    User user = new User();
                    user.id = resultSet.getInt(1);
                    user.username = username;
                    return user;
                }
            }
        }
    }

    public static User login(String username, String password) throws SQLException {
        try(Connection connection = DBUtil.getConnection()) {
            String sql = "select id from user where username = ? and password = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try(ResultSet r = preparedStatement.executeQuery()) {
                    if(!r.next()) {
                        return null;
                    }
                    User user = new User();
                    user.id = r.getInt("id");
                    user.username = username;
                    return user;
                }
            }
        }
    }
}
