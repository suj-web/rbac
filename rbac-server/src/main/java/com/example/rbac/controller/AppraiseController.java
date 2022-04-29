package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IAppraiseService;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 *人事管理-员工考评
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/personnel/appraise")
public class AppraiseController {

    @Autowired
    private IAppraiseService appraiseService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @OperationLogAnnotation(operModul = "员工考评",operType = "查询",operDesc = "查询员工考评信息")
    @ApiOperation(value = "查询员工考评信息")
    @GetMapping("/")
    public RespPageBean getAllAppraises(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        String name, Integer depId, String localDate) {
        return appraiseService.getAllAppraise(currentPage, size, name, localDate, depId);
    }

    @OperationLogAnnotation(operModul = "员工考评",operType = "添加",operDesc = "添加员工考评信息")
    @ApiOperation(value = "添加员工考评信息")
    @PostMapping("/")
    public RespBean addEmployeeTrain(@RequestBody Appraise appraise) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", appraise.getEmployee().getWorkId()).eq("name", appraise.getEmployee().getName()));
        if(null != list && 1 == list.size() && "离职".equals(list.get(0).getWorkState())){
            return RespBean.error("该员工已离职");
        }
        if(null != list && 1 == list.size()) {
            appraise.setEmployeeId(list.get(0).getId());
            if (appraiseService.save(appraise)) {
                return RespBean.success("添加成功");
            }
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "员工考评",operType = "更新",operDesc = "更新员工考评信息")
    @ApiOperation(value = "更新员工考评信息")
    @PutMapping("/")
    public RespBean updateEmployeeTrain(@RequestBody Appraise appraise) {
        List<Employee> list = employeeService.list(new QueryWrapper<Employee>().eq("work_id", appraise.getEmployee().getWorkId()).eq("name", appraise.getEmployee().getName()));
        if(null != list && 1 == list.size()) {
            if (appraiseService.updateById(appraise)) {
                return RespBean.success("更新成功");
            }
        }
        return RespBean.error("更新失败");
    }

    @OperationLogAnnotation(operModul = "员工培训",operType = "删除",operDesc = "删除员工培训信息")
    @ApiOperation(value = "删除员工培训信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeTrain(@PathVariable Integer id) {
        if(appraiseService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "员工培训",operType = "删除",operDesc = "批量删除员工培训信息")
    @ApiOperation(value = "批量删除员工培训信息")
    @DeleteMapping("/")
    public RespBean deleteManyEmployeeTrain(Integer[] ids) {
        if(appraiseService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
