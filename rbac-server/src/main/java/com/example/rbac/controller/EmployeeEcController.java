package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEcRuleService;
import com.example.rbac.service.IEmployeeEcService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 人事管理-员工奖惩
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/personnel/ec")
public class EmployeeEcController {
    @Autowired
    private IEmployeeEcService employeeEcService;

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IEcRuleService ecRuleService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "获取所有奖惩规则")
    @GetMapping("/ecRule/list")
    public List<EcRule> getAllEcRules() {
        return ecRuleService.list();
    }

    @OperationLogAnnotation(operModul = "员工奖惩",operType = "查询",operDesc = "获取所有奖惩人员信息")
    @ApiOperation(value = "获取所有奖惩人员信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeEc(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         String name, String localDate, Integer depId) {
        return employeeEcService.getAllEmployeeEc(currentPage, size, name, localDate, depId);
    }

    @OperationLogAnnotation(operModul = "员工奖惩",operType = "添加",operDesc = "添加奖惩人员信息")
    @ApiOperation(value = "添加奖惩人员信息")
    @PostMapping("/")
    public RespBean addEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", employeeEc.getEmployee().getWorkId()).eq("name", employeeEc.getEmployee().getName()));
        if(null != list && 1 == list.size()) {
            employeeEc.setEmployeeId(list.get(0).getId());
            if (employeeEcService.save(employeeEc)) {
                return RespBean.success("添加成功");
            }
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "员工奖惩",operType = "更新",operDesc = "更新奖惩人员信息")
    @ApiOperation(value = "更新奖惩人员信息")
    @PutMapping("/")
    public RespBean updateEmployeeEc(@RequestBody EmployeeEc employeeEc) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", employeeEc.getEmployee().getWorkId()).eq("name", employeeEc.getEmployee().getName()));
        if(null != list && 1 == list.size()) {
            if (employeeEcService.updateById(employeeEc)) {
                return RespBean.success("修改成功");
            }
        }
        return RespBean.error("修改失败");
    }

    @OperationLogAnnotation(operModul = "员工奖惩",operType = "删除",operDesc = "删除奖惩人员信息")
    @ApiOperation(value = "删除奖惩人员信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeEc(@PathVariable Integer id) {
        if(employeeEcService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "员工奖惩",operType = "删除",operDesc = "批量删除奖惩人员信息")
    @ApiOperation(value = "批量删除奖惩人员信息")
    @DeleteMapping("/")
    public RespBean deleteManyEmployeeEc(Integer[] ids) {
        if(employeeEcService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
