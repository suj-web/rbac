package com.example.rbac.controller;

import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.SysMsg;
import com.example.rbac.service.IOplogService;
import com.example.rbac.service.ISysMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * 系统管理-系统管理
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/system/cfg")
public class SystemCfgController {

    @Autowired
    private ISysMsgService sysMsgService;

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "查询", operDesc = "查询系统公告")
    @ApiOperation(value = "查询系统公告")
    @GetMapping("/system/message/list")
    public RespPageBean getAllSystemMessage(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            SysMsg sysMsg) {
        return sysMsgService.getAllSystemMessage(currentPage, size, sysMsg);
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "添加", operDesc = "添加系统公告")
    @ApiOperation(value = "添加系统公告")
    @PostMapping("/system/message")
    public RespBean addSystemMessage(@RequestBody SysMsg sysMsg) {
        if(sysMsgService.save(sysMsg)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "更新", operDesc = "更新系统公告")
    @ApiOperation(value = "更新系统公告")
    @PutMapping("/system/message")
    public RespBean updateSystemMessage(@RequestBody SysMsg sysMsg) {
        if(sysMsgService.updateById(sysMsg)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "删除", operDesc = "删除系统公告")
    @ApiOperation(value = "删除系统公告")
    @DeleteMapping("/system/message/{id}")
    public RespBean deleteSystemMessage(@PathVariable Integer id) {
        if(sysMsgService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "删除", operDesc = "批量删除系统公告")
    @ApiOperation(value = "批量删除系统公告")
    @DeleteMapping("/system/message")
    public RespBean deleteSystemMessage(Integer[] ids) {
        if(sysMsgService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
