package com.example.rbac.mapper;

import com.example.rbac.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据管理员id获取对应角色
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);
}
