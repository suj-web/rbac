package com.example.rbac.service;

import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author suj
 * @create 2022/1/8
 */
public interface LoginService {


    /**
     * 用户登录
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 获取当前管理员信息
     * @param username
     * @return
     */
    Admin getAdminInfo(String username);

    /**
     * 获取当前员工信息
     * @param username
     * @return
     */
    Employee getEmployeeInfo(String username);
}
