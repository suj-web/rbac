package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Oplog;
import com.example.rbac.mapper.OplogMapper;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IOplogService;
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
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

    @Autowired
    private OplogMapper oplogMapper;

    /**
     * 查询操作日志
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    @Override
    public RespPageBean getAllOplogs(Integer currentPage, Integer size, String name) {
        Page<Oplog> page = new Page<>(currentPage, size);
        IPage<Oplog> oplogIPage = oplogMapper.getAllOplogs(page, name);
        return new RespPageBean(oplogIPage.getTotal(), oplogIPage.getRecords());
    }
}
