package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.IsAdmin;
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

    @Autowired
    private IsAdmin isAdmin;
    /**
     * 通过用户id查询资源列表
     * @return
     */
    @Override
    public List<Resource> getResourcesByUserId() {
        Integer userId;
        List<Resource> resources;
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if(isAdmin.getIsAdmin()) {
            userId = ((Admin)UserUtils.getCurrentUser()).getId();
            //从redis中获取菜单数据
            resources = (List) valueOperations.get("menu_admin_" + userId);
            if(CollectionUtils.isEmpty(resources)) {
                resources = resourceMapper.getResourcesByAdminId(userId);
                valueOperations.set("menu_admin_" + userId, resources);
            }
        } else {
            userId = ((Employee) UserUtils.getCurrentUser()).getId();
            resources = (List) valueOperations.get("menu_employee_" + userId);
            if(CollectionUtils.isEmpty(resources)) {
                resources = resourceMapper.getResourcesByEmployeeId(userId);
                valueOperations.set("menu_employee_"+userId,resources);
            }
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
        Integer userId;
        Integer parentId = resourceMapper.selectOne(new QueryWrapper<Resource>().eq("path",currentActivePath)).getId();
        if(isAdmin.getIsAdmin()){
            userId = ((Admin)UserUtils.getCurrentUser()).getId();
            return resourceMapper.getAdminActionByPath(userId, parentId);
        } else {
            userId = ((Employee)UserUtils.getCurrentUser()).getId();
            return resourceMapper.getEmployeeActionByPath(userId, parentId);
        }
    }

    /**
     * 获取所有资源
     * @return
     */
    @Override
    public List<Resource> getAllResources() {
        return resourceMapper.getAllResources(-1);
    }
}
