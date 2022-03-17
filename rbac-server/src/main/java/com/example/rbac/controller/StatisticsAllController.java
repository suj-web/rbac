package com.example.rbac.controller;

import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IAttendanceService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @ApiOperation(value = "出勤信息统计")
    @GetMapping("/attendance/list")
    public RespPageBean getAllAttendance(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         String localDate, Boolean absenteeism) {
        return attendanceService.getAllAttendance(currentPage, size, localDate, absenteeism);
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
}
