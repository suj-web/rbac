package com.example.rbac.config.security.component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IAdminService;
import com.example.rbac.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * jwt登录授权过滤器
 */
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(tokenHeader);
        //存在token
        if(null!=authHeader && authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring(tokenHead.length()+1);
            String username = jwtTokenUtil.getUserNameFormToken(authToken);
            //token存在用户名,但未登录
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证token是否有效,重新设置用户对象
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    ValueOperations valueOperations = redisTemplate.opsForValue();
                    String redisToken = (String)valueOperations.get("token_admin_" + ((Admin)userDetails).getId());
                    if(redisToken != null && redisToken.equals("")) {
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        httpServletResponse.setContentType("application/json");
                        PrintWriter out = null;
                        try {
                            out = httpServletResponse.getWriter();
                            RespBean bean = RespBean.error("用户被强制退出");
                            bean.setCode(406);
                            out.write(new ObjectMapper().writeValueAsString(bean));
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if(out != null) {
                                out.close();
                            }
                        }
                    } else if(redisToken != null && !authToken.equals(redisToken)) {
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        httpServletResponse.setContentType("application/json");
                        PrintWriter out = null;
                        try {
                            out = httpServletResponse.getWriter();
                            RespBean bean = RespBean.error("已被其他用户登录");
                            bean.setCode(402);
                            out.write(new ObjectMapper().writeValueAsString(bean));
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if(out != null) {
                                out.close();
                            }
                        }
                    }
                }
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
