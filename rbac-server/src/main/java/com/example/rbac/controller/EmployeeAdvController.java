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
 * 普通员工高级资料
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
    private ISalaryService salaryService;

    @ApiOperation(value = "获取所有考评信息")
    @GetMapping("/appraise")
    public RespPageBean getAllAppraise(@RequestParam(defaultValue = "1") Integer currentSize,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       String name, String workId) {
        return appraiseService.getAllAppraise(currentSize, size, name, workId);
    }
}
