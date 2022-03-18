package com.example.rbac.controller;

import com.example.rbac.pojo.Department;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation(value = "员工结构统计-按年龄段统计")
    @GetMapping("/composition/age")
    public List<RespChartBean> getCompositionByAge(Integer depId) {
        return employeeService.getCompositionByAge(depId);
    }

    @ApiOperation(value = "员工结构统计-按工龄统计")
    @GetMapping("/composition/workAge")
    public List<RespChartBean> getCompositionByWorkAge(Integer depId) {
        return employeeService.getCompositionByWorkAge(depId);
    }

    @ApiOperation(value = "员工结构统计-按性别统计")
    @GetMapping("/composition/gender")
    public List<RespChartBean> getCompositionByGender(Integer depId) {
        return employeeService.getCompositionByGender(depId);
    }

    @ApiOperation(value = "员工结构统计-按最高学历统计")
    @GetMapping("/composition/degree")
    public List<RespChartBean> getCompositionByDegree(Integer depId) {
        return employeeService.getCompositionByDegree(depId);
    }

    @ApiOperation(value = "员工结构统计-按聘用形式统计")
    @GetMapping("/composition/engageForm")
    public List<RespChartBean> getCompositionByEngageForm(Integer depId) {
        return employeeService.getCompositionByEngageForm(depId);
    }

    @ApiOperation(value = "员工结构统计-按在职状态统计")
    @GetMapping("/composition/workState")
    public List<RespChartBean> getCompositionByWorkState(Integer depId) {
        return employeeService.getCompositionByWorkState(depId);
    }

    @ApiOperation(value = "年龄统计-员工平均年龄统计")
    @GetMapping("/averageAge")
    public Integer getAverageAge(Integer depId) {
        Integer averageAge = employeeService.getAverageAge(depId);
        if(null != averageAge) {
            return averageAge;
        }
        return null;
    }

    @ApiOperation(value = "年龄统计-部门员工平均年龄统计")
    @GetMapping("/department/averageAge")
    public List<RespChartBean> getDepartmentAverageAge() {
        return employeeService.getDepartmentAverageAge();
    }

    @ApiOperation(value = "工龄统计-员工平均工龄统计")
    @GetMapping("/average/workAge")
    public Integer getAverageWorkAge(Integer depId) {
        return employeeService.getAverageWorkAge(depId);
    }

    @ApiOperation(value = "工龄统计-部门员工平均工龄统计")
    @GetMapping("/department/average/workAge")
    public List<RespChartBean> getDepartmentAverageWorkAge() {
        return employeeService.getDepartmentAverageWorkAge();
    }

    @ApiOperation(value = "按婚姻状况统计")
    @GetMapping("/wedLock")
    public List<RespChartBean> getWedLock(Integer depId) {
        return employeeService.getWedLock(depId);
    }

    @ApiOperation(value = "按民族进行统计")
    @GetMapping("/nation")
    public List<RespChartBean> getNation(Integer depId) {
        return employeeService.getNation(depId);
    }

    @ApiOperation(value = "按政治面貌进行统计")
    @GetMapping("/politic/status")
    public List<RespChartBean> getPoliticStatus(Integer depId) {
        return employeeService.getPoliticStatus(depId);
    }

}
