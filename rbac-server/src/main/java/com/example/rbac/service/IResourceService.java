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
     * 通过用户id(管理员id， 员工id)查询资源列表
     * @return
     */
    List<Resource> getResourcesByUserId();

    /**
     * 根据角色获取资源
     * @return
     */
    List<Resource> getResourcesWithRole();

//    /**
//     * 通过组件path查询当前页面下的可执行的操作资源
//     * @param currentActivePath
//     * @return
//     */
//    List<String> getActionResourceByPath(String currentActivePath);

    /**
     * 获取所有资源
     * @return
     */
    List<Resource> getAllResources();

//    List<Resource> getParentResource();

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

//    /**
//     * 根据角色id获取对应的资源id
//     * @param rid
//     * @return
//     */
//    List<Integer> getResIdsByRoleId(Integer rid);
//
//
//    List<RespResIdsBean> getResIdByRoleId();

    /**
     * 获取所有角色对应资源id
     * @return
     */
    List<RespResIdsBean> getResIdsWithRoleId();
}
