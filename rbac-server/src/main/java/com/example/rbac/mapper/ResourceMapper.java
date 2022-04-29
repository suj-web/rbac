package com.example.rbac.mapper;

import com.example.rbac.pojo.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-05
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 通过管理员id查询资源列表(菜单级别)
     * @param adminId
     * @return
     */
    List<Resource> getResourcesByAdminId(Integer adminId);

//    /**
//     * 通过员工id查询资源列表
//     * @param employeeId
//     * @return
//     */
//    List<Resource> getResourcesByEmployeeId(Integer employeeId);

    /**
     * 根据角色获取资源
     * @return
     */
    List<Resource> getResourcesWithRole();

    /**
     * 获取所有资源
     * @return
     */
    List<Resource> getAllResources(Integer parentId);

//    /**
//     * 根据userId和parentId获取管理员当前页面下可执行的操作
//     * @param userId
//     * @param parentId
//     * @return
//     */
//    List<Resource> getAdminActionByPath(@Param("userId") Integer userId, @Param("parentId") Integer parentId);

    List<Resource> getParentResource(Integer parentId);

    /**
     * 获取所有资源(菜单管理)
     * @param parentId
     * @return
     */
    List<Resource> getResources(Integer parentId);

    /**
     * 根据管理员id查询用户按钮权限
     * @param userId
     * @return
     */
    List<Resource> getActionsByUserId(Integer userId);

//    /**
//     * 根据userId和parentId获取员工当前页面下可执行的操作
//     * @param userId
//     * @param parentId
//     * @return
//     */
//    List<Resource> getEmployeeActionByPath(@Param("userId") Integer userId,@Param("parentId") Integer parentId);
}
