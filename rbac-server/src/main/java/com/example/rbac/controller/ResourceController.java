package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Resource;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RoleResource;
import com.example.rbac.service.IResourceService;
import com.example.rbac.service.IRoleResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * 系统管理-系统管理-菜单管理
 * @author suj
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/system/cfg")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IRoleResourceService roleResourceService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "通过用户id查询资源列表")
    @GetMapping("/resource")
    public List<Resource> getResourcesByUserId(){
        return resourceService.getResourcesByUserId();
    }

    @ApiOperation(value = "查询当前用户按钮权限")
    @GetMapping("/action")
    public List<String> getActionsByUserId() {
        return resourceService.getActionsByUserId();
    }

    @ApiOperation(value = "获取所有资源(菜单管理)")
    @GetMapping("/resources")
    public List<Resource> getResources(){
        return resourceService.getResources();
    }

    @ApiOperation(value = "添加资源")
    @PostMapping("/resource")
    public RespBean addResource(@RequestBody Resource resource) {
        if(resourceService.save(resource)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "修改资源")
    @PutMapping("/resource")
    public RespBean updateResource(@RequestBody Resource resource) {
        if(resourceService.updateById(resource)) {
            redisTemplate.delete(redisTemplate.keys("menu_admin_*"));
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除资源")
    @DeleteMapping("/resource/{id}")
    public RespBean deleteResource(@PathVariable Integer id) {
        int size = roleResourceService.list(new QueryWrapper<RoleResource>().eq("resource_id", id)).size();
        int children = resourceService.list(new QueryWrapper<Resource>().eq("parent_id",id).eq("enabled",true)).size();
        if(size > 0 || children > 0) {
            return RespBean.error("该数据有关联数据,操作失败!");
        } else {
            if (resourceService.removeById(id)) {
                redisTemplate.delete(redisTemplate.keys("menu_admin_*"));
                return RespBean.success("删除成功");
            }
        }
        return RespBean.error("删除失败");
    }
}
