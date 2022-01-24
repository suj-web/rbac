package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RoleResource;
import com.example.rbac.mapper.RoleResourceMapper;
import com.example.rbac.service.IRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-05
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 更新菜单列表
     * @param roleId
     * @param ids
     * @return
     */
    @Override
    public RespBean updateRoleResource(Integer roleId, Integer[] ids) {
        roleResourceMapper.delete(new QueryWrapper<RoleResource>().eq("role_id",roleId));
        if(null == ids || 0 == ids.length){
            redisTemplate.delete(redisTemplate.keys("menu_*"));
            return RespBean.success("更新成功");
        }
        Integer result = roleResourceMapper.insertRecord(roleId, ids);
        if(result == ids.length){
            redisTemplate.delete(redisTemplate.keys("menu_*"));
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
