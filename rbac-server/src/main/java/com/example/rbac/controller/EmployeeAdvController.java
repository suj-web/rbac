package com.example.rbac.controller;

import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.Role;
import com.example.rbac.service.IEmployeeRoleService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 普通员工高级资料
 *
 * @Author suj
 * @create 2022/1/22
 */
@RestController
@RequestMapping("/employee/advanced")
public class EmployeeAdvController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IEmployeeRoleService employeeRoleService;

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "获取所有员工信息(带用户角色)")
    @GetMapping("/")
    public RespPageBean getEmployeeWithRoleByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       String name){
        return employeeService.getEmployeeWithRoleByPage(currentPage, size, name);
    }

    @ApiOperation(value = "获取角色信息")
    @GetMapping("/role")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "更新员工角色")
    @PutMapping("/role")
    public RespBean updateEmployeeRole(Integer employeeId, Integer[] ids) {
        return employeeService.updateEmployeeRole(employeeId, ids);
    }
}
