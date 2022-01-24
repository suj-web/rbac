package com.example.rbac.controller;

import com.example.rbac.pojo.*;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @Author suj
 * @create 2022/1/5
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IsAdmin isAdmin;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginParam user, HttpServletRequest request) {
        isAdmin.setIsAdmin(user.getIsAdmin());
        return loginService.login(user.getUsername(), user.getPassword(),user.getCode(),request);
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/info")
    public void getUserInfo(HttpServletResponse response) throws IOException {
        if(isAdmin.getIsAdmin()) {
            response.sendRedirect("/admin/info");
        } else {
            response.sendRedirect("/employee/info");
        }
    }

    @ApiOperation(value = "获取当前管理员信息", hidden = true)
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if(null == principal){
            return null;
        }
        return loginService.getAdminInfo(principal.getName());
    }

    @ApiOperation(value = "获取当前员工信息", hidden = true)
    @GetMapping("/employee/info")
    public Employee getEmployeeInfo(Principal principal) {
        if(null == principal){
            return null;
        }
        return loginService.getEmployeeInfo(principal.getName());
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("退出成功");
    }
}
