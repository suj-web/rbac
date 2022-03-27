package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rbac.mapper.LoginLogMapper;
import com.example.rbac.pojo.LoginLog;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-03-26
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 查询登录日志
     * @param currentPage
     * @param size
     * @param loginLog
     * @param localDateTimeScope
     * @return
     */
    @Override
    public RespPageBean getLoginLogs(Integer currentPage, Integer size, LoginLog loginLog, LocalDateTime[] localDateTimeScope) {
        Page<LoginLog> page = new Page<>(currentPage, size);
        IPage<LoginLog> loginLogIPage = loginLogMapper.getLoginLogs(page, loginLog, localDateTimeScope);
        RespPageBean respPageBean = new RespPageBean(loginLogIPage.getTotal(), loginLogIPage.getRecords());
        return respPageBean;
    }
}
