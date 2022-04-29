package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryAdjustService;
import com.example.rbac.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 人事管理-员工调薪
 * @author suj
 * @since 2022-01-12
 *
 */
@RestController
@RequestMapping("/personnel/salary")
public class SalaryAdjustController {

    @Autowired
    private ISalaryAdjustService salaryAdjustService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salary/list")
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @OperationLogAnnotation(operModul = "员工调薪",operType = "查询",operDesc = "获取所有员工调薪信息")
    @ApiOperation(value = "获取所有员工调薪信息")
    @GetMapping("/")
    public RespPageBean getAllSalaryAdjust(@RequestParam(defaultValue = "1") Integer currentPage,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           Integer depId, String localDate) {
        return salaryAdjustService.getAllSalaryAdjust(currentPage, size, depId, localDate);
    }

    @ApiOperation(value = "根据工号获取员工信息")
    @GetMapping("/employee")
    public Employee getEmployeeByWorkId(String workId) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", workId));
        if(null != list && 1 == list.size()) {
            Employee employee = new Employee();
            employee.setSalaryId(list.get(0).getSalaryId());
            employee.setName(list.get(0).getName());
            return employee;
        }
        return null;
    }

    @OperationLogAnnotation(operModul = "员工调薪",operType = "添加",operDesc = "添加员工调薪信息")
    @ApiOperation(value = "添加员工调薪信息")
    @PostMapping("/")
    @Transactional
    public RespBean addSalaryAdjust(@RequestBody SalaryAdjust salaryAdjust) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", salaryAdjust.getEmployee().getWorkId()).eq("name", salaryAdjust.getEmployee().getName()));
        if(null != list && 1 == list.size() && "离职".equals(list.get(0).getWorkState())){
            return RespBean.error("该员工已离职");
        }
        if(null != list && 1 == list.size()) {
            salaryAdjust.setEmployeeId(list.get(0).getId());
            if(salaryAdjustService.save(salaryAdjust)) {
                return RespBean.success("添加成功");
            }
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "员工调薪",operType = "更新",operDesc = "修改员工调薪信息")
    @ApiOperation(value = "修改员工调薪信息")
    @PutMapping("/")
    public RespBean updateSalaryAdjust(@RequestBody SalaryAdjust salaryAdjust) {
        salaryAdjust.setAsDate(LocalDate.now());
        if(salaryAdjustService.updateById(salaryAdjust)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @OperationLogAnnotation(operModul = "员工调薪",operType = "删除",operDesc = "删除员工调薪信息")
    @ApiOperation(value = "删除员工调薪信息")
    @DeleteMapping("/{id}")
    public RespBean deleteSalaryAdjust(@PathVariable Integer id) {
        if(salaryAdjustService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "员工调薪",operType = "删除",operDesc = "批量删除员工调薪信息")
    @ApiOperation(value = "批量删除员工调薪信息")
    @DeleteMapping("/")
    public RespBean deleteManySalaryAdjust(Integer[] ids) {
        if(salaryAdjustService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
