package com.example.rbac.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IEcRuleService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ILoginLogService;
import com.example.rbac.utils.ClientUtils;
import com.example.rbac.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ILoginLogService loginLogService;

    @ApiOperation(value = "在职员工数量")
    @GetMapping("/employee/count")
    public Integer getEmployeeCount() {
        return employeeService.list(new QueryWrapper<Employee>().eq("work_state","在职")).size();
    }

    @ApiOperation(value = "合同到期提醒")
    @GetMapping("/contract/expire")
    public RespPageBean getContractExpire(@RequestParam(defaultValue = "1") Integer currentPage,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getContractExpire(currentPage, size);
    }

    @ApiOperation(value = "合同到期数量")
    @GetMapping("/contract/expire/count")
    public Integer getContractExpireCount() {
        return employeeService.getContractExpireCount();
    }

    @ApiOperation(value = "生日提醒")
    @GetMapping("/birthday/remind")
    public RespPageBean getBirthdayRemind(@RequestParam(defaultValue = "1") Integer currentPage,
                                @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getBirthdayRemind(currentPage, size);
    }

    @ApiOperation(value = "生日提醒数量")
    @GetMapping("/birthday/remind/count")
    public Integer getBirthdayRemindCount() {
        return employeeService.getBirthdayRemindCount();
    }

    @ApiOperation(value = "员工转正提醒")
    @GetMapping("/conversion/remind")
    public RespPageBean getConversionRemind(@RequestParam(defaultValue = "1") Integer currentPage,
                                             @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getConversionRemind(currentPage, size);
    }

    @ApiOperation(value = "员工转正提醒数量")
    @GetMapping("/conversion/remind/count")
    public Integer getConversionRemindCount() {
        return employeeService.getConversionRemindCount();
    }


    @ApiOperation(value = "在线人数")
    @GetMapping("/online/count")
    public Integer getOnlineCount() {
        List<String> list = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());
        return list.size();
    }

    @ApiOperation(value = "在线用户")
    @GetMapping("/online/user")
    public List<OnlineUser> getOnlineUser(HttpServletRequest request) {
        List<OnlineUser> users = new ArrayList<>();
        //获取principals
        List<Object> principals = sessionRegistry.getAllPrincipals();

        System.out.println(principals);
        for (Object o : principals) {
            List<SessionInformation> sessionInformation = sessionRegistry.getAllSessions(o, false);
            OnlineUser user = new OnlineUser();
            user.setLoginName(UserUtils.getCurrentUser().getUsername());
            user.setIp(ClientUtils.getIpAddress(request));
            user.setBrowser(ClientUtils.getBrowserType(request));
            user.setOs(ClientUtils.getOs(request));
            user.setAddress(ClientUtils.getAddress(request));
            user.setSessionId(sessionInformation.get(0).getSessionId());
            users.add(user);
        }
        return users;
    }

    @ApiOperation(value = "查询登录日志")
    @GetMapping("/login/log")
    public RespPageBean getLoginLogs(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     LoginLog loginLog, LocalDateTime[] loginDateTimeScope) {
        return loginLogService.getLoginLogs(currentPage, size, loginLog, loginDateTimeScope);
    }

    @OperationLogAnnotation(operModul = "首页展示",operType = "删除",operDesc = "删除登录日志")
    @ApiOperation(value = "删除登录日志")
    @DeleteMapping("/login/log")
    public RespBean deleteLoginLog(Integer[] logIds) {
        if(loginLogService.removeByIds(Arrays.asList(logIds))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "首页展示",operType = "导出",operDesc = "导出登录日志")
    @ApiOperation(value = "导出登录日志")
    @GetMapping(value = "/login/log/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        List<LoginLog> list = loginLogService.list();
        ExportParams params = new ExportParams("登录日志表","登录日志表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, LoginLog.class, list);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("登录日志表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
