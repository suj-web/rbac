package com.example.rbac.utils;

import com.example.rbac.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author suj
 * @create 2022/1/5
 */
public class UserUtils {

    /**
     * 获取当前登录用户
     */
    public static Admin getCurrentUser(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
