package com.example.rbac.service;

import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-05
 */
public interface IRoleResourceService extends IService<RoleResource> {

    /**
     * 更新菜单列表
     * @param roleId
     * @param ids
     * @return
     */
    RespBean updateRoleResource(Integer roleId, Integer[] ids);
}
