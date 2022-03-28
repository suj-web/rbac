package com.example.rbac.controller;

import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.utils.FastDFSUtils;
import com.example.rbac.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AdminInfoController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/get/info")
    public Admin getAdminInfo() {
        return UserUtils.getCurrentUser();
    }

    @OperationLogAnnotation(operModul = "用户信息",operType = "更新",operDesc = "更新当前操作员信息")
    @ApiOperation(value = "更新当前操作员信息")
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

    @OperationLogAnnotation(operModul = "用户信息",operType = "更新",operDesc = "更新当前操作员密码")
    @ApiOperation(value = "更新操作员密码")
    @PutMapping("/admin/pass")
    public RespBean updateUserPass(@RequestBody Map<String, String> info) {
        String oldPass = info.get("oldPass");
        String pass = info.get("pass");
        Integer adminId = Integer.parseInt(info.get("adminId"));
        return adminService.updateAdminPassword(oldPass, pass, adminId);
    }

    @OperationLogAnnotation(operModul = "用户信息",operType = "更新",operDesc = "更新当前操作员头像")
    @ApiOperation(value = "更新操作员头像")
    @PostMapping("/admin/userface")
    public RespBean updateUserFace(MultipartFile file, Integer id, Authentication authentication) {
        String[] filePath = FastDFSUtils.upload(file);
        String url = FastDFSUtils.getTrackerUrl() + filePath[0]  + "/" + filePath[1];
//        System.out.println(filePath[0]+"***"+filePath[1]);
        return adminService.updateAdminUserFace(url, id, authentication);
    }
}
