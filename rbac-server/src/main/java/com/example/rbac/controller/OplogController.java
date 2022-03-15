package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Oplog;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IOplogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统管理-操作日志管理
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/system/log")
public class OplogController {

    @Autowired
    private IOplogService oplogService;

    @OperationLogAnnotation(operModul = "系统管理-操作日志管理", operType = "查询", operDesc = "查询操作日志")
    @ApiOperation(value = "查询操作日志")
    @GetMapping("/")
    public RespPageBean getAllOplogs(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     String name) {
        return oplogService.getAllOplogs(currentPage, size, name);
    }

    @OperationLogAnnotation(operModul = "系统管理-操作日志管理", operType = "删除", operDesc = "删除操作日志")
    @ApiOperation(value = "删除操作日志")
    @DeleteMapping("/")
    public RespBean deleteByIds(Integer[] ids) {
        if(oplogService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
