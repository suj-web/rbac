package com.example.rbac.controller;

import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ILoginLogService;
import com.example.rbac.service.LoginService;
import com.example.rbac.utils.ClientUtils;
import com.example.rbac.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * @Author suj
 * @create 2022/1/5
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private IAdminService adminService;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ILoginLogService loginLogService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginParam user, HttpServletRequest request) {
        RespBean respBean = loginService.login(user.getUsername(), user.getPassword(),user.getCode(),request);

        //插入登录日志
        LoginLog loginLog = new LoginLog();
        loginLog.setSessionId(request.getRequestedSessionId());
        loginLog.setName(user.getUsername());
        loginLog.setIp(ClientUtils.getIpAddress(request));
        loginLog.setAddress(ClientUtils.getAddressByApi(request));
        loginLog.setBrowser(ClientUtils.getBrowserType(request));
        loginLog.setOs(ClientUtils.getOs(request));
        if(respBean.getCode() != 200) {
            loginLog.setType(false);
        }
        loginLog.setOperInfo(respBean.getMessage());
        loginLogService.save(loginLog);

        return respBean;
    }

    @ApiOperation(value = "获取当前管理员信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if(null == principal){
            return null;
        }
        return loginService.getAdminInfo(principal.getName());
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = ((Admin) sessionRegistry.getSessionInformation(session.getId()).getPrincipal()).getUsername();
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object o : principals) {
            if (!(o instanceof Admin)) {
                continue;
            }
            if(((Admin) o).getUsername().equals(username)) {
                System.out.println(((Admin) o).getUsername());
                List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(o, false);
                for(SessionInformation sessionInformation: sessionInformations) {
                    sessionInformation.expireNow();
                    sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
                }
            }
        }
        Integer adminId = adminService.getAdminByUserName(username).getId();
        redisTemplate.delete("token_admin_" + adminId);
        return RespBean.success("注销成功");
    }
}
