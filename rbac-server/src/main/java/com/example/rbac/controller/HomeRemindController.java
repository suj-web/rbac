package com.example.rbac.controller;

import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEcRuleService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author suj
 * @create 2022/3/18
 */
@RestController
@RequestMapping("/home/remind")
public class HomeRemindController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @ApiOperation(value = "合同到期提醒")
    @GetMapping("/contract/expire")
    public RespPageBean getContractExpire(@RequestParam(defaultValue = "1") Integer currentPage,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getContractExpire(currentPage, size);
    }

//    @ApiOperation(value = "生日提醒")
//    @GetMapping("/birthday")
//    public RespPageBean getBirthday(@RequestParam(defaultValue = "1") Integer currentPage,
//                                @RequestParam(defaultValue = "10") Integer size) {
//        return employeeService.getBirthday(currentPage, size);
//    }


    @ApiOperation(value = "在线人数")
    @GetMapping("/online/count")
    public Integer getOnlineCount() {
        return sessionRegistry.getAllPrincipals().size();
    }
}
