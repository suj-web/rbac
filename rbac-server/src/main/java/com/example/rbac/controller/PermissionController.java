package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统管理-基础信息设置-权限组
 * @Author suj
 * @create 2022/1/6
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
    private IAdminRoleService adminRoleService;

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-权限组",operType = "添加",operDesc = "添加角色")
    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role) {
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-权限组",operType = "删除",operDesc = "删除角色")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{id}")
    public RespBean deleteRole(@PathVariable Integer id){
        List<AdminRole> adminRoles = adminRoleService.list(new QueryWrapper<AdminRole>().eq("role_id", id));
        if(adminRoles.size() > 0) {
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

    @ApiOperation(value = "获取所有资源id")
    @GetMapping("/resId")
    public List<Integer> getAllResId() {
        return resourceService.list().stream().map(Resource::getId).collect(Collectors.toList());
    }

    @ApiOperation(value = "获取所有父级资源")
    @GetMapping("/parent")
    public List<Resource> getParentResource() {
        return resourceService.getParentResource();
    }


    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-权限组",operType = "更新",operDesc = "更新角色菜单列表")
    @ApiOperation(value = "更新角色菜单列表")
    @PutMapping("/")
    public RespBean updateRoleResource(Integer roleId, Integer[] ids){
        return roleResourceService.updateRoleResource(roleId, ids);
    }
}
