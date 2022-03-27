package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 *  登录日志Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-03-26
 */
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 查询登录日志
     * @param page
     * @param loginLog
     * @param localDateTimeScope
     * @return
     */
    IPage<LoginLog> getLoginLogs(Page<LoginLog> page, @Param("loginLog") LoginLog loginLog, @Param("localDateTimeScope") LocalDateTime[] localDateTimeScope);
}
