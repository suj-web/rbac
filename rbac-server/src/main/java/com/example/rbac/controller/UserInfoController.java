package com.example.rbac.controller;

import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.IsAdmin;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.utils.FastDFSUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author suj
 * @create 2022/1/12
 *
 * 当前登录用户
 */
@RestController
public class UserInfoController {
    @Autowired
    private IAdminService adminService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IsAdmin isAdmin;

    @ApiOperation(value = "更新当前管理员信息")
    @PutMapping("/admin/info")
    public RespBean updateAdminInfo(@RequestBody Admin admin, Authentication authentication) {
        if(adminService.updateById(admin)){
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities())
            );
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "更新当前员工信息")
    @PutMapping("/employee/info")
    public RespBean updateEmployeeInfo(@RequestBody Employee employee, Authentication authentication) {
        if(employeeService.updateById(employee)){
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(employee, null, authentication.getAuthorities())
            );
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "更新用户（管理员或员工）密码")
    @PutMapping("/user/pass")
    public RespBean updateUserPass(@RequestBody Map<String, String> info) {
        String oldPass = info.get("oldPass");
        String pass = info.get("pass");
        if(isAdmin.getIsAdmin()) {
            Integer adminId = Integer.parseInt(info.get("adminId"));
            return adminService.updateAdminPassword(oldPass, pass, adminId);
        } else {
            Integer employeeId = Integer.parseInt(info.get("employeeId"));
            return employeeService.updateEmployeePassword(oldPass, pass, employeeId);
        }
    }

    @ApiOperation(value = "更新用户（管理员或员工）头像")
    @PostMapping("/user/userface")
    public RespBean updateUserFace(MultipartFile file, Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl() + filePath[0]  + "/" + filePath[1];
//        System.out.println(filePath[0]+"***"+filePath[1]);
        return isAdmin.getIsAdmin() ? adminService.updateAdminUserFace(url, id, authentication) : employeeService.updateEmployeeUserFace(url, id, authentication);
    }
}
