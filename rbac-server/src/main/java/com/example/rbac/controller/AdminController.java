package com.example.rbac.controller;


import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.Role;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author suj
 * @since 2022-01-07
 *
 * 操作员管理
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    @OperationLogAnnotation(operModul = "系统操作模块-操作员操作",operType = "查询",operDesc = "查询所有操作员信息")
    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
    }

    @OperationLogAnnotation(operModul = "系统操作模块-操作员操作",operType = "更新",operDesc = "更新操作员信息")
    @ApiOperation(value = "更新操作员")
    @PutMapping("/")
    public RespBean updateAdmin(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @OperationLogAnnotation(operModul = "系统操作模块-操作员操作",operType = "删除",operDesc = "删除操作员信息")
    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        if (adminService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/role")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @OperationLogAnnotation(operModul = "系统操作模块-操作员操作",operType = "更新",operDesc = "更新操作员角色")
    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        return adminService.updateAdminRole(adminId, rids);
    }
}
