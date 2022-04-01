package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.ISysMsgService;
import com.example.rbac.utils.ClientUtils;
import com.example.rbac.utils.UserUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 系统管理-系统管理
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/system/cfg")
public class SystemCfgController {

    @Autowired
    private ISysMsgService sysMsgService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "查询系统公告(用于首页轮播)")
    @GetMapping("/system/message")
    public List<SysMsg> getSysMsg() {
        return sysMsgService.list(new QueryWrapper<SysMsg>().eq("enabled", true));
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "查询", operDesc = "查询系统公告")
    @ApiOperation(value = "查询系统公告")
    @GetMapping("/system/message/list")
    public RespPageBean getAllSystemMessage(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            SysMsg sysMsg) {
        return sysMsgService.getAllSystemMessage(currentPage, size, sysMsg);
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "添加", operDesc = "添加系统公告")
    @ApiOperation(value = "添加系统公告")
    @PostMapping("/system/message")
    public RespBean addSystemMessage(@RequestBody SysMsg sysMsg) {
        sysMsg.setCreator(UserUtils.getCurrentUser().getUsername());
        if(sysMsgService.save(sysMsg)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "更新", operDesc = "更新系统公告")
    @ApiOperation(value = "更新系统公告")
    @PutMapping("/system/message")
    public RespBean updateSystemMessage(@RequestBody SysMsg sysMsg) {
        if(sysMsgService.updateById(sysMsg)) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "删除", operDesc = "删除系统公告")
    @ApiOperation(value = "删除系统公告")
    @DeleteMapping("/system/message/{id}")
    public RespBean deleteSystemMessage(@PathVariable Integer id) {
        if(sysMsgService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-系统管理", operType = "删除", operDesc = "批量删除系统公告")
    @ApiOperation(value = "批量删除系统公告")
    @DeleteMapping("/system/message")
    public RespBean deleteSystemMessage(Integer[] ids) {
        if(sysMsgService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    //资源 增删改查(菜单管理)

    @ApiOperation(value = "在线用户")
    @GetMapping("/online/user")
    public List<OnlineUser> getOnlineCount(HttpServletRequest request) {
        List<OnlineUser> users = new ArrayList<>();
        //获取principals
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object o : principals) {
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(o, false);
            if (!(o instanceof Admin)) {
                continue;
            }

            OnlineUser user = new OnlineUser();

            user.setLoginTime(Instant.ofEpochMilli(sessionInformations.get(0).getLastRequest().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            user.setLoginName(((Admin) o).getUsername());

            SessionInformation sessionInformation = sessionInformations.get(sessionInformations.size()-1);
            user.setIp(ClientUtils.getIpAddress(request));
            user.setBrowser(ClientUtils.getBrowserType(request));
            user.setOs(ClientUtils.getOs(request));
            user.setAddress(ClientUtils.getAddress(request));
            user.setSessionId(sessionInformation.getSessionId());
            user.setLastRequestTime(Instant.ofEpochMilli(sessionInformation.getLastRequest().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime());
            users.add(user);
        }
        return users;
    }


    @OperationLogAnnotation(operModul = "在线用户",operType = "强退",operDesc = "强制退出用户")
    @ApiOperation(value = "强制退出")
    @PutMapping("/online/quit")
    public RespBean onlineQuit(String username) {
        //移除用户的session
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object o : principals) {
            if (!(o instanceof Admin)) {
                continue;
            }
            if(((Admin) o).getUsername().equals(username)) {
                List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(o, false);
                for(SessionInformation sessionInformation: sessionInformations) {
                    sessionInformation.expireNow();
                    sessionRegistry.removeSessionInformation(sessionInformation.getSessionId());
                }
            }
        }

        //将redis中的token设置为""
        String currentUserName = UserUtils.getCurrentUser().getUsername();
        if(null != username && !username.equals(currentUserName)) {
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer adminId = adminService.getAdminByUserName(username).getId();
            if (null != valueOperations.get("token_admin_" + adminId)) {
                valueOperations.set("token_admin_" + adminId, "");
            }
            return RespBean.success("操作成功");
        }
        return RespBean.error("当前登录用户无法强退");
    }
}
