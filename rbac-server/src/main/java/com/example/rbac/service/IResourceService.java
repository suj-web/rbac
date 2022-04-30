package com.example.rbac.service;

import com.example.rbac.pojo.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespResIdsBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-05
 */
public interface IResourceService extends IService<Resource> {

    /**
     * 通过管理员id查询资源列表
     * @return
     */
    List<Resource> getResourcesByUserId();

    /**
     * 根据角色获取资源
     * @return
     */
    List<Resource> getResourcesWithRole();

    /**
     * 获取所有资源
     * @return
     */
    List<Resource> getAllResources();

    /**
     * 获取所有资源(菜单管理)
     * @return
     */
    List<Resource> getResources();

    /**
     * 查询当前用户按钮权限
     * @return
     */
    List<String> getActionsByUserId();

    /**
     * 获取所有角色对应资源id
     * @return
     */
    List<RespResIdsBean> getResIdsWithRoleId();
}
