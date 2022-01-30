package com.example.rbac.controller;


import com.example.rbac.pojo.EmployeeEc;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeEcService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 员工奖惩
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/personnel/ec")
public class EmployeeEcController {
    @Autowired
    private IEmployeeEcService employeeEcService;

    @ApiOperation(value = "获取所有奖惩人员信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeEc(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         String name, String workId) {
        return employeeEcService.getAllEmployeeEc(currentPage, size, name, workId);
    }

    @ApiOperation(value = "添加奖惩人员信息")
    @PostMapping("/")
    public RespBean addEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        if(employeeEcService.save(employeeEc)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "更新奖惩人员信息")
    @PutMapping("/")
    public RespBean updateEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        if(employeeEcService.updateById(employeeEc)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除奖惩人员信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeEc(@PathVariable Integer id) {
        if(employeeEcService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除奖惩人员信息")
    @DeleteMapping("/")
    public RespBean deleteManyEmployeeEc(Integer[] ids) {
        if(employeeEcService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
