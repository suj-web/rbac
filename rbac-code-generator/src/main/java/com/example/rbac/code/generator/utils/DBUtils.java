package com.example.rbac.code.generator.utils;

import com.example.rbac.code.generator.pojo.Db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author suj
 * @create 2022/4/26
 */
public class DBUtils {
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static Connection initDb(Db db) throws Exception{
        if(null == connection) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(db.getUrl()+"&autoReconnect=true", db.getUsername(), db.getPassword());
        }
        return connection;
    }

    public static void disConnection() {
        try {
            connection.close();
            connection = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
