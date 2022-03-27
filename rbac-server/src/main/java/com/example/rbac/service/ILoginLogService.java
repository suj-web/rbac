package com.example.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.LoginLog;
import com.example.rbac.pojo.RespPageBean;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-03-26
 */
public interface ILoginLogService extends IService<LoginLog> {

    /**
     * 查询登录日志
     * @param currentPage
     * @param size
     * @param loginLog
     * @param localDateTimeScope
     * @return
     */
    RespPageBean getLoginLogs(Integer currentPage, Integer size, LoginLog loginLog, LocalDateTime[] localDateTimeScope);
}
