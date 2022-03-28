package com.example.rbac.controller;


import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Department;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *系统管理-基础信息设置-部门管理
 * @author suj
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @OperationLogAnnotation(operModul = "部门管理",operType = "查询",operDesc = "查询所有部门")
    @ApiOperation(value = "查询所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @OperationLogAnnotation(operModul = "部门管理",operType = "添加",operDesc = "添加部门")
    @ApiOperation(value = "添加部门")
    @PostMapping("/")
    public RespBean addDep(@RequestBody Department department) {
        return departmentService.addDep(department);
    }

    @OperationLogAnnotation(operModul = "部门管理",operType = "删除",operDesc = "删除部门")
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDep(@PathVariable Integer id) {
        return departmentService.deleteDep(id);
    }
}
