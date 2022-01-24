package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.rbac.pojo.*;
import com.example.rbac.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author suj
 * @create 2022/1/6
 *
 * 权限组
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRoleResourceService roleResourceService;

    @Autowired
    private IEmployeeRoleService employeeRoleService;
    @Autowired
    private IAdminRoleService adminRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public RespBean deleteRole(@PathVariable Integer id){
        List<AdminRole> adminRoles = adminRoleService.list(new QueryWrapper<AdminRole>().eq("role_id", id));
        if(adminRoles.size() > 0) {
            return RespBean.error("该数据有关联数据,操作失败!");
        }
        List<EmployeeRole> employeeRoles = employeeRoleService.list(new QueryWrapper<EmployeeRole>().eq("role_id", id));
        if(employeeRoles.size() > 0) {
            return RespBean.error("该数据有关联数据,操作失败!");
        }
        if(roleService.removeById(id)){
            roleResourceService.remove(new QueryWrapper<RoleResource>().eq("role_id",id));
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "获取所有资源")
    @GetMapping("/resources")
    public List<Resource> getAllResources(){
        return resourceService.getAllResources();
    }

    @ApiOperation(value = "通过角色id获取资源id")
    @GetMapping("/resId/{rid}")
    public List<Integer> getResIdByRoleId(@PathVariable Integer rid){
        return roleResourceService.list(new QueryWrapper<RoleResource>().eq("role_id",rid))
                .stream().map(RoleResource::getResourceId).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单列表")
    @PutMapping("/")
    public RespBean updateRoleResource(Integer roleId, Integer[] ids){
        return roleResourceService.updateRoleResource(roleId, ids);
    }
}
