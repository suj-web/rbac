package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.TableMapper;
import com.example.rbac.pojo.Table;
import com.example.rbac.service.ITableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.rbac.pojo.RespPageBean;

/**
 * TableServiceImpl
 * @author suj
 * @since 2022-4-28
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements ITableService {

    @Autowired
    private TableMapper tableMapper;

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPageBean getAllTable(Integer currentPage, Integer size) {
        Page<Table> page = new Page<>(currentPage, size);
        IPage<Table> tableIPage = tableMapper.selectPage(page,null);
        RespPageBean respPageBean = new RespPageBean(tableIPage.getTotal(), tableIPage.getRecords());
        return respPageBean;
    }
}
