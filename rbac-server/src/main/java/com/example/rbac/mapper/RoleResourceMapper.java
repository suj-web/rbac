package com.example.rbac.mapper;

import com.example.rbac.pojo.RoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-05
 */
@Repository
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    /**
     * 插入记录
     * @param roleId
     * @param ids
     * @return
     */
    Integer insertRecord(@Param("roleId") Integer roleId, @Param("ids") Integer[] ids);
}
