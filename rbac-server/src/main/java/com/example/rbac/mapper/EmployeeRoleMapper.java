package com.example.rbac.mapper;

import com.example.rbac.pojo.EmployeeRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-20
 */
@Repository
public interface EmployeeRoleMapper extends BaseMapper<EmployeeRole> {

    /**
     * 添加员工角色
     * @param employeeId
     * @param ids
     * @return
     */
    Integer insertEmployeeRole(Integer employeeId, Integer[] ids);
}
