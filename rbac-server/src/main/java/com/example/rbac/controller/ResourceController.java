package com.example.rbac.controller;


import com.example.rbac.pojo.Resource;
import com.example.rbac.service.IResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * 资源控制器
 * @author suj
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/system/cfg")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @ApiOperation(value = "通过用户id查询资源列表")
    @GetMapping("/resource")
    public List<Resource> getResourcesByUserId(){
        return resourceService.getResourcesByUserId();
    }

    @ApiOperation(value = "通过path查询用户在当前页面下的可执行的操作资源")
    @GetMapping("/action")
    public List<String> getActionResourceByPath(String currentActivePath){
        return resourceService.getActionResourceByPath(currentActivePath);
    }
}
