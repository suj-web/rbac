package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.SysMsg;
import com.example.rbac.mapper.SysMsgMapper;
import com.example.rbac.service.ISysMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Service
public class SysMsgServiceImpl extends ServiceImpl<SysMsgMapper, SysMsg> implements ISysMsgService {

    @Autowired
    private SysMsgMapper sysMsgMapper;

    /**
     * 查询系统公告
     * @param currentPage
     * @param size
     * @param sysMsg
     * @return
     */
    @Override
    public RespPageBean getAllSystemMessage(Integer currentPage, Integer size, SysMsg sysMsg) {
        Page<SysMsg> page = new Page<>(currentPage, size);
        IPage<SysMsg> sysMsgIPage = sysMsgMapper.getAllSystemMessage(page, sysMsg);
        RespPageBean respPageBean = new RespPageBean(sysMsgIPage.getTotal(), sysMsgIPage.getRecords());
        return respPageBean;
    }
}
