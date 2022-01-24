package com.example.rbac.utils;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author suj
 * @create 2022/1/5
 */
public class UserUtils {

    /**
     * 获取当前登录用户
     */
    public static Object getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
