package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.IEmployeeTrainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 *人事管理-员工培训
 * @Author suj
 * @create 2022/1/24
 */
@RestController
@RequestMapping("/personnel/train")
public class EmployeeTrainController {

    @Autowired
    private IEmployeeTrainService employeeTrainService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @OperationLogAnnotation(operModul = "员工培训",operType = "查询",operDesc = "获取员工培训信息")
    @ApiOperation(value = "获取员工培训信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeTrain(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10")Integer size,
                                            String name, String localDate, Integer depId) {
        return employeeTrainService.getAllEmployeeTrain(currentPage, size, name, localDate, depId);
    }

    @OperationLogAnnotation(operModul = "员工培训",operType = "查询",operDesc = "获取员工培训信息")
    @ApiOperation(value = "添加员工培训信息")
    @PostMapping("/")
    public RespBean addEmployeeTrain(@RequestBody EmployeeTrain employeeTrain) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", employeeTrain.getEmployee().getWorkId()).eq("name", employeeTrain.getEmployee().getName()));
        if(null != list && 1 == list.size()) {
            employeeTrain.setEmployeeId(list.get(0).getId());
            if (employeeTrainService.save(employeeTrain)) {
                return RespBean.success("添加成功");
            }
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "员工培训",operType = "更新",operDesc = "更新员工培训信息")
    @ApiOperation(value = "更新员工培训信息")
    @PutMapping("/")
    public RespBean updateEmployeeTrain(@RequestBody EmployeeTrain employeeTrain) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", employeeTrain.getEmployee().getWorkId()).eq("name", employeeTrain.getEmployee().getName()));
        if(null != list && 1 == list.size()) {
            if (employeeTrainService.updateById(employeeTrain)) {
                return RespBean.success("更新成功");
            }
        }
        return RespBean.error("更新失败");
    }

    @OperationLogAnnotation(operModul = "员工培训",operType = "删除",operDesc = "删除员工培训信息")
    @ApiOperation(value = "删除员工培训信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeTrain(@PathVariable Integer id) {
        if(employeeTrainService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "员工培训",operType = "删除",operDesc = "批量删除员工培训信息")
    @ApiOperation(value = "批量删除员工培训信息")
    @DeleteMapping("/")
    public RespBean deleteManyEmployeeTrain(Integer[] ids) {
        if(employeeTrainService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
