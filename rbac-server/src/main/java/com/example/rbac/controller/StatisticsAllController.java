package com.example.rbac.controller;

import com.example.rbac.pojo.Department;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IAppraiseService;
import com.example.rbac.service.IAttendanceService;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计管理-综合信息统计
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/statistics/all")
public class StatisticsAllController {

    @Autowired
    private IAttendanceService attendanceService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IAppraiseService appraiseService;

    @ApiOperation(value = "考勤信息统计")
    @GetMapping("/attendance/list")
    public RespPageBean getAllAttendance(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         String localDate, Boolean absenteeism, Integer depId) {
        return attendanceService.getAllAttendance(currentPage, size, localDate, absenteeism, depId);
    }

    @ApiOperation(value = "职位人员统计")
    @GetMapping("/position/number")
    public List<RespChartBean> getPositionNumber() {
        return employeeService.getPositionNumber();
    }

    @ApiOperation(value = "部门人员统计")
    @GetMapping("/department/number")
    public List<RespChartBean> getDepartmentNumber() {
        return employeeService.getDepartmentNumber();
    }

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "考评得分排名")
    @GetMapping("/appraise/rank")
    public RespPageBean getAppraiseRank(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        String localDate, Integer depId) {
        return appraiseService.getAppraiseRank(currentPage, size, localDate, depId);
    }

    @ApiOperation(value = "部门薪资统计")
    @GetMapping("/salary/department")
    public Map<String, Object> getSalaryDepartment(String localDate) {
        List<RespChartBean> charts = employeeService.getSalaryDepartment(localDate);
        Map<String, Object> map = new HashMap<>();
        List<String> name = new ArrayList<>();
        List<Double> value = new ArrayList<>();

        for(RespChartBean bean : charts) {
            name.add(bean.getName());
            value.add(bean.getAverage());
        }
        map.put("name",name);
        map.put("value",value);
        return map;
    }

    @ApiOperation(value = "职位薪资统计")
    @GetMapping("/salary/position")
    public Map<String, Object> getSalaryPosition(String localDate) {
        List<RespChartBean> charts = employeeService.getSalaryPosition(localDate);
        Map<String, Object> map = new HashMap<>();
        List<String> name = new ArrayList<>();
        List<Double>  value = new ArrayList<>();

        for(RespChartBean bean : charts) {
            name.add(bean.getName());
            value.add(bean.getAverage());
        }
        map.put("name",name);
        map.put("value",value);
        return map;
    }

}
