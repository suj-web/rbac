package com.example.rbac.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.rbac.service.ITableService;
import com.example.rbac.pojo.Table;
import com.example.rbac.pojo.RespPageBean;
import java.util.Arrays;

/**
 * TableController
 * @author suj
 * @since 2022-4-28
 */
@RestController
@RequestMapping("/tableController")
public class TableController {

    @Autowired
    private ITableService tableService;

    @ApiOperation(value = "分页查询")
    @GetMapping("/list")
    public RespPageBean getAllTable(@RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "10") Integer size) {
        return tableService.getAllTable(currentPage, size);
    }

    @ApiOperation(value = "添加")
    @PostMapping("/")
    public RespBean addTable(@RequestBody Table table) {
        if(tableService.save(table)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "修改")
    @PutMapping("/")
    public RespBean updateTable(@RequestBody Table table) {
        if(tableService.updateById(table)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public RespBean deleteTableById(@PathVariable Integer id) {
        if(tableService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public RespBean deleteTableByIds(Integer[] ids) {
        if(tableService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}