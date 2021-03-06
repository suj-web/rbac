package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.Salary;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 工资账套管理
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;

    @OperationLogAnnotation(operModul = "工资账套管理",operType = "查询",operDesc = "获取所有工资账套")
    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalaries(){
        return salaryService.list();
    }

    @OperationLogAnnotation(operModul = "工资账套管理",operType = "添加",operDesc = "添加工资账套")
    @ApiOperation(value = "添加工资账套")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        if(salaryService.save(salary)){
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @OperationLogAnnotation(operModul = "工资账套管理",operType = "更新",operDesc = "更新工资账套")
    @ApiOperation(value = "更新工资账套")
    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary){
        if(salaryService.updateById(salary)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @OperationLogAnnotation(operModul = "工资账套管理",operType = "删除",operDesc = "删除工资账套")
    @ApiOperation(value = "删除工资账套")
    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable Integer id){
        List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("salary_id", id));
        if(employees.size() > 0) {
            return RespBean.error("该数据有关联数据,操作失败!");
        }
        if(salaryService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
