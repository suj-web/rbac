package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeRemoveService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 人事管理-员工调动
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/personnel/remove")
public class EmployeeRemoveController {

    @Autowired
    private IEmployeeRemoveService employeeRemoveService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/position/list")
    public List<Position> getAllPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "根据工号获取员工信息")
    @GetMapping("/employee")
    public Employee getEmployeeByWorkId(String workId) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", workId));
        if(null != list && 1 == list.size()) {
            Employee employee = new Employee();
            employee.setPositionId(list.get(0).getPositionId());
            employee.setDepartmentId(list.get(0).getDepartmentId());
            employee.setName(list.get(0).getName());
            return employee;
        }
        return null;
    }

    @OperationLogAnnotation(operModul = "员工调动",operType = "查询",operDesc = "获取员工调动信息")
    @ApiOperation(value = "获取员工调动信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeRemove(@RequestParam(defaultValue = "1") Integer currentPage,
                                             @RequestParam(defaultValue = "10") Integer size,
                                             EmployeeRemove remove, String localDate) {
        return employeeRemoveService.getAllEmployeeRemove(currentPage, size, remove, localDate);
    }

    @OperationLogAnnotation(operModul = "员工调动",operType = "添加",operDesc = "添加员工调动信息")
    @ApiOperation(value = "添加员工调动信息")
    @PostMapping("/")
    public RespBean addEmployeeRemove(@RequestBody EmployeeRemove employeeRemove) {
        return employeeRemoveService.addEmployeeRemove(employeeRemove);
    }

//    @OperationLogAnnotation(operModul = "员工调动",operType = "修改",operDesc = "修改员工调动信息")
//    @ApiOperation(value = "修改员工调动信息")
//    @PutMapping("/")
//    public RespBean updateEmployeeRemove(@RequestBody EmployeeRemove employeeRemove) {
//        return employeeRemoveService.updateEmployeeRemove(employeeRemove);
//    }

    @OperationLogAnnotation(operModul = "员工调动",operType = "删除",operDesc = "删除员工调动信息")
    @ApiOperation(value = "删除员工调动信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeRemove(@PathVariable Integer id) {
        if(employeeRemoveService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "员工调动",operType = "删除",operDesc = "批量删除员工调动信息")
    @ApiOperation(value = "批量删除员工调动信息")
    @DeleteMapping("/")
    public RespBean deleteManyEmployeeRemove(Integer[] ids) {
        if(employeeRemoveService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
