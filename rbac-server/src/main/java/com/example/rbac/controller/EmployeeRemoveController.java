package com.example.rbac.controller;


import com.example.rbac.pojo.EmployeeRemove;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeRemoveService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 人事管理-员工调动
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/personnel/remove")
public class EmployeeRemoveController {

    @Autowired
    private IEmployeeRemoveService employeeRemoveService;

    @ApiOperation(value = "获取员工调动信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeRemove(@RequestParam(defaultValue = "1") Integer currentPage,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             String name, String workId) {
        return employeeRemoveService.getAllEmployeeRemove(currentPage, size, name, workId);
    }

    @ApiOperation(value = "添加员工调动信息")
    @PostMapping("/")
    public RespBean addEmployeeRemove(@RequestBody EmployeeRemove employeeRemove) {
        return employeeRemoveService.addEmployeeRemove(employeeRemove);
    }

    @ApiOperation(value = "修改员工调动信息")
    @PutMapping("/")
    public RespBean updateEmployeeRemove(@RequestBody EmployeeRemove employeeRemove) {
        return employeeRemoveService.updateEmployeeRemove(employeeRemove);
    }

    @ApiOperation(value = "删除员工调动信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeRemove(@PathVariable Integer id) {
        if(employeeRemoveService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除员工调动信息")
    @DeleteMapping("/")
    public RespBean deleteManyEmployeeRemove(Integer[] ids) {
        if(employeeRemoveService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
