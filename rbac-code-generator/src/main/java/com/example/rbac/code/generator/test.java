package com.example.rbac.code.generator;

import com.example.rbac.code.generator.pojo.Db;
import com.example.rbac.code.generator.utils.DBUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

/**
 * @Author suj
 * @create 2022/4/26
 */
public class test {
    public static void main(String[] args) throws Exception {
//        String ss = "t_abc_def";
//        String[] t_s = ss.split("t_",2);
//        System.out.println(t_s[0]);
//        System.out.println(t_s[1]);
        Db db = new Db();
        db.setUsername("root");
        db.setPassword("12345678");
        db.setUrl("jdbc:mysql://localhost:3306/rbac?useUnicode=true&characterEncoding=utf8&useSSL=false");
        Connection connection = DBUtils.initDb(db);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet keys = metaData.getPrimaryKeys(connection.getCatalog(), null, "t_admin");
        keys.next();
        System.out.println(keys.getString("COLUMN_NAME"));
    }
}
