package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.util.List;

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

    /**
     * 通过用户id查询资源列表
     * @return
     */
    @Override
    public List<Resource> getResourcesByUserId() {
        Integer userId;
        List<Resource> resources = null;
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        userId = UserUtils.getCurrentUser().getId();
        //从redis中获取菜单数据
        resources = (List) valueOperations.get("menu_admin_" + userId);
        if(CollectionUtils.isEmpty(resources)) {
            resources = resourceMapper.getResourcesByAdminId(userId);
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
     * 根据path获取当前页面下可执行的操作
     * @param currentActivePath
     * @return
     */
    @Override
    public List<Resource> getActionResourceByPath(String currentActivePath) {
        Integer parentId = resourceMapper.selectOne(new QueryWrapper<Resource>().eq("path",currentActivePath)).getId();
        Integer userId = UserUtils.getCurrentUser().getId();
        return resourceMapper.getAdminActionByPath(userId, parentId);
    }

    /**
     * 获取所有资源
     * @return
     */
    @Override
    public List<Resource> getAllResources() {
        return resourceMapper.getAllResources(-1);
    }

    @Override
    public List<Resource> getParentResource() {
        return resourceMapper.getParentResource(null);
    }
}
