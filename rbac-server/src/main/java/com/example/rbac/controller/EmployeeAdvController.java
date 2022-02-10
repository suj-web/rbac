package com.example.rbac.controller;

import com.example.rbac.pojo.Appraise;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.Role;
import com.example.rbac.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工高级资料
 *
 * @Author suj
 * @create 2022/1/22
 */
@RestController
@RequestMapping("/employee/advanced")
public class EmployeeAdvController {

    @Autowired
    private IEmployeeEcService employeeEcService;
    @Autowired
    private IEmployeeTrainService employeeTrainService;
    @Autowired
    private IEmployeeRemoveService employeeRemoveService;
    @Autowired
    private IAppraiseService appraiseService;
    @Autowired
    private ISalaryAdjustService salaryAdjustService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有员工考评信息")
    @GetMapping("/appraise")
    public RespPageBean getAllAppraise(@RequestParam(defaultValue = "1") Integer currentSize,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       String name, String workId) {
        return appraiseService.getAllAppraise(currentSize, size, name, workId);
    }

    @ApiOperation(value = "获取所有员工奖惩信息")
    @GetMapping("/ec")
    public RespPageBean getAllEmployeeEc(@RequestParam(defaultValue = "1") Integer currentSize,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         String name, String workId) {
        return employeeEcService.getAllEmployeeEc(currentSize, size, name, workId);
    }

    @ApiOperation(value = "获取所有员工培训信息")
    @GetMapping("/train")
    public RespPageBean getAllEmployeeTrain(@RequestParam(defaultValue = "1") Integer currentSize,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            String name, String workId) {
        return employeeTrainService.getAllEmployeeTrain(currentSize, size, name, workId);
    }

    @ApiOperation(value = "获取所有员工调薪信息")
    @GetMapping("/adjust")
    public RespPageBean getAllSalaryAdjust(@RequestParam(defaultValue = "1") Integer currentSize,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           String name, String workId) {
        return salaryAdjustService.getAllSalaryAdjust(currentSize, size, name, workId);
    }

    @ApiOperation(value = "获取所有员工调动资料")
    @GetMapping("/remove")
    public RespPageBean getAllEmployeeRemove(@RequestParam(defaultValue = "1") Integer currentSize,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             String name, String workId) {
        return employeeRemoveService.getAllEmployeeRemove(currentSize, size, name, workId);
    }

    @ApiOperation(value = "获取所有员工工资信息")
    @GetMapping("/salary")
    public RespPageBean getAllEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentSize,
                                                 @RequestParam(defaultValue = "10") Integer size,
                                                 String name, String workId) {
        return employeeService.getEmployeeWithSalary(currentSize, size, name, workId);
    }
}
