package com.example.rbac.config.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 自定义决策管理器
 * 判断用户角色
 * @Author suj
 * @create 2022/1/5
 */
@Component
public class CustomerUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for(ConfigAttribute configAttribute: collection){
            //当前url所需角色
            String needNode = configAttribute.getAttribute();
            //判断角色是否是登录即可访问的角色
            if("ROLE_LOGIN".equals(needNode)){
                if(authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("尚未登录，请登录");
                } else {
                    return;
                }
            }

            //判断角色是否为url所需角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for(GrantedAuthority authority : authorities){
                if(authority.getAuthority().equals(needNode)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足, 请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
