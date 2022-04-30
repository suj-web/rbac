package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.mapper.RoleMapper;
import com.example.rbac.mapper.RoleResourceMapper;
import com.example.rbac.pojo.RespResIdsBean;
import com.example.rbac.pojo.Role;
import com.example.rbac.pojo.RoleResource;
import com.example.rbac.utils.UserUtils;
import com.example.rbac.pojo.Resource;
import com.example.rbac.mapper.ResourceMapper;
import com.example.rbac.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-05
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 通过用户id查询资源列表
     * @return
     */
    @Override
    public List<Resource> getResourcesByUserId() {
        List<Resource> resources = null;
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        Integer userId = UserUtils.getCurrentUser().getId();
        //从redis中获取菜单数据
        resources = (List) valueOperations.get("menu_admin_" + userId);
        if(CollectionUtils.isEmpty(resources)) {
            resources = resourceMapper.getResourcesByAdminId(userId, 1);
            valueOperations.set("menu_admin_" + userId, resources);
        }
        return resources;
    }

    /**
     * 根据角色获取资源
     * @return
     */
    @Override
    public List<Resource> getResourcesWithRole() {
        return resourceMapper.getResourcesWithRole();
    }

    /**
     * 查询当前用户按钮权限
     * @return
     */
    @Override
    public List<String> getActionsByUserId() {
        Integer userId = UserUtils.getCurrentUser().getId();
        return resourceMapper.getActionsByUserId(userId).stream().map(Resource::getComponent).collect(Collectors.toList());
    }

    /**
     * 获取所有资源(权限组)
     * @return
     */
    @Override
    public List<Resource> getAllResources() {
        return resourceMapper.getAllResources(-1);
    }

    /**
     * 获取所有资源(菜单管理)
     * @return
     */
    @Override
    public List<Resource> getResources() {
        return resourceMapper.getResources(-1);
    }

    @Override
    public List<RespResIdsBean> getResIdsWithRoleId() {
        List<Role> roles = roleMapper.selectList(null);
        List<RespResIdsBean> respBeans = new ArrayList<>();
        for(Role role: roles) {
            RespResIdsBean bean = new RespResIdsBean();
            bean.setRoleId(role.getId());
            List<Integer> ids = new ArrayList<>();
            List<RoleResource> roleResources = roleResourceMapper.selectList(new QueryWrapper<RoleResource>().eq("role_id", role.getId()));
            List<Integer> roleResIds = roleResources.stream().map(RoleResource::getResourceId).collect(Collectors.toList());
            for(RoleResource roleResource: roleResources) {
                Resource resource = resourceMapper.selectOne(new QueryWrapper<Resource>().eq("id", roleResource.getResourceId()));
                if(null != resource) {
                    if(resource.getType() == 1) {
                        List<Integer> actionIds = resourceMapper.selectList(new QueryWrapper<Resource>().eq("parent_id", resource.getId()).eq("type", 2))
                                .stream().map(Resource::getId).collect(Collectors.toList());
                        if (0 == actionIds.size()) {
                            ids.add(resource.getId());
                        } else {
                            actionIds.retainAll(roleResIds);
                            ids.addAll(actionIds);
                        }
                    } else if(resource.getType() == 0) {
                        List<Resource> children = resourceMapper.selectList(new QueryWrapper<Resource>().eq("parent_id", resource.getId()));
                        if(children.size() == 0) {
                            ids.add(resource.getId());
                        }
                    }
                }
            }
            ids = ids.stream().sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
            bean.setResIds(ids);
            respBeans.add(bean);
        }
        return respBeans;
    }
}
