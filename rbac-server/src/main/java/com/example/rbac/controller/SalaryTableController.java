package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryService;
import com.example.rbac.service.ISalaryTableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @OperationLogAnnotation(operModul = "薪资管理-工资表管理",operType = "查询",operDesc = "获取所有员工当月工资信息")
    @ApiOperation(value = "获取所有员工当月工资信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeWithSalaryTable(@RequestParam(defaultValue = "1") Integer currentPage,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          Integer depId) {
        return employeeService.getAllEmployeeWithSalaryTable(currentPage, size, depId);
    }

    @OperationLogAnnotation(operModul = "薪资管理-工资表管理",operType = "更新",operDesc = "更新员工工资")
    @ApiOperation(value = "更新员工工资(实际上是修改员工账套)")
    @PutMapping("/")
    public RespBean updateSalaryTable(Integer employeeId, Integer salaryId) {
        SalaryTable salaryTable = salaryTableService.getOne(new QueryWrapper<SalaryTable>().eq("employee_id", employeeId)
                .eq("year", LocalDate.now().getYear())
                .eq("month", LocalDate.now().getMonthValue()));
        if(salaryTable.getEnabled()) {
            return RespBean.error("账单已锁定,不可修改");
        } else {
            Employee employee = employeeService.getById(employeeId);
            employee.setSalaryId(salaryId);
            Salary salary = salaryService.getById(salaryId);
            double allSalary = salary.getBasicSalary() + salary.getLunchSalary()
                    + salary.getTrafficSalary() + salary.getPensionBase()
                    * salary.getPensionPer() + salary.getMedicalBase()
                    * salary.getMedicalPer() + salary.getAccumulationFundBase()
                    * salary.getAccumulationFundPer() + salaryTable.getBonus();
            salaryTable.setAllSalary(allSalary);
            if(employeeService.updateById(employee) && salaryTableService.updateById(salaryTable)) {
                return RespBean.success("更新成功");
            } else {
                return RespBean.error("更新失败");
            }
        }
    }

}
