package com.example.rbac.config.security;

import com.example.rbac.config.security.component.*;
import com.example.rbac.pojo.Admin;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSessionListener;

/**
 * @Author suj
 * @create 2022/1/5
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

//    @Autowired
//    private IUserService userService;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomerUrlDecisionManager customerUrlDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/ws/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //修改session策略
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                //动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customerUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and()
                .headers()
                .cacheControl();


        //添加登录授权过滤器
        http.addFilterBefore(jwtAuthorizationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .authenticationEntryPoint(restAuthorizationEntryPoint)
                .accessDeniedHandler(restfulAccessDeniedHandler);

        // 配置spring security
        http.sessionManagement()
                .maximumSessions(1) // 控制并发的数量
                .maxSessionsPreventsLogin(false) // 如果并发登录，不允许后面的登录，必须等到前一个登录退出来
                .sessionRegistry(sessionRegistry());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        return username -> {
//            User user = userService.getUserByUserName(username);
//            if(null != user){
//                user.setRoles(userService.getRoles(user.getId()));
//                System.out.println(user);
//                return user;
//            }
////            throw new UsernameNotFoundException("用户名或密码不正确");
//            return null;
//        };
//    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
                Admin admin = adminService.getAdminByUserName(username);
                if(null != admin){
                    admin.setRoles(adminService.getRolesByAdminId(admin.getId()));
                    return admin;
                }
            return null;
        };
    }

    @Bean
    public JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter(){
        return new JwtAuthorizationTokenFilter();
    }

    //统计在线用户
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
