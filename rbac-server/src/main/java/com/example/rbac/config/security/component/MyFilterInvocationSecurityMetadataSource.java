package com.example.rbac.config.security.component;

import com.example.rbac.pojo.Resource;
import com.example.rbac.pojo.Role;
import com.example.rbac.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据url分析请求所需的角色
 * @Author suj
 * @create 2022/1/5
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IResourceService resourceService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 加载URL与权限对应关系
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //根据角色获取资源
        List<Resource> resources = resourceService.getResourcesWithRole();
        for(Resource resource : resources){
            if(resource.getUrl().equals("/")){
                continue;
            }
            if(antPathMatcher.match(resource.getUrl(),requestUrl)){
                String[] str = resource.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        //没有匹配的设置登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
