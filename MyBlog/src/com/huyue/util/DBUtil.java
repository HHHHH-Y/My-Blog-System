package com.huyue.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: HHH.Y
 * Date: 2020-07-13
 */
public class DBUtil {
    public static final DataSource datasource = initDataSource();

    private static DataSource initDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setServerName("127.0.0.1");
        mysqlDataSource.setPort(3306);
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("huyuelover1017");
        mysqlDataSource.setCharacterEncoding("utf8");
        mysqlDataSource.setDatabaseName("my_blog");
        mysqlDataSource.setUseSSL(false);

        return mysqlDataSource;
    }

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }
}
