package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.Salary;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryAdjustService;
import com.example.rbac.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工调薪
 * @author suj
 * @since 2022-01-12
 */
@RestController
@RequestMapping("/personnel/salary")
public class SalaryAdjustController {

    @Autowired
    private ISalaryAdjustService salaryAdjustService;

    @ApiOperation(value = "获取所有员工调薪信息")
    @GetMapping("/")
    public RespPageBean getAllSalaryAdjust(@RequestParam(defaultValue = "1") Integer currentPage,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           String name, String workId) {
        return salaryAdjustService.getAllSalaryAdjust(currentPage, size, name, workId);

    }
}
