package com.example.rbac.controller;

import com.example.rbac.pojo.Department;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.Salary;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 薪资管理-工资表管理
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/salary/table")
public class SalaryTableController {
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ISalaryService salaryService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salary")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department")
    public List<Department> getAllDepartment() {
        return departmentService.list();
    }

    @ApiOperation(value = "获取所有员工工资信息")
    @GetMapping("/")
    public List<Salary> getAllSalary(Integer salaryId, Integer depId) {
        return salaryService.getAllSalary(salaryId, depId);
    }
}
