package com.example.rbac.controller;

import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.Table;
import com.example.rbac.service.ITableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统管理-初始化数据库
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/system/init")
public class SystemInitController {
    @Autowired
    private ITableService tableService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "查询数据表信息")
    @GetMapping("/list")
    public List<Table> getAllTables() {
        return tableService.list();
    }

    @OperationLogAnnotation(operModul = "初始化数据库",operType = "初始化",operDesc = "初始化数据表")
    @ApiOperation(value = "初始化数据表")
    @DeleteMapping("/")
    public RespBean deleteTableByIds(Integer[] ids) {
        for(Integer id: ids) {
            if(id < 12) {
                return RespBean.error("服务器上设置了清除保护设置,无法执行该操作");
            }
        }
        try {
            List<Table> tables = tableService.listByIds(Arrays.asList(ids));
            for (Table table : tables) {
                jdbcTemplate.update("delete from " + table.getTableName());
            }
            return RespBean.success("初始化成功");
        } catch (Exception e) {
            return RespBean.error("初始化失败");
        }
    }
}
