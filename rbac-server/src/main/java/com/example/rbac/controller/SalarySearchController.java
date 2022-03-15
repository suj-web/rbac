package com.example.rbac.controller;

import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryTableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 人事管理-工资表查询
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/salary/search")
public class SalarySearchController {

    @Autowired
    private IEmployeeService employeeService;

    @OperationLogAnnotation(operModul = "人事管理-工资表查询",operType = "查询",operDesc = "获取所有工资表信息")
    @ApiOperation(value = "获取所有工资表信息")
    @GetMapping("/list")
    public RespPageBean getAllSalaryTables(@RequestParam(defaultValue = "1") Integer currentPage,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           Integer depId,
                                           LocalDate localDate) {
        return employeeService.getAllSalaryTables(currentPage, size, depId, localDate);
    }
}
