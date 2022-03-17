package com.example.rbac.controller;

import com.example.rbac.pojo.Department;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 统计管理-人事信息统计
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/statistics/personnel")
public class StatisticsPersonnelController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "人员构成分析-按年龄段统计")
    @GetMapping("/composition/age")
    public List<RespChartBean> getCompositionByAge(Integer depId) {
        return employeeService.getCompositionByAge(depId);
    }

    @ApiOperation(value = "人员构成分析-按工龄统计")
    @GetMapping("/composition/workAge")
    public List<RespChartBean> getCompositionByWorkAge(Integer depId) {
        return employeeService.getCompositionByWorkAge(depId);
    }

    @ApiOperation(value = "人员构成分析-按性别统计")
    @GetMapping("/composition/gender")
    public List<RespChartBean> getCompositionByGender(Integer depId) {
        return employeeService.getCompositionByGender(depId);
    }

    @ApiOperation(value = "人员构成分析-按最高学历统计")
    @GetMapping("/composition/degree")
    public List<RespChartBean> getCompositionByDegree(Integer depId) {
        return employeeService.getCompositionByDegree(depId);
    }
}
