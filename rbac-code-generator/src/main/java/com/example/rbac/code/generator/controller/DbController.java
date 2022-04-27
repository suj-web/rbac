package com.example.rbac.code.generator.controller;

import com.example.rbac.code.generator.pojo.Db;
import com.example.rbac.code.generator.pojo.RespBean;
import com.example.rbac.code.generator.pojo.TableClass;
import com.example.rbac.code.generator.utils.DBUtils;
import com.google.common.base.CaseFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author suj
 * @create 2022/4/26
 */
@RestController
public class DbController {

    @PostMapping("/connect")
    public RespBean connect(@RequestBody Db db) {
        try {
            Connection con = DBUtils.initDb(db);
            if (null != con) {
                return RespBean.success("数据库连接成功");
            }
        } catch (Exception e) {
            return RespBean.error("数据库连接失败");
        }
        return RespBean.error("数据库连接失败");
    }

    @PostMapping("/disconnect")
    public RespBean disconnect() {
        try {
            DBUtils.disConnection();
            return RespBean.success("数据库连接已断开");
        } catch (Exception e){
            return RespBean.error("数据库连接断开失败");
        }
    }

    @PostMapping("/config")
    public RespBean config(@RequestBody Map<String, String> map){
        String packageName = map.get("packageName");
        String tablePrefix = map.get("tablePrefix");
        try {
            Connection connection = DBUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, null);
            List<TableClass> tableClassList = new ArrayList<>();
            while (tables.next()){
                TableClass tableClass = new TableClass();
                tableClass.setPackageName(packageName);
                String table_name = tables.getString("TABLE_NAME");
                tableClass.setTableName(table_name);
                if(null != tablePrefix && !"".equals(tablePrefix) && table_name.startsWith(tablePrefix)) {
                    table_name = table_name.split(tablePrefix,2)[1];
                }
                String pojoName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table_name);
                tableClass.setPojoName(pojoName);
                tableClass.setControllerName(pojoName+"Controller");
                tableClass.setMapperName(pojoName+"Mapper");
                tableClass.setServiceName("I"+pojoName+"Service");
                tableClass.setServiceImplName(pojoName+"ServiceImpl");
                tableClass.setVueName(pojoName);
                tableClassList.add(tableClass);
            }
            return RespBean.success("数据库信息读取成功",tableClassList);
        } catch (Exception e) {
            return RespBean.error("数据库信息读取失败");
        }
    }
}
