package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.*;
import com.example.rbac.utils.SalaryUtils;
import com.example.rbac.utils.ScoreUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * 薪资管理-工资表管理
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/salary/table")
public class SalaryTableController {
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ISalaryTableService salaryTableService;
    @Autowired
    private IEmployeeEcService employeeEcService;

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salary")
    public List<Salary> getAllSalary() {
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department")
    public List<Department> getAllDepartment() {
        return departmentService.list();
    }

    @OperationLogAnnotation(operModul = "工资表管理",operType = "查询",operDesc = "获取所有员工当月工资信息")
    @ApiOperation(value = "获取所有员工当月工资信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeWithSalaryTable(@RequestParam(defaultValue = "1") Integer currentPage,
                                                      @RequestParam(defaultValue = "10") Integer size,
                                                      Integer depId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDateTime localDate = LocalDateTime.now();
        return salaryTableService.getAllSalaryTableByCurrentMonth(currentPage, size, depId, formatter.format(localDate));
//        return employeeService.getAllEmployeeWithSalaryTable(currentPage, size, depId, formatter.format(localDate));
    }

    @OperationLogAnnotation(operModul = "工资表管理",operType = "更新",operDesc = "更新员工工资")
    @ApiOperation(value = "更新员工工资(实际上是修改员工账套)")
    @PutMapping("/")
    public RespBean updateSalaryTable(Integer employeeId, Integer salaryId) {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        SalaryTable salaryTable = salaryTableService.getOne(new QueryWrapper<SalaryTable>().eq("employee_id", employeeId)
                .between("date", localDate.with(TemporalAdjusters.firstDayOfMonth()),localDate.with(TemporalAdjusters.lastDayOfMonth())));
        if(salaryTable.getEnabled()) {
            return RespBean.error("账单已锁定,不可修改");
        } else {
            Employee employee = employeeService.getById(employeeId);
            employee.setSalaryId(salaryId);

            Salary salary = salaryService.getById(salaryId);
            double baseSalary = SalaryUtils.getSalary(salary);

            Integer score = employeeEcService.getScoreByEmployeeId(salaryTable.getEmployeeId(), formatter.format(localDate));
            double bonus = baseSalary * 0.1 * ScoreUtils.getScoreGrade(score);

            salaryTable.setBonus(bonus);
            salaryTable.setAllSalary(baseSalary + bonus);
            if(employeeService.updateById(employee) && salaryTableService.updateById(salaryTable)) {
                return RespBean.success("更新成功");
            } else {
                return RespBean.error("更新失败");
            }
        }
    }

}
