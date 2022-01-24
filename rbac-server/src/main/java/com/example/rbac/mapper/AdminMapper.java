package com.example.rbac.mapper;

import com.example.rbac.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获取所有管理员
     * @param adminId
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(@Param("adminId") Integer adminId, @Param("keywords") String keywords);
}
