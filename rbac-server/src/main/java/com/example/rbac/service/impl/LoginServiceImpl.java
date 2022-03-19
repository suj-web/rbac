package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.rbac.config.security.component.JwtTokenUtil;
import com.example.rbac.pojo.*;
import com.example.rbac.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author suj
 * @create 2022/1/8
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String)request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码错误,请重新输入");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }

        if(!userDetails.isEnabled()){
            return RespBean.error("账号已被禁用, 请联系管理员");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);



        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    /**
     * 获取当前管理员信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminInfo(String username) {
        return (Admin)userDetailsService.loadUserByUsername(username);
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public Employee getEmployeeInfo(String username) {
        return (Employee) userDetailsService.loadUserByUsername(username);
    }
}
