package com.example.rbac.controller;

import com.example.rbac.pojo.Department;
import com.example.rbac.pojo.EmployeeEc;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeEcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 统计管理-员工积分统计
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/statistics/score")
public class StatisticsScoreController {

    @Autowired
    private IEmployeeEcService employeeEcService;

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "员工积分统计")
    @GetMapping("/statistic")
    public List<RespChartBean> getScoreStatistic(String localDate, Integer depId) {
        return employeeEcService.getScoreStatistic(localDate, depId);
    }

    @ApiOperation(value = "员工积分排名")
    @GetMapping("/rank")
    public RespPageBean getScoreRank(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     String localDate, Integer depId) {
        return employeeEcService.getScoreRank(currentPage, size, localDate, depId);
    }
}
