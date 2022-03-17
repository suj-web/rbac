package com.example.rbac.controller;

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

    @ApiOperation(value = "删除数据表")
    @DeleteMapping("/{id}")
    public RespBean deleteTableById(@PathVariable Integer id) {
        Table table = tableService.getById(id);
        if(null != table) {
            int i = jdbcTemplate.update("delete from t_" + table.getTableName());
            if(i > 0) {
                return RespBean.success("初始化成功");
            }
        }
        return RespBean.error("初始化失败");
    }

    @ApiOperation(value = "批量删除数据表")
    @DeleteMapping("/")
    public RespBean deleteTableByIds(Integer[] ids) {
        try {
            List<Table> tables = tableService.listByIds(Arrays.asList(ids));
            for (Table table : tables) {
                jdbcTemplate.update("delete from t_" + table.getTableName());
            }
            return RespBean.success("初始化成功");
        } catch (Exception e) {
            return RespBean.error("初始化失败");
        }
    }
}
